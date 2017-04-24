package com.cisco;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TextDownloaderServiceTest {

    public static final String HTML_CONTENT = "<h1>HTML Ipsum Presents</h1>\n" + "\n"
            + "<p><strong>Pellentesque habitant morbi tristique</strong> senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. <em>Aenean ultricies mi vitae est.</em> Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, <code>commodo vitae</code>, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href=\"#\">Donec non enim</a> in turpis pulvinar facilisis. Ut felis.</p>\n"
            + "\n" + "<h2>Header Level 2</h2>\n" + "\n" + "<ol>\n"
            + "   <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>\n"
            + "   <li>Aliquam tincidunt mauris eu risus.</li>\n" + "</ol>\n" + "\n"
            + "<blockquote><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est.</p></blockquote>\n"
            + "\n" + "<h3>Header Level 3</h3>\n" + "\n" + "<ul>\n"
            + "   <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>\n"
            + "   <li>Aliquam tincidunt mauris eu risus.</li>\n" + "</ul>\n" + "\n" + "<pre><code>"
            + "</code></pre>";

    public static final String TEXT_CONTENT = "HTML Ipsum Presents Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Header Level 2 Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam tincidunt mauris eu risus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est. Header Level 3 Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam tincidunt mauris eu risus.";


    @Autowired
    TextDownloaderService textDownloaderService;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void shouldFetchTheContendOfTheRequest() throws MalformedURLException, URISyntaxException, TextFetchingException {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(ExpectedCount.once(), requestTo("http://test")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(HTML_CONTENT, MediaType.TEXT_HTML));

        // Http request to URL provided
        assertThat(textDownloaderService.fetchText(new URL("http://test")), is(TEXT_CONTENT));
        // Verify all expectations met
        server.verify();
    }
}