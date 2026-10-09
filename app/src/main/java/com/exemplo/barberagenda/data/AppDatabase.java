package com.exemplo.barberagenda.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.exemplo.barberagenda.BuildConfig;
import com.exemplo.barberagenda.data.dao.AgendamentoDao;
import com.exemplo.barberagenda.data.dao.UsuarioDao;
import com.exemplo.barberagenda.data.model.Agendamento;
import com.exemplo.barberagenda.data.model.Usuario;

@Database(entities = {Usuario.class, Agendamento.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract AgendamentoDao agendamentoDao();

    private static volatile AppDatabase instancia;

    public static AppDatabase get(Context contexto) {
        if (instancia == null) {
            synchronized (AppDatabase.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(
                                    contexto.getApplicationContext(),
                                    AppDatabase.class,
                                    "barberagenda.db")
                            .addCallback(new SeedCallback())
                            .build();
                }
            }
        }
        return instancia;
    }

    private static class SeedCallback extends Callback {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
<<<<<<< HEAD
            inserirUsuario(db, "Carlos",
=======
            inserirUsuario(db, "Carlos (barbeiro)",
>>>>>>> 2dfd45fc015ddfc7418bd81dcbf71d9c5b7c9a5e
                    BuildConfig.DEMO_DONO_LOGIN, BuildConfig.DEMO_DONO_SENHA, Usuario.PERFIL_DONO);
            inserirUsuario(db, "Ana",
                    BuildConfig.DEMO_CLIENTE_LOGIN, BuildConfig.DEMO_CLIENTE_SENHA, Usuario.PERFIL_CLIENTE);
        }

        private void inserirUsuario(SupportSQLiteDatabase db, String nome,
                                    String login, String senha, String perfil) {
            ContentValues valores = new ContentValues();
            valores.put("nome", nome);
            valores.put("login", login);
            valores.put("senha", senha);
            valores.put("perfil", perfil);
            db.insert("usuarios", SQLiteDatabase.CONFLICT_IGNORE, valores);
        }
    }
}
