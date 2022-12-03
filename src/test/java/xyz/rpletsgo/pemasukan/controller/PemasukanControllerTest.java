package xyz.rpletsgo.pemasukan.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.pemasukan.service.PemasukanService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class PemasukanControllerTest {
    @MockBean
    PemasukanService pemasukanService;
    
    @InjectMocks
    PemasukanController pemasukanController;
    
    @BeforeEach
    void setup(){
        pemasukanService = mock(PemasukanService.class);
        MockitoAnnotations.openMocks(this);
    }
    
    
    String workspaceId = "a";
    String pemasukanId = "b";
    
    
    @Test
    void getPemasukans() {
        pemasukanController.getPemasukan(workspaceId);
        verify(pemasukanService, times(1)).getPemasukansByWorkspace(workspaceId);
    }
    
    
    String nama = "c";
    String keterangan = "d";
    String waktuStr = "2000-01-02";
    LocalDateTime waktu = LocalDateTime.of(2000, 1, 2, 0, 0);
    long nominal = 10;
    String kategoriPemasukanId = "e";
    
    @Test
    void createPemasukan() {
        pemasukanController.createPemasukan(workspaceId, nama, keterangan, waktuStr, nominal,
                                            kategoriPemasukanId);
        verify(pemasukanService, times(1))
            .create(workspaceId, nama, keterangan, waktu, nominal, kategoriPemasukanId);
    }
    
    @Test
    void updatePemasukan() {
        pemasukanController.updatePemasukan(workspaceId, pemasukanId, nama, keterangan, waktuStr, nominal,
                                              kategoriPemasukanId);
        verify(pemasukanService, times(1))
            .update(workspaceId, pemasukanId, nama, keterangan, waktu, nominal, kategoriPemasukanId);
    }
    
    @Test
    void deletePemasukan() {
        pemasukanController.deletePemasukan(workspaceId, pemasukanId);
        verify(pemasukanService, times(1)).delete(workspaceId, pemasukanId);
    }
    
    
}