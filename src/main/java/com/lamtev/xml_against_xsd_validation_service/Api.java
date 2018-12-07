package com.lamtev.xml_against_xsd_validation_service;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Rest controller waiting HTTP POST requests with path /validate
 */
@RestController
@RequestMapping("/validate")
public final class Api {
    /**
     * HTTP POST requests handler.
     * <p>
     * Request format:
     * Content-Type:    multipart/form-data
     * Body:            key=xml, value=*.xml; key=xsd, value=*.xsd.
     * <p>
     * Request format:
     * Content-Type:    application/json
     * Body:            single boolean value.
     *
     * @param xml XML file - a part of multipart/form-data request body
     * @param xsd XSD File - a part of multipart/form-data request body
     * @return true if XML file is valid and false otherwise
     */
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = OK)
    public boolean validate(@RequestPart("xml") final Part xml,
                            @RequestPart("xsd") final Part xsd) {
        if (isNull(xml) || isNull(xsd)) {
            return false;
        }

        try (final var xmlInputStream = xml.getInputStream();
             final var xsdInput = xsd.getInputStream()) {
            return XMLValidator.isXMLValidAgainstXSD(xmlInputStream, xsdInput);
        } catch (IOException e) {
            return false;
        }
    }

}
