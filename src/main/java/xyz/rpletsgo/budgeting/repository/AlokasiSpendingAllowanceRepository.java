package xyz.rpletsgo.budgeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;

import java.util.Optional;

public interface AlokasiSpendingAllowanceRepository
    extends JpaRepository<AlokasiSpendingAllowance, String>
{
    @Override
    Optional<AlokasiSpendingAllowance> findById(String id);
}
