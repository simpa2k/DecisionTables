package table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ArrayTable implements Table {

    private ArrayList<ArrayList<String>> table = new ArrayList<>();

    private RowFactory rowFactory;

    public ArrayTable(RowFactory rowFactory) {

        if (rowFactory == null) {
            throw new IllegalArgumentException("Row factory may not be null.");
        }

        this.rowFactory = rowFactory;
    }

    public ArrayTable(Table table, RowFactory rowFactory) {

        this(rowFactory);
        table.rows().forEach(this::appendRow);

    }

    public ArrayTable(List<List<String>> values, RowFactory rowFactory) {

        this(rowFactory);
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
        table.add(row.asArrayList());
    }

    @Override
    public void appendColumn(Column column) {

    }

    @Override
    public void appendColumnHeaders(String[] columnHeaders) {

    }

    @Override
    public void appendColumnHeaders(Row columnHeaders) {

    }

    @Override
    public void appendResultRow(String[] resultRow) {

    }

    @Override
    public void insertRow(int index, Row row) {

    }

    @Override
    public void removeLastRow() {
        table.remove(table.size() - 1);
    }

    @Override
    public Column getColumn(int column) {
        return null;
    }

    @Override
    public Row getColumnHeaders() {
        return null;
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
        return null;
    }

    @Override
    public Stream<Column> columns(int from, int to) {
        return null;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

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
        return true;

    }
}