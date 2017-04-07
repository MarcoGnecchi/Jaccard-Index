package com.cisco;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(JaccardIndexController.class)
public class JaccardIndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JaccardIndexService jaccardIndexService;

    @Test
    public void shouldComputeJaccardIndex() throws Exception {
        mockMvc.perform(get("/").param("url1", "http://www.google.com").param("url2", "http://www.bbc.co.uk"))
                .andExpect(status().is2xxSuccessful());
        verify(jaccardIndexService, times(1)).perform(new URL("http://www.google.com"),
                new URL("http://www.bbc.co.uk"));
    }

    @Test
    public void shouldValidateUrls() throws Exception {
        mockMvc.perform(get("/").param("url1", "test").param("url2", "test")).andExpect(status().is4xxClientError());
        verifyZeroInteractions(jaccardIndexService);
    }

    @Test
    public void shouldReturnJaccardIndex() throws Exception {
        when(jaccardIndexService.perform(any(), any())).thenReturn(1);

        mockMvc.perform(get("/").param("url1", "http://www.google.com").param("url2", "http://www.bbc.co.uk"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("1"));
    }

}