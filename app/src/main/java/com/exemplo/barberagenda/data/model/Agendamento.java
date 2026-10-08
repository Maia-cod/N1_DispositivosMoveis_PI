package com.exemplo.barberagenda.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "agendamentos")
public class Agendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_CONFIRMADO = "CONFIRMADO";
    public static final String STATUS_CANCELADO = "CANCELADO";
    public static final String STATUS_CONCLUIDO = "CONCLUIDO";

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String cliente;
    public String telefone;
    public String servico;
    public long dataHora;
    public String observacao;
    public String status;
    public String criadoPor;

    public Agendamento(long id, String cliente, String telefone, String servico,
                       long dataHora, String observacao, String status, String criadoPor) {
        this.id = id;
        this.cliente = cliente;
        this.telefone = telefone;
        this.servico = servico;
        this.dataHora = dataHora;
        this.observacao = observacao;
        this.status = status;
        this.criadoPor = criadoPor;
    }

    public boolean isConfirmado() {
        return STATUS_CONFIRMADO.equals(status);
    }

    public boolean isAtivo() {
        return STATUS_CONFIRMADO.equals(status) || STATUS_CONCLUIDO.equals(status);
    }
}
