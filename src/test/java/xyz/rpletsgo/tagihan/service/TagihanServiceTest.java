package xyz.rpletsgo.tagihan.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.tagihan.model.TagihanFactory;
import xyz.rpletsgo.tagihan.repository.TagihanRepository;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class TagihanServiceTest {
    @MockBean
    CurrentLoggedInPengguna loggedInPengguna;
    @MockBean
    WorkspaceRepository workspaceRepository;
    @MockBean
    TagihanRepository tagihanRepository;
    
    @InjectMocks
    TagihanService tagihanService;
    
    Workspace workspace;
    String workspaceId = "a";
    TagihanFactory tagihanFactory;
    Tagihan tagihan;
    LocalDateTime waktu = LocalDateTime.of(2000, 1, 1, 1, 1 );
    long nominal =  10;
    String namaTagihan = "b";
    String keteranganTagihan = "c";
    String tagihanId = "d";
    
    @BeforeEach
    void setup(){
        loggedInPengguna = mock(CurrentLoggedInPengguna.class);
        workspaceRepository = mock(WorkspaceRepository.class);
        tagihanRepository = mock(TagihanRepository.class);
    
        workspace = mock(Workspace.class);
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        
        tagihanFactory = mock(TagihanFactory.class);
        tagihan = mock(Tagihan.class);
        when(tagihanFactory.create(waktu)).thenReturn(tagihan);
    
        MockitoAnnotations.openMocks(this);
    }
    
    
    @Test
    void create() {
        tagihanService.create(workspaceId, namaTagihan, keteranganTagihan, waktu, nominal);
        
        verify(tagihan, times(1)).updateValue(namaTagihan, keteranganTagihan, waktu, nominal);
        verify(tagihanRepository, times(1)).saveAndFlush(tagihan);
        verify(workspaceRepository, times(1)).saveAndFlush(workspace);
    }
    
    @Test
    void update() {
        when(tagihanRepository.findById(tagihanId)).thenReturn(Optional.of(tagihan));
        tagihanService.update(workspaceId, tagihanId, namaTagihan, keteranganTagihan, waktu, nominal);
        
        verify(tagihanRepository, times(1)).saveAndFlush(tagihan);
        verify(workspaceRepository, times(1)).saveAndFlush(workspace);
    }
    
    @Test
    void deleteTagihan() {
        when(tagihanRepository.findById(tagihanId)).thenReturn(Optional.of(tagihan));
        tagihanService.deleteTagihan(workspaceId, tagihanId);
    
        verify(workspace, times(1)).deleteFinancialEventOrThrow(tagihanId);
        verify(tagihanRepository, times(1)).deleteById(tagihanId);
        verify(workspaceRepository, times(1)).saveAndFlush(workspace);
    }
    
    @Test
    void getTagihan() {
        when(tagihanRepository.findById(tagihanId)).thenReturn(Optional.of(tagihan));
        var res = tagihanService.getTagihan(workspaceId, tagihanId);
    
        assertSame(tagihan, res);
    }
    
    @Test
    void getTagihanList() {
        var tagihan2 = mock(Tagihan.class);
        List<FinancialEvent> listOfTagihan = List.of(tagihan, tagihan2);
        when(workspace.getTagihan()).thenReturn(listOfTagihan);
        var res = tagihanService.getListTagihan(workspaceId);
    
        assertSame(listOfTagihan, res);
    }
}