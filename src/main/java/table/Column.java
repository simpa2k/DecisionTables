package table;

import java.util.ArrayList;

public class Column {

    private ArrayList<String> values = new ArrayList<>();

    public String getValue(int row) {
        return null;
    }

    public String getEndingPoint() {
        return values.get(values.size() - 1);
    }

    public Column replaceAllFromRow(int fromRow, String value) {
        return null;
    }
}
