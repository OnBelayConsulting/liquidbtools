package com.onbelay.liquidbtools.model;

import com.onbelay.liquidbtools.read.ChangeLogContainer;

import java.util.ArrayList;
import java.util.List;

public class ChangeLogContainerFixture {

    private static final String DATA_TABLESPACE_NAME = "${tblspace_data}";
    private static final String INDEX_TABLESPACE_NAME ="${tblspace_data_idx}";


    public static ChangeLogContainer createContainerWithTableNode() {

        ChangeLogContainer container = new ChangeLogContainer();

        container.getTables().add(
                createTableNode("MY_TABLE")
        );

        return container;
    }

    public static ChangeLogContainer createContainerWithUniqueConstraintNode() {

        ChangeLogContainer container = new ChangeLogContainer();
        container.getUniqueConstraints().add(
                createUniqueConstraint("MY_FOR_UC")
        );

        return container;
    }


    public static ChangeLogContainer createContainerWithForeignKeyConstraintNode() {

        ChangeLogContainer container = new ChangeLogContainer();

        container.getForeignConstraints().add(
                createForeignKey("MY_FOR_KEY")
        );

        return container;
    }

    public static ChangeLogContainer createContainerWithCreateIndexNode() {

        ChangeLogContainer container = new ChangeLogContainer();

        container.getIndices().add(
                createIndexNode("TABLE_IDX")
        );

        return container;
    }

    public static ChangeLogContainer createContainerWithCreateSequenceNode() {

        ChangeLogContainer container = new ChangeLogContainer();

        container.getSequences().add(
                createSequence("TABLE_SQ")
        );

        return container;
    }


    public static  ChangeLogContainer createContainerWithInsertNode() {
        ChangeLogContainer container = new ChangeLogContainer();

        ArrayList<InsertNode> insertList = new ArrayList<>();
        InsertNode insertNode = new InsertNode();
        insertNode.setTableName("BUY_SELL_CODE");

        insertNode.addColumnNode(
                newInsertColumn("CODE_CD", "B"));
        insertNode.addColumnNode(
                newInsertColumn("CODE_LABEL", "Buy"));
        insertNode.addColumnNode(
                newInsertColumn("DISPLAY_ORDER_NO", "10"));
        insertList.add(insertNode);

        insertNode = new InsertNode();
        insertNode.setTableName("BUY_SELL_CODE");

        insertNode.addColumnNode(
                newInsertColumn("CODE_CD", "S"));
        insertNode.addColumnNode(
                newInsertColumn("CODE_LABEL", "Sell"));
        insertNode.addColumnNode(
                newInsertColumn("DISPLAY_ORDER_NO", "10"));
        insertList.add(insertNode);

        container.getInsertMap().put("BUY_SELL_CODE", insertList);

        return container;
    }

    private static ColumnNode newInsertColumn(String name, String value) {
        ColumnNode columnNode = new ColumnNode();
        columnNode.setName(name);
        columnNode.setValue(value);
        return columnNode;
    }

    public static AddForeignKeyConstraintNode createForeignKey(String name) {
        AddForeignKeyConstraintNode node = new AddForeignKeyConstraintNode();
        node.setConstraintName(name);
        node.setDeferrable(false);
        node.setInitiallyDeferred(false);
        node.setOnDelete("NO ACTION");
        node.setOnUpdate("NO ACTION");
        node.setBaseColumnNames("COL_1");
        node.setBaseTableName("MY_TABLE");
        node.setReferencedColumnNames("OTHER_PK");
        node.setReferencedTableName("OTHER_TABLE");
        node.setValidate(true);
        return node;
    }

    public static AddUniqueConstraintNode createUniqueConstraint(String name) {
        AddUniqueConstraintNode node = new AddUniqueConstraintNode();
        node.setConstraintName(name);
        node.setColumnNames("MY_COL,MY_COL2");
        node.setTableName("MY_TABLE");
        return node;
    }

