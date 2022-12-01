package xyz.rpletsgo.auth.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.auth.exceptions.InvalidSessionException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.util.RandomStringUtil;

import java.util.concurrent.TimeUnit;

@Repository
public class SessionRepository implements ISessionRepository{
    Cache<String, Pengguna> loggedInPengguna =  CacheBuilder
        .newBuilder()
        .expireAfterAccess(3, TimeUnit.HOURS)
        .build();
    
    @Override
    public String createSession(Pengguna pengguna) {
        loggedInPengguna.cleanUp();
        String token = RandomStringUtil.randomString(16);
        
        loggedInPengguna.put(token, pengguna);
        return token;
    }
    
    @Override
    public Pengguna getSessionOrThrow(String session) {
        var pengguna = loggedInPengguna.getIfPresent(session);
        if (pengguna == null){
            throw new InvalidSessionException("Invalid session id");
        }
        
        return pengguna;
    }
}
