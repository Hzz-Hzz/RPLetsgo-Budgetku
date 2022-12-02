package xyz.rpletsgo.pemasukan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import java.util.Optional;

@Repository
public interface PemasukanRepository extends JpaRepository<Pemasukan, String> {
    @Override
    Optional<Pemasukan> findById(String id);
}
