package com.epam.bar.controller.filter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotEquals;

public class XssRequestWrapperTest {
    private XssRequestWrapper wrapper;
    private HttpServletRequest request;
    private List<String> onlyScript;
    private List<String> scriptWithRequest;

    @BeforeEach
    public void setUp() {
        onlyScript = new ArrayList<>();
        onlyScript.add("<script>");
        onlyScript.add("<script>alert(‘XSS’)</script>");
        onlyScript.add("<script>alert(document.cookie)</script>");
        scriptWithRequest = new ArrayList<>();
        scriptWithRequest.add("<body onload=alert(‘something’)>;");
        scriptWithRequest.add("<script type=”text/javascript”>var test=’../example.php?cookie_data=" +
                "’+escape(document.cookie);</script>");
        scriptWithRequest.add("<script>destroyWebsite();</script>");
    }

    @Test
    public void stripXSSShouldReturnEmpty() {
        request = mock(HttpServletRequest.class);
        wrapper = new XssRequestWrapper(request);
        StringBuilder actual = new StringBuilder();
        for (String param : onlyScript) {
            actual.append(wrapper.stripXSS(param));
        }
        assertEquals(actual.toString(), "");
    }

    @Test
    public void stripXSSShouldReturnChanged() {
        request = mock(HttpServletRequest.class);
        wrapper = new XssRequestWrapper(request);
        StringBuilder actual = new StringBuilder();
        for (String param : scriptWithRequest) {
            actual.append(wrapper.stripXSS(param));
        }
        assertNotEquals(actual.toString(), "");
    }

    @AfterEach
    public void tearDown() {
        wrapper = null;
        request = null;
        onlyScript = null;
        scriptWithRequest = null;
    }
}