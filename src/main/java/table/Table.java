package table;

import java.util.stream.Stream;

public interface Table {

    int getColumns();

    int getRows();

    int getRowLength();

    void add(int column, int row, String value);

    void appendRow(String[] row);

    void appendRow(Row row);

    void appendColumn(Column column);

    void appendColumnHeaders(String[] columnHeaders);

    void appendColumnHeaders(Row columnHeaders);

    void appendResultRow(String[] resultRow);

    void insertRow(int index, Row row);

    void insertColumn(int index, Column column);

    Row removeRow(int row);

    void removeLastRow();

    void removeColumnHeaders();

    Column getColumn(int column);

    Row getColumnHeaders();

    Row getRow(int row);

    Stream<Row> rows();

    Stream<Column> columns();

    Stream<Column> columns(int from, int to);

}
