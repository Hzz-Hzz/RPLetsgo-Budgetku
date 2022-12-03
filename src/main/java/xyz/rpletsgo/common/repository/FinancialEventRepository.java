package xyz.rpletsgo.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.common.model.FinancialEvent;

@Repository
public interface FinancialEventRepository extends JpaRepository<FinancialEvent, String> {
}