    public static CreateSequenceNode createSequence(String name) {
        CreateSequenceNode createSequenceNode = new CreateSequenceNode();
        createSequenceNode.setSequenceName(name);
        createSequenceNode.setIncrementBy(1);
        createSequenceNode.setCycle(false);
        createSequenceNode.setStartValue(1);
        return createSequenceNode;
    }

    public static CreateIndexNode createIndexNode(String name) {
        CreateIndexNode createIndexNode = new CreateIndexNode();
        createIndexNode.setIndexName(name);
        createIndexNode.setTableName("MY_TABLE");
        ColumnNode columnNode = new ColumnNode();
        columnNode.setName("MY_COLUMN");
        columnNode.setType("java.sql.Types.NUMERIC(10, 0)");
        createIndexNode.getColumnNodes().add(columnNode);
        return createIndexNode;
    }



    public static CreateTableNode createTableNode(String tableName) {
        CreateTableNode createTableNode = new CreateTableNode();
        createTableNode.setTablespace(DATA_TABLESPACE_NAME);
        createTableNode.setTableName(tableName);
        for (int i=0; i < 3; i++) {
            ColumnNode columnNode = new ColumnNode();
            columnNode.setName("col_" + (i+1));
            columnNode.setType("numeric");
            ConstraintsNode constraintsNode = new ConstraintsNode();
            if (i == 0) {
                constraintsNode.setPrimaryKey(true);
                constraintsNode.setPrimaryKeyTablespace(INDEX_TABLESPACE_NAME);
                constraintsNode.setPrimaryKeyName("pk_" + (i+1));
                constraintsNode.setNullable(false);
            } else {
                constraintsNode.setNullable(true);
            }
            columnNode.setConstraints(constraintsNode);
            createTableNode.addColumnNode(columnNode);
        }
        return createTableNode;
    }


    public static CreateTableNode createCodeLangTableNode(String tableName) {
        CreateTableNode createTableNode = new CreateTableNode();
        createTableNode.setTablespace(DATA_TABLESPACE_NAME);
        createTableNode.setTableName(tableName);

        ColumnNode columnNode = new ColumnNode();
        columnNode.setName("CODE_CD");
        columnNode.setType("VARCHAR");
        ConstraintsNode constraintsNode = new ConstraintsNode();
        constraintsNode.setPrimaryKey(true);
        constraintsNode.setPrimaryKeyTablespace(INDEX_TABLESPACE_NAME);
        constraintsNode.setPrimaryKeyName("CODE_PK");
        constraintsNode.setNullable(false);
        createTableNode.addColumnNode(columnNode);

        columnNode = new ColumnNode();
        columnNode.setName("DISPLAY_ORDER_NO");
        columnNode.setType("VARCHAR");
        createTableNode.addColumnNode(columnNode);

        columnNode = new ColumnNode();
        columnNode.setName("CODE_LABEL");
        columnNode.setType("VARCHAR");
        createTableNode.addColumnNode(columnNode);
        return createTableNode;
    }


    public static CreateTableNode createTableNodeWithValueComputed(String tableName) {
        CreateTableNode createTableNode = new CreateTableNode();
        createTableNode.setTablespace(DATA_TABLESPACE_NAME);
        createTableNode.setTableName(tableName);
        for (int i=0; i < 3; i++) {
            ColumnNode columnNode = new ColumnNode();
            columnNode.setName("col_" + (i+1));
            columnNode.setType("numeric");
            ConstraintsNode constraintsNode = new ConstraintsNode();
            if (i == 0) {
                columnNode.setDefaultValueComputed("NEXT VALUE FOR [ADDRESS_AUD_SQ]");
                columnNode.setDefaultValueConstraintName("VALUE_CONSTRAINT_NM_" + i);
                constraintsNode.setPrimaryKey(true);
                constraintsNode.setPrimaryKeyName("pk_" + (i+1));
                constraintsNode.setNullable(false);
            } else {
                constraintsNode.setNullable(true);
            }
            columnNode.setConstraints(constraintsNode);
            createTableNode.addColumnNode(columnNode);
        }
        return createTableNode;
    }

}
