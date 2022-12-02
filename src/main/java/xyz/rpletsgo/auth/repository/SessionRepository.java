package xyz.rpletsgo.auth.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.auth.exceptions.InvalidSessionException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.util.RandomStringUtil;

import java.util.concurrent.TimeUnit;

@Repository
public class SessionRepository implements ISessionRepository{
    @Setter
    Cache<String, Pengguna> sessionToPenggunaMapping =  CacheBuilder
        .newBuilder()
        .expireAfterAccess(3, TimeUnit.HOURS)
        .build();
    
    @Override
    public String createSession(Pengguna pengguna) {
        sessionToPenggunaMapping.cleanUp();
        String token = RandomStringUtil.randomString(16);
        
        sessionToPenggunaMapping.put(token, pengguna);
        return token;
    }
    
    @Override
    public Pengguna getSessionOrThrow(String session) {
        var pengguna = sessionToPenggunaMapping.getIfPresent(session);
        if (pengguna == null){
            throw new InvalidSessionException("Not logged in (Invalid session id)");
        }
        
        return pengguna;
    }
}
