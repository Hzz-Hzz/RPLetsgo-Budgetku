package xyz.rpletsgo.budgeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;

import java.util.Optional;

public interface KategoriPemasukanRepository extends JpaRepository<SpendingAllowance, String> {
    @Override
    public abstract Optional<SpendingAllowance> findById(String id);
}
