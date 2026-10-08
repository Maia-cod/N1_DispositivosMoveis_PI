package com.exemplo.barberagenda.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exemplo.barberagenda.data.model.Agendamento;

import java.util.List;

@Dao
public interface AgendamentoDao {

    @Insert
    long inserir(Agendamento agendamento);

    @Update
    void atualizar(Agendamento agendamento);

    @Query("SELECT * FROM agendamentos ORDER BY dataHora ASC")
    LiveData<List<Agendamento>> listarTodos();

    @Query("SELECT * FROM agendamentos WHERE id = :id")
    Agendamento buscarPorId(long id);

    @Query("SELECT COUNT(*) FROM agendamentos "
            + "WHERE status = 'CONFIRMADO' AND dataHora = :dataHora AND id <> :idIgnorado")
    int contarNoMesmoHorario(long dataHora, long idIgnorado);
}
