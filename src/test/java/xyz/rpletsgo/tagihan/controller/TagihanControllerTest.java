package xyz.rpletsgo.tagihan.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.tagihan.service.TagihanService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TagihanControllerTest {
    @MockBean
    TagihanService tagihanService;
    
    @InjectMocks
    TagihanController tagihanController;
    
    @BeforeEach
    void setup(){
        tagihanService = mock(TagihanService.class);
        MockitoAnnotations.openMocks(this);
    }
    
    
    String workspaceId = "a";
    String pengeluaranId = "b";
    
    
    
    String nama = "c";
    String keterangan = "d";
    String waktuStr = "2000-01-02";
    LocalDateTime waktu = LocalDateTime.of(2000, 1, 2, 0, 0);
    long nominal = 10;
    String spendingAllowanceId = "e";
    String tagihanId = "f";
    
    @Test
    void createTagihan() {
        tagihanController.createTagihan(workspaceId, nama, keterangan, waktuStr, nominal);
        verify(tagihanService, times(1))
            .create(workspaceId, nama, keterangan, waktu, nominal);
    }
    
    @Test
    void updateTagihan() {
        tagihanController.updateTagihan(workspaceId, tagihanId, nama, keterangan, waktuStr, nominal);
        verify(tagihanService, times(1))
            .update(workspaceId, tagihanId, nama, keterangan, waktu, nominal);
    }
    
    @Test
    void deleteTagihan() {
        tagihanController.deleteTagihan(workspaceId, tagihanId);
        verify(tagihanService, times(1)).deleteTagihan(workspaceId, tagihanId);
    }
    
    @Test
    void getTagihan() {
        tagihanController.getTagihan(workspaceId);
        verify(tagihanService, times(1)).getListTagihan(workspaceId);
    }
    
    @Test
    void getTagihan_2() {
        tagihanController.getTagihan(workspaceId, tagihanId);
        verify(tagihanService, times(1)).getTagihan(workspaceId, tagihanId);
    }
}