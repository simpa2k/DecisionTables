package table;

import java.util.ArrayList;

public class ColumnFactory {

    public Column create() {
        return new Column(new ColumnFactory());
    }

    public Column create(ArrayList<String> values) {
        return new Column(new ColumnFactory(), values);
    }
}
