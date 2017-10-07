package table;

import java.util.ArrayList;

public class Column {

    private ArrayList<String> values = new ArrayList<>();

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
        return null;
    }

    public ArrayList<String> asArrayList() {
        return new ArrayList<>(values);
    }
}
