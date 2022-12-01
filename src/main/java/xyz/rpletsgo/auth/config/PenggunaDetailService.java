package xyz.rpletsgo.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.repository.PenggunaRepository;

@Service
public class PenggunaDetailService implements UserDetailsService {
    @Autowired
    PenggunaRepository penggunaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var pengguna = penggunaRepository.findByUsername(username);
        if (pengguna == null)
            throw new UsernameNotFoundException("User Not Found");

        return new PenggunaDetails(pengguna.getUsername(), pengguna.getPassword());
    }


}
