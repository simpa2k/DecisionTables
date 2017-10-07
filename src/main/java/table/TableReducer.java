package table;

import java.util.*;

public class TableReducer {

    private Table table;
    private TableFactory tableFactory;
    private TablePermuter tablePermuter;

    public TableReducer(Table table, TableFactory tableFactory, TablePermuter tablePermuter) {

        if (table == null) {
            throw new IllegalArgumentException("Table may not be null.");
        }

        if (tableFactory == null) {
            throw new IllegalArgumentException("Table factory may not be null.");
        }

        if (tablePermuter == null) {
            throw new IllegalArgumentException("Table permuter may not be null.");
        }

        this.table = table;
        this.tableFactory = tableFactory;
        this.tablePermuter = tablePermuter;

    }

    public Table reduce() {

        List<Table> permutations = tablePermuter.getPermutations();
        return reduce(1);

    }

    private Table reduce(int row) {

        Table reducedTable = tableFactory.create();

        Collection<ArrayList<Column>> columnsByValue = pickOutSameValues(row);

        columnsByValue.forEach(columns -> {

            if (columns.stream()
                    .map(column -> column.getEndingPoint())
                    .distinct()
                    .count() <= 1) { // All ending points the same.

                reducedTable.appendColumn(columns.get(0).replaceAllFromRow(row, "*"));

            } else {
                reduce(row + 1);
            }
        });

        reducedTable.appendColumn(table.getColumn(0));
        reducedTable.appendColumnHeaders(table.getColumnHeaders());

        return reducedTable;

    }

    private Collection<ArrayList<Column>> pickOutSameValues(int row) {

        Map<String, ArrayList<Column>> columnsByValue = new HashMap<>();

        table.columns().forEach(column -> {

            String value = column.getValue(row);
            ArrayList<Column> columns = columnsByValue.get(value);

            if (columnsByValue.get(value) == null) {

                columns = new ArrayList<>();
                columnsByValue.put(value, columns);

            }

            columns.add(column);

        });
        return columnsByValue.values();

    }
}
