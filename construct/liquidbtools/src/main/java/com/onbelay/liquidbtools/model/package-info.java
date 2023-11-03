@XmlSchema
        (
                namespace="http://www.liquibase.org/xml/ns/dbchangelog",
                elementFormDefault= XmlNsForm.QUALIFIED,
                xmlns=
                        {
                                @XmlNs(namespaceURI = "http://www.w3.org/2001/XMLSchema-instance",    prefix = "xsi"),
                        }
        )
package com.onbelay.liquidbtools.model;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;