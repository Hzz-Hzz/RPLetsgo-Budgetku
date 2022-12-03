package xyz.rpletsgo.budgeting.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.budgeting.service.KategoriPemasukanService;

import static org.mockito.Mockito.*;


class ITKategoriPemasukanTest {
    @MockBean
    KategoriPemasukanService kategoriPemasukanService;
    
    @InjectMocks
    KategoriPemasukanController kategoriPemasukanController;
    
    @BeforeEach
    void setup(){
        kategoriPemasukanService = mock(KategoriPemasukanService.class);
        MockitoAnnotations.openMocks(this);
    }
    
    
    String workspaceId = "a";
    String idKategori = "b-1";
    String namaKategori = "b-2";
    Double[] besarAlokasi = new Double[]{0.2, 0.8};
    String[] spendingAllowanceId = new String[]{"c", "d"};
    
    @Test
    @SneakyThrows
    void createSpendingAllowance() {
        kategoriPemasukanController
            .createKategoriPemasukan(workspaceId, namaKategori, besarAlokasi, spendingAllowanceId);
        verify(kategoriPemasukanService, times(1))
            .create(workspaceId, namaKategori, besarAlokasi, spendingAllowanceId);
    }
    
    @Test
    void updateSpendingAllowance() {
        kategoriPemasukanController.updateKategoriPemasukan(workspaceId, idKategori,
                                                            namaKategori, besarAlokasi, spendingAllowanceId);
        verify(kategoriPemasukanService, times(1))
            .update(workspaceId, idKategori,
                    namaKategori, besarAlokasi, spendingAllowanceId);
    }
    
    @Test
    void getSpendingAllowance() {
        kategoriPemasukanController.getKategoriPemasukan(workspaceId);
        verify(kategoriPemasukanService, times(1))
            .getKategoriPemasukanByWorkspace(workspaceId);
    }
    
    @Test
    void deleteSpendingAllowanceFromWorkspace() {
        kategoriPemasukanController.deleteKategoriPemasukanFromWorkspace(workspaceId, idKategori);
        verify(kategoriPemasukanService, times(1))
            .deleteKategoriPemasukanFromWorkspace(workspaceId, idKategori);
    }
}