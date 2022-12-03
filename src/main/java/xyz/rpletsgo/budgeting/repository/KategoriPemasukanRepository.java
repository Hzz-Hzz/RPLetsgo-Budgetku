package xyz.rpletsgo.budgeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.Optional;

public interface KategoriPemasukanRepository extends JpaRepository<KategoriPemasukan, String> {
    @Override
    Optional<KategoriPemasukan> findById(String id);
    
}
