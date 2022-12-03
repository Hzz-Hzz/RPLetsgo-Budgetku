package xyz.rpletsgo.workspace.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.repository.PenggunaRepository;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.workspace.core.WorkspaceFactory;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class WorkspaceServiceTest {
    @MockBean
    WorkspaceRepository workspaceRepository;
    @MockBean
    PenggunaRepository penggunaRepository;
    @MockBean
    KategoriPemasukanRepository kategoriPemasukanRepository;
    @MockBean
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    @MockBean
    SpendingAllowanceRepository spendingAllowanceRepository;
    @MockBean
    CurrentLoggedInPengguna loggedInPengguna;
    
    @InjectMocks
    WorkspaceService workspaceService;
    
    
    String workspaceName = "a";
    String workspaceId = "b";
    Workspace workspace;
    Pengguna pengguna;
    SpendingAllowance spendingAllowance;
    KategoriPemasukan kategoriPemasukan;
    AlokasiSpendingAllowance alokasi;
    
    WorkspaceFactory workspaceFactory;
    
    
    @BeforeEach
    void setup(){
        initializeMocks();
        MockitoAnnotations.openMocks(this);
        workspaceService.setWorkspaceFactory(workspaceFactory);
    }
    
    void initializeMocks(){
        workspaceRepository = mock(WorkspaceRepository.class);
        penggunaRepository = mock(PenggunaRepository.class);
        kategoriPemasukanRepository = mock(KategoriPemasukanRepository.class);
        alokasiSpendingAllowanceRepository = mock(AlokasiSpendingAllowanceRepository.class);
        spendingAllowanceRepository = mock(SpendingAllowanceRepository.class);
        loggedInPengguna = mock(CurrentLoggedInPengguna.class);
    
        pengguna = mock(Pengguna.class);
        workspace = mock(Workspace.class);
        spendingAllowance = mock(SpendingAllowance.class);
        kategoriPemasukan = mock(KategoriPemasukan.class);
        alokasi = mock(AlokasiSpendingAllowance.class);
        workspaceFactory = mock(WorkspaceFactory.class);
    
        when(loggedInPengguna.getCurrentPengguna()).thenReturn(pengguna);
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        when(workspaceFactory.create(workspaceName)).thenReturn(workspace);
    
        when(workspace.getKategoriPemasukan()).thenReturn(List.of(kategoriPemasukan));
        when(workspace.getSpendingAllowances()).thenReturn(List.of(spendingAllowance));
        when(workspace.getAlokasiSpendingAllowances()).thenReturn(List.of(alokasi));
    }
    
    
    @Test
    void createWorkspace() {
        var workspace = workspaceService.createWorkspace(workspaceName);
        
        verify(alokasiSpendingAllowanceRepository, times(1)).saveAllAndFlush(List.of(alokasi));
        verify(spendingAllowanceRepository, times(1)).saveAllAndFlush(List.of(spendingAllowance));
        verify(kategoriPemasukanRepository, times(1)).saveAllAndFlush(List.of(kategoriPemasukan));
        
        verify(workspaceRepository, times(1)).save(workspace);
        verify(pengguna, times(1)).addToCreatedWorkspaces(workspace);
        verify(penggunaRepository, times(1)).save(pengguna);
    }
    
    @Test
    void updateWorkspace() {
        String newName = "newName";
        workspaceService.updateWorkspace(workspaceId, newName);
        verify(workspace, times(1)).setNama(newName);
    }
    
    @Test
    void getWorkspace() {
        var res = workspaceService.getWorkspace(workspaceId);
        assertSame(workspace, res);
    }
    
    @Test
    void getWorkspaces() {
        var workspaces = List.of(workspace, mock(Workspace.class));
        when(pengguna.getCreatedWorkspaces()).thenReturn(workspaces);
        
        var res = workspaceService.getWorkspaces();
        assertSame(workspaces, res);
    }
    
    @Test
    void deleteWorkspace() {
        workspaceService.deleteWorkspace(workspaceId);
        
        verify(loggedInPengguna, times(1)).authorizeWorkspace(workspaceId);
        verify(workspaceRepository, times(1)).deleteById(workspaceId);
    }
}