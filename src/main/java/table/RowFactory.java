package table;

import java.util.ArrayList;

public class RowFactory {

    public Row create(ArrayList<String> values) {
        return new Row(values);
    }
}
