package com.lamtev.xml_against_xsd_validation_service;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


/**
 * XML-against-XSD-validation-service API.
 * <p>
 * Service waits for HTTP POST requests by path "/validate"
 * and then handles them using {@link #validate(Part, Part)} method.
 *
 * @author Anton Lamtev
 */
@RestController
@RequestMapping("/validate")
public final class Api {

    private final static String XML_IS_VALID_JSON = "{\"valid\" : \"true\"}";
    private final static String XML_IS_NOT_VALID_JSON = "{\"valid\" : \"false\"}";

    /**
     * HTTP POST requests handler.
     * <p>
     * Request format:
     * Content-Type:    multipart/form-data
     * Body:            key=xml, value=*.xml; key=xsd, value=*.xsd.
     * <p>
     * Request format:
     * Content-Type:    application/json;charset=UTF-8
     * Status-Code:     202 (Accepted)
     * Body:            json with field "valid" equal to "true" or "false".
     *
     * @param xml XML file - a part of multipart/form-data request body. Value for key "xml".
     * @param xsd XSD File - a part of multipart/form-data request body. Value for key "xsd".
     * @return json with field "valid" equal to "true" if XML file is valid and "false" otherwise.
     */
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = ACCEPTED)
    public String validate(@RequestPart("xml") final Part xml,
                           @RequestPart("xsd") final Part xsd) {
        if (isNull(xml) || isNull(xsd)) {
            return XML_IS_NOT_VALID_JSON;
        }

        boolean xmlIsValid;
        try (final var xmlInputStream = xml.getInputStream();
             final var xsdInput = xsd.getInputStream()) {
            xmlIsValid = XMLValidator.isXMLValidAgainstXSD(xmlInputStream, xsdInput);
        } catch (IOException e) {
            xmlIsValid = false;
        }

        return xmlIsValid ? XML_IS_VALID_JSON : XML_IS_NOT_VALID_JSON;
    }

}
