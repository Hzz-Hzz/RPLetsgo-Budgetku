package xyz.rpletsgo.pengeluaran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;

import java.util.Optional;

@Repository
public interface PengeluaranRepository extends JpaRepository<Pengeluaran, String> {
    @Override
    public abstract Optional<Pengeluaran> findById(String id);
}
