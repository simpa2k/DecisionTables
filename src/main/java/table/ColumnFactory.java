package table;

import java.util.ArrayList;

public class ColumnFactory {

    public Column create() {
        return new Column();
    }

    public Column create(ArrayList<String> values) {
        return new Column(values);
    }
}
