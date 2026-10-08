package com.exemplo.barberagenda.negocio;

import android.content.Context;

import com.exemplo.barberagenda.R;
import com.exemplo.barberagenda.data.model.Agendamento;

/**
 * Regras de negócio do domínio de agendamento de barbearia.
 * Todos os métodos retornam null quando a operação é permitida.
 */
public final class RegrasNegocio {

    public static final long ANTECEDENCIA_MINIMA_MS = 60L * 60L * 1000L;
    public static final long PRAZO_CANCELAMENTO_MS = 2L * 60L * 60L * 1000L;

    private RegrasNegocio() {
    }

    public static String validarLogin(Context contexto, String login, String senha) {
        if (login == null || login.trim().isEmpty()) {
            return contexto.getString(R.string.erro_login_vazio);
        }
        if (senha == null || senha.isEmpty()) {
            return contexto.getString(R.string.erro_senha_vazia);
        }
        return null;
    }

    public static String validarCliente(Context contexto, String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            return contexto.getString(R.string.erro_cliente_vazio);
        }
        return null;
    }

    public static String validarTelefone(Context contexto, String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return contexto.getString(R.string.erro_telefone_vazio);
        }
        String digitos = telefone.replaceAll("[^0-9]", "");
        if (digitos.length() < 8) {
            return contexto.getString(R.string.erro_telefone_curto);
        }
        return null;
    }

    public static String validarHorario(Context contexto, long dataHora, long agora) {
        if (dataHora <= agora) {
            return contexto.getString(R.string.erro_agendamento_antigo);
        }
        if (dataHora - agora < ANTECEDENCIA_MINIMA_MS) {
            return contexto.getString(R.string.erro_antecedencia);
        }
        return null;
    }

    public static String validarConflito(Context contexto, int quantidadeNoHorario) {
        if (quantidadeNoHorario > 0) {
            return contexto.getString(R.string.erro_conflito);
        }
        return null;
    }

    public static String validarCancelamento(Context contexto, Agendamento agendamento,
                                             long agora, boolean usuarioEhDono) {
        if (!agendamento.isConfirmado()) {
            return contexto.getString(R.string.sem_alteracao);
        }
        if (usuarioEhDono) {
            return null;
        }
        if (agendamento.dataHora - agora < PRAZO_CANCELAMENTO_MS) {
            return contexto.getString(R.string.erro_cancelamento_prazo);
        }
        return null;
    }
}
