package xyz.rpletsgo.budgeting.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.common.exceptions.GeneralException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AlokasiSpendingAllowanceFactoryTest {
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    SpendingAllowanceRepository spendingAllowanceRepository;
    
    AlokasiSpendingAllowance alokasi1;
    String alokasiId1 = "id1";
    AlokasiSpendingAllowance alokasi2;
    String alokasiId2 = "id2";
    SpendingAllowance spendingAllowance1;
    SpendingAllowance spendingAllowance2;
    String spendingId1 = "spid1";
    String spendingId2 = "spid2";
    
    @BeforeEach
    void setup(){
        alokasi1 = mock(AlokasiSpendingAllowance.class);
        alokasi2 = mock(AlokasiSpendingAllowance.class);
        spendingAllowance1 = mock(SpendingAllowance.class);
        spendingAllowance2 = mock(SpendingAllowance.class);
        
        when(alokasi1.getId()).thenReturn(alokasiId1);
        when(alokasi2.getId()).thenReturn(alokasiId2);
        when(spendingAllowance1.getId()).thenReturn(spendingId1);
        when(spendingAllowance2.getId()).thenReturn(spendingId2);
        when(alokasi1.getSpendingAllowance()).thenReturn(spendingAllowance1);
        when(alokasi2.getSpendingAllowance()).thenReturn(spendingAllowance2);
    
        alokasiSpendingAllowanceRepository = mock(AlokasiSpendingAllowanceRepository.class);
        when(alokasiSpendingAllowanceRepository.findById(alokasiId1))
            .thenReturn(Optional.of(alokasi1));
    
        spendingAllowanceRepository = mock(SpendingAllowanceRepository.class);
        when(spendingAllowanceRepository.findById(spendingId1))
            .thenReturn(Optional.of(spendingAllowance1));
    }
    
    
    @Test
    void createFromExisting() {
        when(alokasiSpendingAllowanceRepository.findById(alokasiId2))
            .thenReturn(Optional.of(alokasi2));
        when(spendingAllowanceRepository.findById(spendingId2))
            .thenReturn(Optional.of(spendingAllowance2));
        
        var factory = new AlokasiSpendingAllowanceFactory();
        var alokasiList = List.of(alokasi1, alokasi2);
        
        var alokasiSpendingAllowancesList = factory.create(
            alokasiList, alokasiSpendingAllowanceRepository, spendingAllowanceRepository);
        assertEquals(alokasiList, alokasiSpendingAllowancesList);
    }
    @Test
    void createFromExisting_throwIfAlokasiNotFound() {
        when(alokasiSpendingAllowanceRepository.findById(alokasiId2))
            .thenReturn(Optional.empty());
        when(spendingAllowanceRepository.findById(spendingId2))
            .thenReturn(Optional.of(spendingAllowance2));
        
        var factory = new AlokasiSpendingAllowanceFactory();
        var alokasiList = List.of(alokasi1, alokasi2);
        
        assertThrows(
            GeneralException.class,
            () -> factory.create(alokasiList, alokasiSpendingAllowanceRepository, spendingAllowanceRepository)
        );
    }
    @Test
    void createFromExisting_throwIfSpendingAllowanceNotFound() {
        when(alokasiSpendingAllowanceRepository.findById(alokasiId2))
            .thenReturn(Optional.of(alokasi2));
        when(spendingAllowanceRepository.findById(spendingId2))
            .thenReturn(Optional.empty());
        
        var factory = new AlokasiSpendingAllowanceFactory();
        var alokasiList = List.of(alokasi1, alokasi2);
        
        assertThrows(
            SpendingAllowanceNotFoundException.class,
            () -> factory.create(alokasiList, alokasiSpendingAllowanceRepository, spendingAllowanceRepository)
        );
    }
    
    
    
    @Test
    void create_itShouldThrowsIfGivenListsLengthAreNotEqual() {
        var factory = new AlokasiSpendingAllowanceFactory();
        var spendingAllowances = List.of(spendingAllowance1);  // one item
        List<Double> besarAlokasi = List.of();  // empty
        
        assertThrows(GeneralException.class,
                     () -> factory.create(spendingAllowances, besarAlokasi));
    }
    @Test
    void create_itShouldThrowsIfTotalAlokasiIsGreaterThanOne() {
        var factory = new AlokasiSpendingAllowanceFactory();
        var spendingAllowances = List.of(spendingAllowance1, spendingAllowance2);
        var besarAlokasi = List.of(1.0, 0.1);
        
        assertThrows(GeneralException.class,
                     () -> factory.create(spendingAllowances, besarAlokasi));
    }
    
    @Test
    void create_itShouldThrowsIfTotalAlokasiIsLessThanZero() {
        var factory = new AlokasiSpendingAllowanceFactory();
        var spendingAllowances = List.of(spendingAllowance1, spendingAllowance2);
        var besarAlokasi = List.of(-0.5, -0.5);
        
        assertThrows(GeneralException.class,
                     () -> factory.create(spendingAllowances, besarAlokasi));
    }
    @Test
    void create_itShouldThrowsIfTotalAlokasiBetweenZeroAndOne() {
        var factory = new AlokasiSpendingAllowanceFactory();
        var spendingAllowances = List.of(spendingAllowance1, spendingAllowance2);
        var besarAlokasi = List.of(0.3, 0.4);
        
        var res = factory.create(spendingAllowances, besarAlokasi);
        
        assertEquals(0.3, res.get(0).getBesarAlokasi());
        assertEquals(0.4, res.get(1).getBesarAlokasi());
        assertEquals(spendingAllowance1, res.get(0).getSpendingAllowance());
        assertEquals(spendingAllowance2, res.get(1).getSpendingAllowance());
    }
}