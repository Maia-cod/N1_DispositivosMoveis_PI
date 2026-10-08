package com.exemplo.barberagenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.barberagenda.data.AgendamentoRepository;
import com.exemplo.barberagenda.data.Sessao;
import com.exemplo.barberagenda.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding vinculo;
    private AgendamentoRepository repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vinculo = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(vinculo.getRoot());

        if (Sessao.estaAberta(this)) {
            irParaAgenda();
            return;
        }

        repositorio = new AgendamentoRepository(this);

        vinculo.botaoEntrar.setOnClickListener(v -> tentarEntrar());

        vinculo.editSenha.setOnEditorActionListener((texto, acao, evento) -> {
            if (acao == EditorInfo.IME_ACTION_DONE) {
                tentarEntrar();
                return true;
            }
            return false;
        });
    }

    private void tentarEntrar() {
        String login = vinculo.editLogin.getText() == null
                ? "" : vinculo.editLogin.getText().toString().trim();
        String senha = vinculo.editSenha.getText() == null
                ? "" : vinculo.editSenha.getText().toString();

        vinculo.layoutLogin.setError(null);
        vinculo.layoutSenha.setError(null);

        String erro = com.exemplo.barberagenda.negocio.RegrasNegocio
                .validarLogin(this, login, senha);
        if (erro != null) {
            if (login.isEmpty()) {
                vinculo.layoutLogin.setError(erro);
            } else {
                vinculo.layoutSenha.setError(erro);
            }
            return;
        }

        vinculo.botaoEntrar.setEnabled(false);
        repositorio.autenticar(login, senha, usuario -> {
            vinculo.botaoEntrar.setEnabled(true);
            if (usuario == null) {
                vinculo.layoutSenha.setError(getString(com.exemplo.barberagenda.R.string.erro_login_invalido));
                return;
            }
            Sessao.abrir(this, usuario);
            irParaAgenda();
        });
    }

    private void irParaAgenda() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
