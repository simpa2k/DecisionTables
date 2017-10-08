package table;

import java.util.ArrayList;
import java.util.List;

public class Column {

    private ColumnFactory columnFactory;
    private ArrayList<String> values = new ArrayList<>();

    public Column(ColumnFactory columnFactory) {
        this.columnFactory = columnFactory;
    }

    public Column(ColumnFactory columnFactory, ArrayList<String> values) {

        this(columnFactory);
        this.values.addAll(values);

    }

    public String getValue(int row) {
        return values.get(row);
    }

    public String getEndingPoint() {
        return values.get(values.size() - 1);
    }

    public void append(String value) {
        values.add(value);
    }

    public void addHeader(String value) {
        values.add(0, value);
    }

    public void addResult(String value) {
        values.add(values.size() - 1, value);
    }

    public Column replaceAllFromRow(int fromRow, String value) {

        List<String> replaced = values.subList(fromRow, values.size());
        replaced.replaceAll(content -> value);

        ArrayList<String> result = new ArrayList<String>(values.subList(0, fromRow));
        result.addAll(replaced);

        return columnFactory.create(result);

    }

    public ArrayList<String> asArrayList() {
        return new ArrayList<>(values);
    }
}
