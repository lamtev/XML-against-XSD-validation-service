package com.lamtev.xml_against_xsd_validation_service;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;


/**
 * XMLValidator - validates XML against XSD
 *
 * @author Anton Lamtev
 */
final class XMLValidator {
    /**
     * Validates XML against XSD both passed as {@link InputStream}
     *
     * @param xml {@link InputStream} instance containing XML file
     * @param xsd {@link InputStream} instance containing XSD file
     * @return {@code true} if XML is valid against XSD and false otherwise
     */
    static boolean isXMLValidAgainstXSD(@NotNull final InputStream xml,
                                        @NotNull final InputStream xsd) {
        final var xsdSource = new StreamSource(xsd);
        final var xmlSource = new StreamSource(xml);
        try {
            final var schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(xsdSource);
            schema.newValidator().validate(xmlSource);
        } catch (SAXException | IOException e) {
            return false;
        }

        return true;
    }
}
