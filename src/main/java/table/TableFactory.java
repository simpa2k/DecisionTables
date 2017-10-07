package table;

import java.util.ArrayList;
import java.util.List;

public class TableFactory {

    public Table create() {
        return new ArrayTable(new RowFactory());
    }

    public Table create(Table table) {
        return new ArrayTable(table, new RowFactory());
    }

    public Table create(List<List<String>> rows) {
        return new ArrayTable(rows, new RowFactory());
    }
}
