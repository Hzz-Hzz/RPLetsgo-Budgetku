package xyz.rpletsgo.auth.repository;

import xyz.rpletsgo.auth.model.Pengguna;

public interface ISessionRepository {
    String createSession(Pengguna pengguna);
    Pengguna getSessionOrThrow(String session);
}
