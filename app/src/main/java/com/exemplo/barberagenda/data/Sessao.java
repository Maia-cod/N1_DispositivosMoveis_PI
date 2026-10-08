package com.exemplo.barberagenda.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.exemplo.barberagenda.data.model.Usuario;

public class Sessao {

    private static final String ARQUIVO = "sessao";
    private static final String CHAVE_LOGIN = "login";
    private static final String CHAVE_NOME = "nome";
    private static final String CHAVE_PERFIL = "perfil";

    private Sessao() {
    }

    private static SharedPreferences prefs(Context contexto) {
        return contexto.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
    }

    public static void abrir(Context contexto, Usuario usuario) {
        prefs(contexto).edit()
                .putString(CHAVE_LOGIN, usuario.login)
                .putString(CHAVE_NOME, usuario.nome)
                .putString(CHAVE_PERFIL, usuario.perfil)
                .apply();
    }

    public static String getLogin(Context contexto) {
        return prefs(contexto).getString(CHAVE_LOGIN, "");
    }

    public static String getNome(Context contexto) {
        return prefs(contexto).getString(CHAVE_NOME, "");
    }

    public static String getPerfil(Context contexto) {
        return prefs(contexto).getString(CHAVE_PERFIL, "");
    }

    public static boolean isDono(Context contexto) {
        return Usuario.PERFIL_DONO.equals(getPerfil(contexto));
    }

    public static boolean estaAberta(Context contexto) {
        return !getLogin(contexto).isEmpty();
    }

    public static void encerrar(Context contexto) {
        prefs(contexto).edit().clear().apply();
    }
}
