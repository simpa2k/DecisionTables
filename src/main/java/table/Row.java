package table;

import java.util.ArrayList;

public class Row {

    private ArrayList<String> values;

    public Row(ArrayList<String> values) {
        this.values = new ArrayList<>(values);
    }

    public ArrayList<String> asArrayList() {
        return new ArrayList<>(values);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Row row = (Row) o;

        return values.equals(row.values);

    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
