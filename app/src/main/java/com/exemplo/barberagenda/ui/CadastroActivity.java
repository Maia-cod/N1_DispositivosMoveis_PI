package com.exemplo.barberagenda.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.barberagenda.R;
import com.exemplo.barberagenda.data.AgendamentoRepository;
import com.exemplo.barberagenda.data.Sessao;
import com.exemplo.barberagenda.data.model.Agendamento;
import com.exemplo.barberagenda.databinding.ActivityCadastroBinding;
import com.exemplo.barberagenda.negocio.RegrasNegocio;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CadastroActivity extends AppCompatActivity {

    private static final String CHAVE_ANO = "ano";
    private static final String CHAVE_MES = "mes";
    private static final String CHAVE_DIA = "dia";
    private static final String CHAVE_HORA = "hora";
    private static final String CHAVE_MINUTO = "minuto";

    private ActivityCadastroBinding vinculo;
    private AgendamentoRepository repositorio;

    private int ano = -1;
    private int mes = -1;
    private int dia = -1;
    private int hora = -1;
    private int minuto = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vinculo = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(vinculo.getRoot());

        repositorio = new AgendamentoRepository(this);

        ArrayAdapter<String> adaptadorServicos = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.servicos));
        vinculo.dropdownServico.setAdapter(adaptadorServicos);

        if (savedInstanceState != null) {
            ano = savedInstanceState.getInt(CHAVE_ANO, -1);
            mes = savedInstanceState.getInt(CHAVE_MES, -1);
            dia = savedInstanceState.getInt(CHAVE_DIA, -1);
            hora = savedInstanceState.getInt(CHAVE_HORA, -1);
            minuto = savedInstanceState.getInt(CHAVE_MINUTO, -1);
            atualizarTextosDataHora();
        }

        vinculo.botaoData.setOnClickListener(v -> abrirSeletorDeData());
        vinculo.botaoHora.setOnClickListener(v -> abrirSeletorDeHora());
        vinculo.botaoSalvar.setOnClickListener(v -> tentarSalvar());
        vinculo.botaoCancelar.setOnClickListener(v -> finish());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHAVE_ANO, ano);
        outState.putInt(CHAVE_MES, mes);
        outState.putInt(CHAVE_DIA, dia);
        outState.putInt(CHAVE_HORA, hora);
        outState.putInt(CHAVE_MINUTO, minuto);
    }

    private void abrirSeletorDeData() {
        MaterialDatePicker<Long> seletor = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.campo_data))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        seletor.addOnPositiveButtonClickListener(selecao -> {
            Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            utc.setTimeInMillis(selecao);
            ano = utc.get(Calendar.YEAR);
            mes = utc.get(Calendar.MONTH);
            dia = utc.get(Calendar.DAY_OF_MONTH);
            atualizarTextosDataHora();
        });

        seletor.show(getSupportFragmentManager(), "seletor_data");
    }

    private void abrirSeletorDeHora() {
        MaterialTimePicker seletor = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hora >= 0 ? hora : 9)
                .setMinute(minuto >= 0 ? minuto : 0)
                .setTitleText(getString(R.string.campo_hora))
                .build();

        seletor.addOnPositiveButtonClickListener(v -> {
            hora = seletor.getHour();
            minuto = seletor.getMinute();
            atualizarTextosDataHora();
        });

        seletor.show(getSupportFragmentManager(), "seletor_hora");
    }

    private void atualizarTextosDataHora() {
        if (dia > 0) {
            vinculo.valorData.setText(String.format(Locale.getDefault(),
                    "%02d/%02d/%04d", dia, mes + 1, ano));
        }
        if (hora >= 0) {
            vinculo.valorHora.setText(String.format(Locale.getDefault(),
                    "%02d:%02d", hora, minuto));
        }
    }

    private void tentarSalvar() {
        String cliente = textoDe(vinculo.editCliente);
        String telefone = textoDe(vinculo.editTelefone);
        String servico = textoDe(vinculo.dropdownServico);
        String observacao = textoDe(vinculo.editObservacao);

        vinculo.layoutCliente.setError(null);
        vinculo.layoutTelefone.setError(null);
        vinculo.layoutServico.setError(null);

        String erroCliente = RegrasNegocio.validarCliente(this, cliente);
        if (erroCliente != null) {
            vinculo.layoutCliente.setError(erroCliente);
            vinculo.editCliente.requestFocus();
            return;
        }

        String erroTelefone = RegrasNegocio.validarTelefone(this, telefone);
        if (erroTelefone != null) {
            vinculo.layoutTelefone.setError(erroTelefone);
            vinculo.editTelefone.requestFocus();
            return;
        }

        if (servico.isEmpty()) {
            vinculo.layoutServico.setError(getString(R.string.erro_servico_vazio));
            return;
        }

        if (ano < 0 || dia < 0) {
            mostrarAviso(getString(R.string.erro_data_obrigatoria));
            return;
        }
        if (hora < 0 || minuto < 0) {
            mostrarAviso(getString(R.string.erro_hora_obrigatoria));
            return;
        }

        Calendar calendario = Calendar.getInstance();
        calendario.set(ano, mes, dia, hora, minuto, 0);
        calendario.set(Calendar.MILLISECOND, 0);
        long dataHora = calendario.getTimeInMillis();

        String erroHorario = RegrasNegocio.validarHorario(
                this, dataHora, System.currentTimeMillis());
        if (erroHorario != null) {
            mostrarAviso(erroHorario);
            return;
        }

        vinculo.botaoSalvar.setEnabled(false);
        repositorio.contarNoMesmoHorario(dataHora, 0, quantidade -> {
            String erroConflito = RegrasNegocio.validarConflito(this, quantidade);
            if (erroConflito != null) {
                vinculo.botaoSalvar.setEnabled(true);
                mostrarAviso(erroConflito);
                return;
            }

            Agendamento novo = new Agendamento(0, cliente, telefone, servico, dataHora,
                    observacao, Agendamento.STATUS_CONFIRMADO, Sessao.getLogin(this));

            repositorio.inserir(novo, erro -> {
                vinculo.botaoSalvar.setEnabled(true);
                if (erro != null) {
                    mostrarAviso(getString(R.string.erro_salvar));
                    return;
                }
                Toast.makeText(this, R.string.sucesso_agendado, Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private String textoDe(TextView campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }

    private void mostrarAviso(String mensagem) {
        Snackbar.make(vinculo.getRoot(), mensagem, Snackbar.LENGTH_LONG).show();
    }
}
