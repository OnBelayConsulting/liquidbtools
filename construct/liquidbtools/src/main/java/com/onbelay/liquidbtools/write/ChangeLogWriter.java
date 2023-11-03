package com.onbelay.liquidbtools.write;

import com.onbelay.liquidbtools.model.*;
import com.onbelay.liquidbtools.read.ChangeLogContainer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

public class ChangeLogWriter {
    private static final String schemaLocation = "http://www.liquibase.org/xml/ns/dbchangelog-ext " +
            " http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd" +
            " http://www.liquibase.org/xml/ns/dbchangelog" +
            " http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.9.xsd";


    private String logicalFilePrefix="com.onbelay";
    private String applicationName;

    private OutputStream outputStream;

    public static ChangeLogWriter newWriter(String applicationName) {
        return new ChangeLogWriter(applicationName);
    }
    public static ChangeLogWriter newWriter(OutputStream outputStream) {
        return new ChangeLogWriter(outputStream);
    }


    protected ChangeLogWriter(String applicationName) {
        this.applicationName = applicationName;
    }

    protected ChangeLogWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ChangeLogWriter withLogicalFilePrefix(String prefix) {
        this.logicalFilePrefix = prefix;
        return this;
    }

    public void write(
            ChangeLogContainer container) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(
                DatabaseChangeLog.class,
                InsertNode.class,
                CreateViewNode.class,
                ColumnNode.class,
                CreateTableNode.class,
                CreateSequenceNode.class,
                AddForeignKeyConstraintNode.class,
                AddUniqueConstraintNode.class,
                AddPrimaryKeyNode.class,
                CreateIndexNode.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        if (container.getInsertMap().size() > 0) {
            for (String tableName: container.getInsertMap().keySet()) {
                List<InsertNode> insertNodes = container.getInsertMap().get(tableName);
                DatabaseChangeLog log = new DatabaseChangeLog();
                log.setLogicalFilePath(logicalFilePrefix +
                        "/" +
                        applicationName +
                        "/snapshot/dml/INSERT_" + tableName + ".xml");
                ChangeSet changeSet = new ChangeSet();
                String fileRef = "INSERT_" + tableName;
                log.addChangeSet(changeSet);
                changeSet.setId(fileRef);
                for (InsertNode node : insertNodes) {
                    changeSet.addNode(node);
                }
                writeChangeLog(
                        marshaller,
                        "./" + fileRef + ".xml",
                        log);
            }
        }

        if (container.getSequences().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/sequences/sequences.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("sequences");
            for (CreateSequenceNode node : container.getSequences()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./sequences.xml",
                    log);
        }

        if (container.getTables().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/tables/tables.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("tables");
            for (CreateTableNode node : container.getTables()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./tables.xml",
                    log);
        }

        if (container.getCodeTables().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/tables/codes.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("codes");
            for (CreateTableNode node : container.getCodeTables()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./codes.xml",
                    log);
        }


        if (container.getForeignConstraints().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/constraints/foreignkeyconstraints.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("fkconstraints");
            for (AddForeignKeyConstraintNode node : container.getForeignConstraints()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./foreignKeyConstraints.xml",
                    log);
        }


        if (container.getUniqueConstraints().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/constraints/uniqueconstraints.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("uniqueconstraints");
            for (AddUniqueConstraintNode node : container.getUniqueConstraints()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./uniqueConstraints.xml",
                    log);
        }


        if (container.getIndices().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/indices/indices.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("indices");
            for (CreateIndexNode node : container.getIndices()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./indices.xml",
                    log);
        }


        if (container.getPrimaryKeys().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/snapshot/ddl/constraints/primarykeys.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setId("keys");
            for (AddPrimaryKeyNode node : container.getPrimaryKeys()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./primarykeys.xml",
                    log);
        }


        if (container.getViews().size() > 0) {
            DatabaseChangeLog log = new DatabaseChangeLog();
            log.setLogicalFilePath(logicalFilePrefix +
                    "/" +
                    applicationName +
                    "/runalways/postchange/views/views.xml");
            ChangeSet changeSet = new ChangeSet();
            log.addChangeSet(changeSet);
            changeSet.setRunOnChange(true);
            changeSet.setRunAlways(true);
            changeSet.setId("views");
            for (CreateViewNode node : container.getViews()) {

                changeSet.addNode(node);
            }
            writeChangeLog(
                    marshaller,
                    "./views.xml",
                    log);
        }


    }

    private void writeChangeLog(
            Marshaller marshaller,
            String fileName,
            DatabaseChangeLog changeLog) throws JAXBException {

        if (outputStream != null)
            marshaller.marshal(changeLog, outputStream);
        else
            marshaller.marshal(changeLog, new File(fileName));
    }

}
