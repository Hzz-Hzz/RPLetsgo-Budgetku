package xyz.rpletsgo.budgeting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.exceptions.UnauthorizedAccessException;
import xyz.rpletsgo.budgeting.core.AlokasiSpendingAllowanceFactory;
import xyz.rpletsgo.budgeting.core.KategoriPemasukanFactory;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class KategoriPemasukanServiceTest {
    @InjectMocks
    KategoriPemasukanService kategoriPemasukanService;
    
    @MockBean
    CurrentLoggedInPengguna loggedInPengguna;
    @MockBean
    WorkspaceRepository workspaceRepository;
    
    @MockBean
    KategoriPemasukanRepository kategoriPemasukanRepository;
    @MockBean
    SpendingAllowanceRepository spendingAllowanceRepository;
    @MockBean
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    
    AlokasiSpendingAllowanceFactory alokasiSpendingAllowanceFactory;
    KategoriPemasukanFactory kategoriPemasukanFactory;
    
    Workspace workspace;
    KategoriPemasukan kategoriPemasukan;
    String kategoriPemasukanId = "kt-1";
    String namaKategoriPemasukan = "nm-kt-1";
    SpendingAllowance spendingAllowance1;
    SpendingAllowance spendingAllowance2;
    String spendingAllowanceId1 = "sp-1";
    String spendingAllowanceId2 = "sp-2";
    
    AlokasiSpendingAllowance alokasiSpendingAllowance1;
    AlokasiSpendingAllowance alokasiSpendingAllowance2;
    List<AlokasiSpendingAllowance> alokasiSpendingAllowances;
    
    
    
    @BeforeEach
    void setup(){
        initializeMocks();
        initializeOther();
    }
    
    void initializeMocks(){
        loggedInPengguna = mock(CurrentLoggedInPengguna.class);
        workspaceRepository = mock(WorkspaceRepository.class);
        kategoriPemasukanRepository = mock(KategoriPemasukanRepository.class);
        spendingAllowanceRepository = mock(SpendingAllowanceRepository.class);
        alokasiSpendingAllowanceRepository = mock(AlokasiSpendingAllowanceRepository.class);
        alokasiSpendingAllowanceFactory = mock(AlokasiSpendingAllowanceFactory.class);
        kategoriPemasukanFactory = mock(KategoriPemasukanFactory.class);
        
        workspace = mock(Workspace.class);
        kategoriPemasukan = mock(KategoriPemasukan.class);
        spendingAllowance1 = mock(SpendingAllowance.class);
        alokasiSpendingAllowance1 = mock(AlokasiSpendingAllowance.class);
        when(spendingAllowance1.getId()).thenReturn(spendingAllowanceId1);
        
        spendingAllowance2 = mock(SpendingAllowance.class);
        alokasiSpendingAllowance2 = mock(AlokasiSpendingAllowance.class);
        when(spendingAllowance2.getId()).thenReturn(spendingAllowanceId2);
    }
    
    void initializeOther(){
        besarAlokasi = new Double[]{0.25, 0.75};
        spendingAllowanceIds = new String[]{spendingAllowanceId1, spendingAllowanceId2};
        spendingAllowances = List.of(spendingAllowance1, spendingAllowance2);
        alokasiSpendingAllowances = List.of(alokasiSpendingAllowance1, alokasiSpendingAllowance2);
    
        MockitoAnnotations.openMocks(this);
    
        kategoriPemasukanService.setKategoriPemasukanFactory(kategoriPemasukanFactory);
        kategoriPemasukanService.setAlokasiSpendingAllowanceFactory(alokasiSpendingAllowanceFactory);
    }
    
    
    
    Double[] besarAlokasi;
    List<SpendingAllowance> spendingAllowances;
    String[] spendingAllowanceIds;
    
    
    @Test
    void create_throwIfNotAuthorized() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenThrow(UnauthorizedAccessException.class);
        
        assertThrows(UnauthorizedAccessException.class,
                     () -> kategoriPemasukanService.create("a", namaKategoriPemasukan,
                                                           besarAlokasi, spendingAllowanceIds));
    }
    @Test
    void create_throwIfSpendingAllowanceNotFound() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenReturn(workspace);
        when(workspace.getSpendingAllowanceOrThrow(
            Arrays.asList(spendingAllowanceIds)
        )).thenThrow(SpendingAllowanceNotFoundException.class);
        
        assertThrows(SpendingAllowanceNotFoundException.class,
                     () -> kategoriPemasukanService.create("a", namaKategoriPemasukan,
                                                           besarAlokasi, spendingAllowanceIds));
    }
    
    
    @Test
    void create_runSuccessfully() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenReturn(workspace);
        
        when(workspace.getSpendingAllowanceOrThrow(
            Arrays.asList(spendingAllowanceIds)
        )).thenReturn(spendingAllowances);
        
        when(alokasiSpendingAllowanceFactory.create(
            spendingAllowances, Arrays.asList(besarAlokasi)
        )).thenReturn(alokasiSpendingAllowances);
        
        when(kategoriPemasukanFactory.create(namaKategoriPemasukan)).thenReturn(
            kategoriPemasukan
        );
    
    
        var res = kategoriPemasukanService
            .create("a", namaKategoriPemasukan, besarAlokasi, spendingAllowanceIds);
        
        
        verify(kategoriPemasukan, times(1)).setAlokasiSpendingAllowances(alokasiSpendingAllowances);
        verify(alokasiSpendingAllowanceRepository, times(1)).saveAllAndFlush(alokasiSpendingAllowances);
        verify(kategoriPemasukanRepository, times(1)).saveAndFlush(kategoriPemasukan);
        verify(workspace, times(1)).addKategoriPemasukan(kategoriPemasukan);
        verify(workspaceRepository, times(1)).save(workspace);
        assertSame(kategoriPemasukan, res);
    }
    
    
    @Test
    void update_throwsIfIdNotFound() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenReturn(workspace);
    
        when(workspace.getSpendingAllowanceOrThrow(
            Arrays.asList(spendingAllowanceIds)
        )).thenReturn(spendingAllowances);
    
        when(alokasiSpendingAllowanceFactory.create(
            spendingAllowances, Arrays.asList(besarAlokasi)
        )).thenReturn(alokasiSpendingAllowances);
    
        when(kategoriPemasukanRepository.findById(
            kategoriPemasukanId
        )).thenReturn(Optional.empty());
    
    
        assertThrows(KategoriPemasukanNotFoundException.class,
                     () -> {
                         kategoriPemasukanService
                             .update("a", kategoriPemasukanId, namaKategoriPemasukan,
                                     besarAlokasi, spendingAllowanceIds);
        });
    }
    
    @Test
    void update_runSuccessfully() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenReturn(workspace);
        
        when(workspace.getSpendingAllowanceOrThrow(
            Arrays.asList(spendingAllowanceIds)
        )).thenReturn(spendingAllowances);
    
        when(alokasiSpendingAllowanceFactory.create(
            spendingAllowances, Arrays.asList(besarAlokasi)
        )).thenReturn(alokasiSpendingAllowances);
        
        when(kategoriPemasukanRepository.findById(
            kategoriPemasukanId
        )).thenReturn(Optional.ofNullable(kategoriPemasukan));
        
        
        var res = kategoriPemasukanService
            .update("a", kategoriPemasukanId, namaKategoriPemasukan,
                    besarAlokasi, spendingAllowanceIds);
        
        
        verify(kategoriPemasukan, times(1)).setNama(namaKategoriPemasukan);
        verify(kategoriPemasukan, times(1)).setAlokasiSpendingAllowances(alokasiSpendingAllowances);
        
        verify(alokasiSpendingAllowanceRepository, times(1)).saveAllAndFlush(alokasiSpendingAllowances);
        verify(kategoriPemasukanRepository, times(1)).saveAndFlush(kategoriPemasukan);
        assertSame(kategoriPemasukan, res);
    }
    
    
    
    
    @Test
    void getKategoriPemasukanByWorkspace_runSuccessfully() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenReturn(workspace);
        
        var listOfKategori = List.of(kategoriPemasukan);
        when(workspace.getKategoriPemasukan()).thenReturn(listOfKategori);
        
        var res = kategoriPemasukanService.getKategoriPemasukanByWorkspace("a");
        assertSame(listOfKategori, res);
    }
    
    
    @Test
    void deleteKategoriPemasukanFromWorkspace_runSuccessfully() {
        when(loggedInPengguna.authorizeWorkspace("a")).thenReturn(workspace);
        
        var listOfKategori = List.of(kategoriPemasukan);
        
        kategoriPemasukanService
            .deleteKategoriPemasukanFromWorkspace("a", kategoriPemasukanId);
        
        verify(workspace, times(1))
            .removeKategoriPemasukan(kategoriPemasukanId);
    }
}