package com.exemplo.barberagenda.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.exemplo.barberagenda.R;
import com.exemplo.barberagenda.data.model.Agendamento;
import com.exemplo.barberagenda.databinding.ItemAgendamentoBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AgendamentoAdapter extends ListAdapter<Agendamento, AgendamentoAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void aoClicar(Agendamento agendamento);
    }

    private static final DiffUtil.ItemCallback<Agendamento> DIF =
            new DiffUtil.ItemCallback<Agendamento>() {
                @Override
                public boolean areItemsTheSame(@NonNull Agendamento antigo, @NonNull Agendamento novo) {
                    return antigo.id == novo.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Agendamento antigo, @NonNull Agendamento novo) {
                    return antigo.id == novo.id
                            && Objects.equals(antigo.status, novo.status)
                            && antigo.dataHora == novo.dataHora
                            && Objects.equals(antigo.cliente, novo.cliente);
                }
            };

    private final OnItemClickListener listener;
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public AgendamentoAdapter(OnItemClickListener listener) {
        super(DIF);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAgendamentoBinding vinculo =
                ItemAgendamentoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(vinculo);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Agendamento agendamento = getItem(position);
        Context contexto = holder.itemView.getContext();

        holder.binding.textCliente.setText(agendamento.cliente);
        holder.binding.textServico.setText(agendamento.servico);
        holder.binding.textHora.setText(formatoHora.format(new Date(agendamento.dataHora)));
        holder.binding.textData.setText(formatoData.format(new Date(agendamento.dataHora)));
        pintarStatus(contexto, holder.binding.textStatus, agendamento);

        holder.itemView.setContentDescription(
                contexto.getString(R.string.descricao_abrir_agendamento, agendamento.cliente));
        holder.itemView.setOnClickListener(v -> listener.aoClicar(agendamento));
    }

    public static void pintarStatus(Context contexto,
                                    android.widget.TextView destino,
                                    Agendamento agendamento) {
        long agora = System.currentTimeMillis();
        int texto;
        int cor;
        if (Agendamento.STATUS_CANCELADO.equals(agendamento.status)) {
            texto = R.string.status_cancelado;
            cor = R.color.status_cancelado;
        } else if (Agendamento.STATUS_CONCLUIDO.equals(agendamento.status)) {
            texto = R.string.status_concluido;
            cor = R.color.status_concluido;
        } else if (agendamento.dataHora < agora) {
            texto = R.string.status_atrasado;
            cor = R.color.status_atrasado;
        } else {
            texto = R.string.status_confirmado;
            cor = R.color.status_confirmado;
        }
        destino.setText(texto);
        destino.setTextColor(ContextCompat.getColor(contexto, cor));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAgendamentoBinding binding;

        ViewHolder(ItemAgendamentoBinding vinculo) {
            super(vinculo.getRoot());
            this.binding = vinculo;
        }
    }
}
