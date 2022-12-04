package xyz.rpletsgo.tagihan.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import xyz.rpletsgo.budgeting.controller.SpendingAllowanceController;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.tagihan.service.TagihanService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TagihanControllerTest {
    @MockBean
    private TagihanService tagihanService;

    @InjectMocks
    private TagihanController tagihanController;

    @Mock
    private Tagihan tagihanMock;

    private String workspaceIdDum =  "1";
    private String tagihanIdDum =  "2";
    private String namaDum =  "a";
    private String keteranganDum =  "h";
    private String waktuStrDum =  "2022-01-02";
    private long nominalDum = 100;
    private LocalDateTime waktuDum = LocalDateTime.of(2022, 1, 2, 0, 0);

    @BeforeEach
    void setUp(){
        tagihanService = mock(TagihanService.class);
        tagihanMock = mock(Tagihan.class);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTagihan() {
        tagihanController.createTagihan(workspaceIdDum, namaDum, keteranganDum, waktuStrDum, nominalDum);

        verify(tagihanService, times(1)).create(workspaceIdDum, namaDum, keteranganDum, waktuDum, nominalDum);
    }

    @Test
    void testUpdateTagihan() {
        tagihanController.updateTagihan(workspaceIdDum, tagihanIdDum, namaDum, keteranganDum, waktuStrDum, nominalDum);

        verify(tagihanService, times(1)).update(workspaceIdDum, tagihanIdDum, namaDum, keteranganDum, waktuDum, nominalDum);
    }

    @Test
    void testDeleteTagihan() {
        tagihanController.deleteTagihan(workspaceIdDum, tagihanIdDum);

        verify(tagihanService, times(1)).deleteTagihan(workspaceIdDum, tagihanIdDum);
    }

    @Test
    void testGetListTagihan(){
        List<FinancialEvent> list = new ArrayList<>();
        list.add(tagihanMock);
        when(tagihanService.getListTagihan(workspaceIdDum)).thenReturn(list);

        var hasil = tagihanController.getTagihan(workspaceIdDum);
        assertEquals(hasil, list);
        verify(tagihanService, times(1)).getListTagihan(workspaceIdDum);

    }

    @Test
    void testGetTagihan(){
        when(tagihanService.getTagihan(workspaceIdDum, tagihanIdDum)).thenReturn(tagihanMock);

        var hasil = tagihanController.getTagihan(workspaceIdDum, tagihanIdDum);
        assertEquals(hasil, tagihanMock);
        verify(tagihanService, times(1)).getTagihan(workspaceIdDum, tagihanIdDum);

    }



//    @Test
//    void testCreate() throws Exception{
//        doNothing().when(tagihanService).create(workspaceIdDum, namaDum, keteranganDum, waktuDum, nominalDum);
//
//        mockMvc.perform(post("/1/tagihan/create")
//                        .contentType(MediaType.APPLICATION_JSON).content(String.format("{\"nama\":\"{}\",\"keterangan\":\"{}\",\"waktuStr\":\"{}\",\"nominal\":\"{}\"}", namaDum, keteranganDum, waktuStrDum, nominalDum)))
//                .andExpect(content().string(containsString("success")));
//    }
//    @Test
//    void testUpdate() throws Exception{
//        doNothing().when(tagihanService).create(workspaceIdDum, namaDum, keteranganDum, waktuDum, nominalDum);
//
//        mockMvc.perform(post("/1/tagihan/create")
//                        .contentType(MediaType.APPLICATION_JSON).content(String.format("{\"nama\":\"{}\",\"keterangan\":\"{}\",\"waktuStr\":\"{}\",\"nominal\":\"{}\"}", namaDum, keteranganDum, waktuStrDum, nominalDum)))
//                .andExpect(content().string(containsString("success")));
//    }
}
