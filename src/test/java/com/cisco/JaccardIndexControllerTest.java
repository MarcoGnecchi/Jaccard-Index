package com.cisco;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JaccardIndexControllerTest {

    public static final String HTTP_WWW_BBC_CO_UK = "http://www.bbc.co.uk";
    public static final String HTTP_WWW_GOOGLE_COM = "http://www.google.com";
    public static final String BBC_TEXT_CONTENT = "<body>BBC<body>";
    public static final String GOOGLE_TEXT_CONTENT = "<body>Google<body>";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JaccardIndexService jaccardIndexService;

    @MockBean
    private TextDownloaderService textDownloader;

    @Before
    public void setUp() throws MalformedURLException {
        when(textDownloader.fetchText(new URL(HTTP_WWW_BBC_CO_UK))).thenReturn(BBC_TEXT_CONTENT);
        when(textDownloader.fetchText(new URL(HTTP_WWW_GOOGLE_COM))).thenReturn(GOOGLE_TEXT_CONTENT);
    }

    @Test
    public void shouldComputeJaccardIndex() throws Exception {
        mockMvc.perform(get("/").param("url1", "http://www.google.com").param("url2", "http://www.bbc.co.uk"))
                .andExpect(status().is2xxSuccessful());
        verify(jaccardIndexService, times(1)).perform(GOOGLE_TEXT_CONTENT, BBC_TEXT_CONTENT);
    }

    @Test
    public void shouldValidateUrls() throws Exception {
        mockMvc.perform(get("/").param("url1", "test").param("url2", "test")).andExpect(status().is4xxClientError());
        verifyZeroInteractions(jaccardIndexService);
    }

    @Test
    public void shouldFetchURLsContent() throws Exception {
        mockMvc.perform(get("/").param("url1", "http://www.google.com").param("url2", "http://www.bbc.co.uk"));
        verify(jaccardIndexService, times(1)).perform(GOOGLE_TEXT_CONTENT, BBC_TEXT_CONTENT);

    }

    @Test
    public void shouldReturnJaccardIndex() throws Exception {
        when(jaccardIndexService.perform(any(), any())).thenReturn(1);
        mockMvc.perform(get("/").param("url1", "http://www.google.com").param("url2", "http://www.bbc.co.uk"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("1"));
    }

}