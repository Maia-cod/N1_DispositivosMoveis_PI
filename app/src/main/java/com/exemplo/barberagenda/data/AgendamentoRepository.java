package com.exemplo.barberagenda.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.exemplo.barberagenda.data.dao.AgendamentoDao;
import com.exemplo.barberagenda.data.dao.UsuarioDao;
import com.exemplo.barberagenda.data.model.Agendamento;
import com.exemplo.barberagenda.data.model.Usuario;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgendamentoRepository {

    public interface AuthCallback {
        void aoAutenticar(Usuario usuario);
    }

    public interface QuantidadeCallback {
        void aoContar(int quantidade);
    }

    public interface AcaoCallback {
        void aoConcluir(String erro);
    }

    private final AgendamentoDao agendamentoDao;
    private final UsuarioDao usuarioDao;
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final Handler principal = new Handler(Looper.getMainLooper());

    public AgendamentoRepository(Context contexto) {
        AppDatabase banco = AppDatabase.get(contexto);
        this.agendamentoDao = banco.agendamentoDao();
        this.usuarioDao = banco.usuarioDao();
    }

    public LiveData<List<Agendamento>> listarTodos() {
        return agendamentoDao.listarTodos();
    }

    public void autenticar(String login, String senha, AuthCallback callback) {
        io.execute(() -> {
            Usuario usuario = usuarioDao.autenticar(login, senha);
            principal.post(() -> callback.aoAutenticar(usuario));
        });
    }

    public void contarNoMesmoHorario(long dataHora, long idIgnorado, QuantidadeCallback callback) {
        io.execute(() -> {
            int quantidade = agendamentoDao.contarNoMesmoHorario(dataHora, idIgnorado);
            principal.post(() -> callback.aoContar(quantidade));
        });
    }

    public void inserir(Agendamento agendamento, AcaoCallback callback) {
        io.execute(() -> {
            agendamentoDao.inserir(agendamento);
            principal.post(() -> callback.aoConcluir(null));
        });
    }

    public void atualizar(Agendamento agendamento, AcaoCallback callback) {
        io.execute(() -> {
            agendamentoDao.atualizar(agendamento);
            principal.post(() -> callback.aoConcluir(null));
        });
    }
}
