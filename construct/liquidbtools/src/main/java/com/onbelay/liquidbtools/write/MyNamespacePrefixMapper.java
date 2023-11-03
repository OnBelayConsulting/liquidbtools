package com.onbelay.liquidbtools.write;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

public class MyNamespacePrefixMapper extends NamespacePrefixMapper {
    private static final String[] namespaces = {
            "http://www.liquibase.org/xml/ns/dbchangelog",
            "http://www.w3.org/2001/XMLSchema-instance"
    };

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return null;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris2() {
        return namespaces;
    }
}
