package xyz.rpletsgo.tagihan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.util.Optional;

public interface TagihanRepository extends JpaRepository<Tagihan, String> {
    @Override
    public abstract Optional<Tagihan> findById(String s);
}
