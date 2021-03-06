package table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ArrayTable implements Table {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<ArrayList<String>> table = new ArrayList<>();

    private RowFactory rowFactory;
    private ColumnFactory columnFactory;

    public ArrayTable(RowFactory rowFactory, ColumnFactory columnFactory) {

        if (rowFactory == null) {
            throw new IllegalArgumentException("Row factory may not be null.");
        }

        if (columnFactory == null) {
            throw new IllegalArgumentException("Column factory may not be null.");
        }

        this.rowFactory = rowFactory;
        this.columnFactory = columnFactory;

    }

    public ArrayTable(Table table, RowFactory rowFactory, ColumnFactory columnFactory) {

        this(rowFactory, columnFactory);
        table.rows().forEach(this::appendRow);

    }

    public ArrayTable(List<List<String>> values, RowFactory rowFactory, ColumnFactory columnFactory) {

        this(rowFactory, columnFactory);
        values.stream().map(ArrayList::new).forEach(table::add);

    }

    @Override
    public int getColumns() {

        if (table.size() == 0) {
            return 0;
        }

        return table.get(0).size();
    }

    @Override
    public int getRows() {
        return table.size();
    }

    @Override
    public int getRowLength() {
        return 0;
    }

    @Override
    public void add(int column, int row, String value) {

    }

    @Override
    public void appendRow(String[] row) {

    }

    @Override
    public void appendRow(Row row) {
        table.add(new ArrayList<>(row.asArrayList()));
    }

    @Override
    public void appendColumn(Column column) {

        ArrayList<String> columnAsList = column.asArrayList();

        for (int i = 0; i < columnAsList.size(); i++) {

            ArrayList<String> currentRow;

            if (table.size() <= i) {

                currentRow = new ArrayList<>();
                table.add(i, currentRow);

            } else {
                currentRow = table.get(i);
            }

            currentRow.add(columnAsList.get(i));

        }
    }

    @Override
    public void appendColumnHeaders(String[] columnHeaders) {

    }

    @Override
    public void appendColumnHeaders(Row columnHeaders) {
        this.columnHeaders = columnHeaders.asArrayList();
    }

    @Override
    public void appendResultRow(String[] resultRow) {

    }

    @Override
    public void insertRow(int index, Row row) {
        table.add(index, row.asArrayList());
    }

    @Override
    public void insertColumn(int index, Column column) {

        ArrayList<String> columnAsList = column.asArrayList();

        for (int i = 0; i < columnAsList.size(); i++) {

            ArrayList<String> currentRow;

            if (table.size() <= i) {

                currentRow = new ArrayList<>();
                table.add(i, currentRow);

            } else {
                currentRow = table.get(i);
            }

            currentRow.add(0, columnAsList.get(i));
        }
    }

    @Override
    public Row removeRow(int row) {
        return rowFactory.create(table.remove(row));
    }

    @Override
    public void removeLastRow() {
        table.remove(table.size() - 1);
    }

    @Override
    public void removeColumnHeaders() {
        columnHeaders.clear();
    }

    @Override
    public Column getColumn(int column) {

        if (column < 0) {
            throw new IllegalArgumentException("Column must be non-negative.");
        }

        Column columnObj = columnFactory.create();

        for (ArrayList<String> row : table) {
            columnObj.append(row.get(column));
        }

        return columnObj;
    }

    @Override
    public Row getColumnHeaders() {
        return rowFactory.create(columnHeaders);
    }

    @Override
    public Row getRow(int row) {
        return rowFactory.create(table.get(row));
    }

    @Override
    public Stream<Row> rows() {
        return table.stream().map(rowFactory::create);
    }

    @Override
    public Stream<Column> columns() {

        ArrayList<Column> columns = new ArrayList<>();

        if (table.size() > 0) {

            for (int x = 0; x < table.get(0).size(); x++) {
                columns.add(getColumn(x));
            }
        }
        return columns.stream();

    }

    @Override
    public Stream<Column> columns(int from, int to) {
        return null;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        if (!columnHeaders.isEmpty()) {

            builder.append(columnHeaders);
            builder.append("\n");

        }

        table.forEach(row -> {

            builder.append(row);
            builder.append("\n");

        });

        return builder.toString();

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Table otherTable = (Table) o;

        /*
         * Stream iterator comparison from:
         * https://stackoverflow.com/questions/34818533/how-to-compare-two-streams-in-java-8
         */
        Iterator<Row> otherIterator = otherTable.rows().iterator();
        Iterator<Row> thisIterator = rows().iterator();

        while(otherIterator.hasNext() && thisIterator.hasNext()) {

            if (!otherIterator.next().equals(thisIterator.next())) {
                return false;
            }
        }

        return !otherIterator.hasNext() && !thisIterator.hasNext();

    }
}
