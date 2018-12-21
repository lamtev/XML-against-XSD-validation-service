package com.lamtev.xml_against_xsd_validation_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Service API integration test.
 *
 * @author Anton Lamtev
 */
@SpringBootTest
@ContextConfiguration(classes = Api.class)
@AutoConfigureMockMvc
class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testStatus() throws Exception {
        mockMvc.perform(get("/status"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("OK")));
    }

    @Test
    void testValidate() throws Exception {
        final var xml = getClass().getClassLoader().getResourceAsStream("xml.xml");
        final var xsd = getClass().getClassLoader().getResourceAsStream("xsd.xsd");

        assertNotNull(xml);
        assertNotNull(xsd);

        mockMvc.perform(multipart("/validate").part(
                new MockPart("xml", "xml.xml", xml.readAllBytes()),
                new MockPart("xsd", "xsd.xsd", xsd.readAllBytes())))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.valid", is("true")));

        mockMvc.perform(multipart("/validate").part(
                new MockPart("xml", "xml.xml", "badValue".getBytes(UTF_8)),
                new MockPart("xsd", "xsd.xsd", "badValue".getBytes(UTF_8))))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.valid", is("false")));

        mockMvc.perform(multipart("/validate").part(
                new MockPart("not_xml", null, null),
                new MockPart("not_xsd", null, null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
