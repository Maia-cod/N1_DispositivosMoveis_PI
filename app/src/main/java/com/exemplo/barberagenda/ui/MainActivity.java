package com.exemplo.barberagenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exemplo.barberagenda.R;
import com.exemplo.barberagenda.data.AgendamentoRepository;
import com.exemplo.barberagenda.data.Sessao;
import com.exemplo.barberagenda.data.model.Agendamento;
import com.exemplo.barberagenda.databinding.ActivityMainBinding;
import com.exemplo.barberagenda.ui.adapters.AgendamentoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding vinculo;
    private AgendamentoRepository repositorio;
    private AgendamentoAdapter adaptador;
    private List<Agendamento> todos = new ArrayList<>();
    private String textoBusca = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vinculo = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(vinculo.getRoot());

        if (!Sessao.estaAberta(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        repositorio = new AgendamentoRepository(this);

        adaptador = new AgendamentoAdapter(agendamento -> abrirDetalhe(agendamento));
        vinculo.listaAgendamentos.setLayoutManager(new LinearLayoutManager(this));
        vinculo.listaAgendamentos.setAdapter(adaptador);

        vinculo.botaoNovo.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroActivity.class)));

        vinculo.editBusca.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                textoBusca = s == null ? "" : s.toString().trim();
                aplicarFiltro();
            }
        });

        repositorio.listarTodos().observe(this, lista -> {
            todos = lista != null ? lista : new ArrayList<>();
            aplicarFiltro();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.itemSair).setOnMenuItemClickListener(item -> {
            Sessao.encerrar(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        });
        return true;
    }

    private void abrirDetalhe(Agendamento agendamento) {
        Intent intent = new Intent(this, DetalheActivity.class);
        intent.putExtra(DetalheActivity.EXTRA_AGENDAMENTO, agendamento);
        startActivity(intent);
    }

    private void aplicarFiltro() {
        List<Agendamento> filtrados = new ArrayList<>();
        String busca = textoBusca.toLowerCase(Locale.getDefault());
        for (Agendamento agendamento : todos) {
            if (busca.isEmpty()
                    || (agendamento.cliente != null
                    && agendamento.cliente.toLowerCase(Locale.getDefault()).contains(busca))) {
                filtrados.add(agendamento);
            }
        }
        adaptador.submitList(filtrados);
        vinculo.textListaVazia.setVisibility(
                filtrados.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
    }
}
