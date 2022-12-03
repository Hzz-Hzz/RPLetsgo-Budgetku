package xyz.rpletsgo.pengeluaran.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.pengeluaran.service.PengeluaranService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PengeluaranControllerTest {
    @MockBean
    PengeluaranService pengeluaranService;
    
    @InjectMocks
    PengeluaranController pengeluaranController;
    
    @BeforeEach
    void setup(){
        pengeluaranService = mock(PengeluaranService.class);
        
        MockitoAnnotations.openMocks(this);
    }
    
    
    String workspaceId = "a";
    String pengeluaranId = "b";
    
    
    @Test
    void getPengeluarans() {
        pengeluaranController.getPengeluarans(workspaceId);
        verify(pengeluaranService, times(1)).getPengeluaransByWorkspace(workspaceId);
    }
    
    @Test
    void getPengeluaranById() {
        pengeluaranController.getPengeluaranById(workspaceId, pengeluaranId);
        verify(pengeluaranService, times(1)).getPengeluaranById(workspaceId, pengeluaranId);
    }
    
    
    String nama = "c";
    String keterangan = "d";
    String waktuStr = "2000-01-02";
    LocalDateTime waktu = LocalDateTime.of(2000, 1, 2, 0, 0);
    long nominal = 10;
    String spendingAllowanceId = "e";
    String tagihanId = "f";
    
    @Test
    void createPengeluaran() {
        pengeluaranController.createPengeluaran(workspaceId, nama, keterangan, waktuStr, nominal,
                                                spendingAllowanceId, Optional.of(tagihanId));
        verify(pengeluaranService, times(1))
            .create(workspaceId, nama, keterangan, waktu, nominal,spendingAllowanceId, tagihanId);
    }
    
    @Test
    void updatePengeluaran() {
        pengeluaranController.updatePengeluaran(workspaceId, pengeluaranId, nama, keterangan, waktuStr, nominal,
                                                spendingAllowanceId, Optional.of(tagihanId));
        verify(pengeluaranService, times(1))
            .update(workspaceId, pengeluaranId, nama, keterangan, waktu, nominal,spendingAllowanceId, tagihanId);
    }
    
    @Test
    void deletePengeluaran() {
        pengeluaranController.deletePengeluaran(workspaceId, pengeluaranId);
        verify(pengeluaranService, times(1)).delete(workspaceId, pengeluaranId);
    }
}