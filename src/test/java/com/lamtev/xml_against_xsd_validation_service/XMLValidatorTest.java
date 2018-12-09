package com.lamtev.xml_against_xsd_validation_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * XMLValidator unit test
 *
 * @author Anton Lamtev
 */
class XMLValidatorTest {

    @Test
    void test() {
        final var xml = getClass().getClassLoader().getResourceAsStream("xml.xml");
        final var xsd = getClass().getClassLoader().getResourceAsStream("xsd.xsd");

        assertNotNull(xml);
        assertNotNull(xsd);

        assertTrue(XMLValidator.isXMLValidAgainstXSD(xml, xsd));
    }

}
