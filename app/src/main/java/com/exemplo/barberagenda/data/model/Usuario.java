package com.exemplo.barberagenda.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {

    public static final String PERFIL_DONO = "DONO";
    public static final String PERFIL_CLIENTE = "CLIENTE";

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String nome;
    public String login;
    public String senha;
    public String perfil;

    public Usuario(long id, String nome, String login, String senha, String perfil) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public boolean isDono() {
        return PERFIL_DONO.equals(perfil);
    }
}
