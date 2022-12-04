package xyz.rpletsgo.pemasukan.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.core.AlokasiSpendingAllowanceFactory;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.common.repository.FinancialEventRepository;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pemasukan.repository.PemasukanRepository;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PemasukanServiceTest {
    @MockBean
    CurrentLoggedInPengguna loggedInPengguna;
    @MockBean
    WorkspaceRepository workspaceRepository;
    @MockBean
    PemasukanRepository pemasukanRepository;
    @MockBean
    KategoriPemasukanRepository kategoriPemasukanRepository;
    @MockBean
    FinancialEventRepository financialEventRepository;
    @MockBean
    SpendingAllowanceRepository spendingAllowanceRepository;
    @MockBean
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    
    AlokasiSpendingAllowanceFactory alokasiSpendingAllowanceFactory;
    
    Workspace workspace;
    String workspaceId = "a";
    KategoriPemasukan kategoriPemasukan;
    String kategoriPemasukanId = "kt-1";
    String pemasukanId = "pm-1";
    long nominal = 10;
    LocalDateTime localDateTime;
    
    @BeforeEach
    void setup(){
        initializeMocks();
    }
    
    void initializeMocks(){
        loggedInPengguna = mock(CurrentLoggedInPengguna.class);
        workspaceRepository = mock(WorkspaceRepository.class);
        pemasukanRepository = mock(PemasukanRepository.class);
        kategoriPemasukanRepository = mock(KategoriPemasukanRepository.class);
        financialEventRepository = mock(FinancialEventRepository.class);
        spendingAllowanceRepository = mock(SpendingAllowanceRepository.class);
        alokasiSpendingAllowanceRepository = mock(AlokasiSpendingAllowanceRepository.class);
    
        alokasiSpendingAllowanceFactory = mock(AlokasiSpendingAllowanceFactory.class);
        workspace = mock(Workspace.class);
        kategoriPemasukan = mock(KategoriPemasukan.class);
        
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        when(kategoriPemasukanRepository.findById(kategoriPemasukanId)).thenReturn(Optional.of(kategoriPemasukan));
        localDateTime = LocalDateTime.of(2000, 1, 1, 1, 1);
    
        MockitoAnnotations.openMocks(this);
    }
    
    @InjectMocks
    PemasukanService pemasukanService;
    
    @Test
    void getPemasukansByWorkspace() {
        var pemasukan1 = mock(Pemasukan.class);
        var pemasukan2 = mock(Pemasukan.class);
        List<FinancialEvent> pemasukans = List.of(pemasukan1, pemasukan2);
    
        when(workspace.getPemasukans()).thenReturn(pemasukans);
        
        assertEquals(pemasukans, pemasukanService.getPemasukansByWorkspace(workspaceId));
    }
    
    @Test
    void create() {
        var pemasukan = pemasukanService.create(workspaceId, "b", "c", localDateTime,
                                          nominal, kategoriPemasukanId);
    
        verify(kategoriPemasukan, times(1)).addPemasukan(nominal);
        verify(pemasukanRepository, times(1)).saveAndFlush(pemasukan);
        verify(workspaceRepository, times(1)).save(workspace);
    }
    @Test
    void create_throwIfKategoriNotFound() {
        reset(kategoriPemasukanRepository);
        when(kategoriPemasukanRepository.findById(kategoriPemasukanId)).thenReturn(Optional.empty());
        
        assertThrows(KategoriPemasukanNotFoundException.class,
                     () -> pemasukanService.create(workspaceId, "b", "c", localDateTime,
                                                   nominal, kategoriPemasukanId));
    }
    
    @Test
    void update() {
        var pemasukan = mock(Pemasukan.class);
        when(pemasukan.getKategori()).thenReturn(kategoriPemasukan);
        when(pemasukanRepository.findById(pemasukanId)).thenReturn(Optional.of(pemasukan));
        pemasukanService.update(workspaceId, pemasukanId, "b", "c", localDateTime,
                                nominal, kategoriPemasukanId);
    
        verify(kategoriPemasukan, times(1)).addPemasukan(nominal);
        verify(pemasukanRepository, times(1)).save(pemasukan);
        verify(workspaceRepository, times(1)).save(workspace);
    }
    
    @Test
    void update_throwIfKategoriNotFound() {
        var pemasukan = mock(Pemasukan.class);
        reset(kategoriPemasukanRepository);
        when(kategoriPemasukanRepository.findById(kategoriPemasukanId)).thenReturn(Optional.empty());
        
        when(pemasukan.getKategori()).thenReturn(kategoriPemasukan);
        when(pemasukanRepository.findById(pemasukanId)).thenReturn(Optional.of(pemasukan));
        
        assertThrows(KategoriPemasukanNotFoundException.class,
                     () -> pemasukanService.update(workspaceId, pemasukanId, "b", "c", localDateTime,
                                                   nominal, kategoriPemasukanId));
    
    }
    
    @Test
    void delete() {
        var pemasukan = mock(Pemasukan.class);
        when(pemasukan.getNominal()).thenReturn(nominal);
        
        when(pemasukan.getKategori()).thenReturn(kategoriPemasukan);
        when(pemasukanRepository.findById(pemasukanId)).thenReturn(Optional.of(pemasukan));
        pemasukanService.delete(workspaceId, pemasukanId);
    
        verify(kategoriPemasukan, times(1)).addPemasukan(-nominal);
        verify(pemasukanRepository, times(1)).deleteById(pemasukanId);
        verify(workspaceRepository, times(1)).save(workspace);
    }
}