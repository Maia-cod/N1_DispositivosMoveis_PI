package com.exemplo.barberagenda.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.exemplo.barberagenda.data.model.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE login = :login AND senha = :senha LIMIT 1")
    Usuario autenticar(String login, String senha);

    @Query("SELECT COUNT(*) FROM usuarios")
    int contar();

    @Insert
    void inserir(Usuario... usuarios);
}
