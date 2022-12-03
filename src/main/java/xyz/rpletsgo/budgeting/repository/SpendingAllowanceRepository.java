package xyz.rpletsgo.budgeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;

import java.util.List;
import java.util.Optional;

public interface SpendingAllowanceRepository extends JpaRepository<SpendingAllowance, String> {
    @Override
    Optional<SpendingAllowance> findById(String id);
    
    List<SpendingAllowance> findByIdIn(List<String> id);
}
