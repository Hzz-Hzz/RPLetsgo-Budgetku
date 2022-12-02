package xyz.rpletsgo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.auth.model.Pengguna;

import java.util.Optional;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, String> {
    Optional<Pengguna> findByUsername(String username);
}
