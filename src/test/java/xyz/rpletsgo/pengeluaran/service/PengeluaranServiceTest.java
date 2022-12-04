package xyz.rpletsgo.pengeluaran.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.common.repository.FinancialEventRepository;
import xyz.rpletsgo.pengeluaran.exceptions.FinancialEventNotFoundException;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.pengeluaran.repository.PengeluaranRepository;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.tagihan.repository.TagihanRepository;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PengeluaranServiceTest {
    @InjectMocks
    PengeluaranService pengeluaranService;
    
    @MockBean
    CurrentLoggedInPengguna loggedInPengguna;
    @MockBean
    WorkspaceRepository workspaceRepository;
    @MockBean
    SpendingAllowanceRepository spendingAllowanceRepository;
    @MockBean
    TagihanRepository tagihanRepository;
    @MockBean
    SpendingAllowanceService spendingAllowanceService;
    @MockBean
    PengeluaranRepository pengeluaranRepository;
    @MockBean
    FinancialEventRepository financialEventRepository;
    
    String workspaceId = "a";
    Workspace workspace;
    
    Pengeluaran pengeluaran1;
    Pengeluaran pengeluaran2;
    String pengeluaranId1 = "b";
    SpendingAllowance spendingAllowance;
    String spendingAllowanceId = "c";
    Tagihan tagihan;
    String tagihanId = "d";
    
    Pengguna pengguna;
    LocalDateTime waktu = LocalDateTime.of(2000, 1, 1, 1, 1);
    long nominal = 10;
    
    
    @BeforeEach
    void setup(){
        initializeMocks();
    }
    
    void initializeMocks(){
        loggedInPengguna = mock(CurrentLoggedInPengguna.class);
        workspaceRepository = mock(WorkspaceRepository.class);
        spendingAllowanceRepository = mock(SpendingAllowanceRepository.class);
        tagihanRepository = mock(TagihanRepository.class);
        spendingAllowanceService = mock(SpendingAllowanceService.class);
        pengeluaranRepository = mock(PengeluaranRepository.class);
        financialEventRepository = mock(FinancialEventRepository.class);
    
        workspace = mock(Workspace.class);
        pengguna = mock(Pengguna.class);
        tagihan = mock(Tagihan.class);
        pengeluaran1 = mock(Pengeluaran.class);
        pengeluaran2 = mock(Pengeluaran.class);
        spendingAllowance = mock(SpendingAllowance.class);
    
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        when(workspace.getSpendingAllowanceOrThrow(spendingAllowanceId)).thenReturn(spendingAllowance);
        when(tagihanRepository.findById(tagihanId)).thenReturn(Optional.of(tagihan));
        
        MockitoAnnotations.openMocks(this);
    }
    
    
    @Test
    void getPengeluaransByWorkspace() {
        List<FinancialEvent> pengeluarans = List.of(pengeluaran1, pengeluaran2);
        when(workspace.getPengeluarans()).thenReturn(pengeluarans);
    
        var res = pengeluaranService.getPengeluaransByWorkspace(workspaceId);
        assertEquals(pengeluarans, res);
    }
    
    
    @Test
    void getPengeluaranById() {
        when(pengeluaranRepository.findById(pengeluaranId1)).thenReturn(Optional.of(pengeluaran1));
        
        var res = pengeluaranService.getPengeluaranById(workspaceId, pengeluaranId1);
        assertEquals(pengeluaran1, res);
    }
    
    
    @Test
    void create() {
        pengeluaranService.create(workspaceId, "nama", "ket", waktu, nominal,
                                  spendingAllowanceId, tagihanId);
    
        var pengeluaranCaptor = ArgumentCaptor.forClass(Pengeluaran.class);
        verify(pengeluaranRepository, times(1)).saveAndFlush(pengeluaranCaptor.capture());
    
        var pengeluaran = pengeluaranCaptor.getValue();
    
        verify(workspace, times(1)).addFinancialEvent(pengeluaran);
        verify(workspaceRepository, times(1)).save(workspace);
        
        verify(spendingAllowance, times(1)).increaseNominal(-nominal);
        verify(tagihan, times(1)).increaseNominal(-nominal);
        
        assertEquals("nama", pengeluaran.getNama());
        assertEquals("ket", pengeluaran.getKeterangan());
        assertEquals(nominal, pengeluaran.getNominal());
    }
    
    
    
    @Test
    void update() {
        when(pengeluaranRepository.findById(pengeluaranId1)).thenReturn(Optional.of(pengeluaran1));
        
        pengeluaranService.update(workspaceId, pengeluaranId1, "nama", "ket", waktu, nominal);
    
        verify(workspace, times(1)).existFinancialEventOrThrow(pengeluaranId1);
        verify(workspaceRepository, times(1)).save(workspace);
        
        verify(pengeluaran1, times(1)).valueUpdate("nama", "ket", waktu, nominal);
    }
    
    @Test
    void update_throwIfPengeluaranIsNotFound() {
        when(pengeluaranRepository.findById(pengeluaranId1)).thenReturn(Optional.empty());
        
        assertThrows(FinancialEventNotFoundException.class,
                     () -> pengeluaranService.update(workspaceId, pengeluaranId1, "nama", "ket", waktu, nominal)
        );
        
    }
    
    @Test
    void delete() {
        when(pengeluaranRepository.findById(pengeluaranId1))
            .thenReturn(Optional.of(pengeluaran1));
        
        pengeluaran1.valueUpdate(any(), any(), any(), anyLong());
        when(pengeluaran1).thenCallRealMethod();
        
    
        pengeluaranService.delete(workspaceId, pengeluaranId1);
        
        verify(pengeluaran1, times(1)).setNominal(0);
    }
    
    @Test
    void delete_throwIfPengeluaranNotFound() {
        when(pengeluaranRepository.findById(pengeluaranId1))
            .thenReturn(Optional.empty());
        
        pengeluaran1.valueUpdate(any(), any(), any(), anyLong());
        when(pengeluaran1).thenCallRealMethod();
        
        assertThrows(FinancialEventNotFoundException.class,
                     () -> pengeluaranService.delete(workspaceId, pengeluaranId1));
    }
}