package com.exemplo.barberagenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.barberagenda.R;
import com.exemplo.barberagenda.data.AgendamentoRepository;
import com.exemplo.barberagenda.data.Sessao;
import com.exemplo.barberagenda.data.model.Agendamento;
import com.exemplo.barberagenda.databinding.ActivityDetalheBinding;
import com.exemplo.barberagenda.negocio.RegrasNegocio;
import com.exemplo.barberagenda.ui.adapters.AgendamentoAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetalheActivity extends AppCompatActivity {

    public static final String EXTRA_AGENDAMENTO = "agendamento";
    private static final String CHAVE_AGENDAMENTO = "agendamento_estado";

    private ActivityDetalheBinding vinculo;
    private AgendamentoRepository repositorio;
    private Agendamento agendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vinculo = ActivityDetalheBinding.inflate(getLayoutInflater());
        setContentView(vinculo.getRoot());

        Object estado = savedInstanceState == null
                ? null : savedInstanceState.getSerializable(CHAVE_AGENDAMENTO);
        if (estado == null) {
            estado = getIntent().getSerializableExtra(EXTRA_AGENDAMENTO);
        }

        if (!(estado instanceof Agendamento)) {
            Toast.makeText(this, R.string.erro_ao_carregar, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        agendamento = (Agendamento) estado;
        repositorio = new AgendamentoRepository(this);

        preencherTela();

        vinculo.botaoConcluir.setOnClickListener(v -> concluir());
        vinculo.botaoCancelarAgendamento.setOnClickListener(v -> pedirConfirmacaoDeCancelamento());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CHAVE_AGENDAMENTO, agendamento);
    }

    private void preencherTela() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault());

        vinculo.valorCliente.setText(agendamento.cliente);
        vinculo.valorTelefone.setText(agendamento.telefone);
        vinculo.valorServico.setText(agendamento.servico);
        vinculo.valorDataHora.setText(formato.format(new Date(agendamento.dataHora)));
        vinculo.valorObservacao.setText(agendamento.observacao == null
                || agendamento.observacao.isEmpty()
                ? getString(R.string.sem_observacao)
                : agendamento.observacao);
        AgendamentoAdapter.pintarStatus(this, vinculo.valorStatus, agendamento);

        boolean editavel = agendamento.isConfirmado();
        vinculo.botaoConcluir.setEnabled(editavel);
        vinculo.botaoCancelarAgendamento.setEnabled(editavel);
    }

    private void concluir() {
        agendamento.status = Agendamento.STATUS_CONCLUIDO;
        repositorio.atualizar(agendamento, erro -> {
            if (erro != null) {
                mostrarAviso(getString(R.string.erro_salvar));
                return;
            }
            Toast.makeText(this, R.string.agendamento_concluido, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void pedirConfirmacaoDeCancelamento() {
        String erro = RegrasNegocio.validarCancelamento(
                this, agendamento, System.currentTimeMillis(), Sessao.isDono(this));
        if (erro != null) {
            mostrarAviso(erro);
            return;
        }

        new MaterialAlertDialogBuilder(this)
                .setMessage(R.string.confirmar_cancelamento)
                .setPositiveButton(R.string.sim, (dialogo, qual) -> executarCancelamento())
                .setNegativeButton(R.string.nao, (dialogo, qual) -> dialogo.dismiss())
                .show();
    }

    private void executarCancelamento() {
        agendamento.status = Agendamento.STATUS_CANCELADO;
        repositorio.atualizar(agendamento, erro -> {
            if (erro != null) {
                mostrarAviso(getString(R.string.erro_salvar));
                return;
            }
            Toast.makeText(this, R.string.agendamento_cancelado, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void mostrarAviso(String mensagem) {
        Snackbar.make(vinculo.getRoot(), mensagem, Snackbar.LENGTH_LONG).show();
    }
}
