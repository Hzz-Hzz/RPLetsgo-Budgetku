package xyz.rpletsgo.budgeting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class AdditionalSpendingAllowanceRepository {
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;
    
    /**
     * Perbedaannya sama spendingAllowanceRepository.findByIdIn() adalah
     * findByIdIn() tidak menjamin kalau semua ID-nya ditemukan, dan tidak menjamin juga kalau urutannya
     * akan tetap terjaga
     * @param idList
     * @return
     */
    public List<SpendingAllowance> findAllById(List<String> idList){
        var spendingAllowanceList = spendingAllowanceRepository.findByIdIn(idList);
        
        int numberOfNotFound = idList.size() - spendingAllowanceList.size();
        if (numberOfNotFound > 0)
            throw new SpendingAllowanceNotFoundException(numberOfNotFound + " spending allowance ID (s) not found");
        return sortBy(spendingAllowanceList, idList);
    }
    
    List<SpendingAllowance> sortBy(List<SpendingAllowance> sort, List<String> by){
        var res = new ArrayList<SpendingAllowance>();
    
        for (String id: by) {
            for (var spendingAllowance: sort) {
                var isEqual = Objects.equals(id, spendingAllowance.getId());
                if (isEqual){
                    res.add(spendingAllowance);
                    break;
                }
            }
        }
        return res;
    }
}
