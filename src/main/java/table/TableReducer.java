package table;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableReducer {

    private Table table;
    private Table reducedTable;
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

        reducedTable = tableFactory.create();

    }

    public Table reduce() {

        Row resultRow = table.getRow(table.getRows() - 1);
        table.removeLastRow();

        LinkedList<Table> permutations = new LinkedList<>(tablePermuter.getPermutations());
        List<Table> reduced = new ArrayList<>();

        final int numThreads = Runtime.getRuntime().availableProcessors() + 1;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        executor.submit(() -> reducePermutations(resultRow, permutations, reduced));

        executor.shutdown();

        try {

            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // ToDo: Might be better to return Futures
            reduced.sort(Comparator.comparingInt(Table::getColumns));

            return reduced.get(0);

        } catch (InterruptedException e) {

            e.printStackTrace();
            return null;

        }
    }

    private void reducePermutations(Row resultRow, LinkedList<Table> permutations, List<Table> reduced) {

        Table table;
        while((table = permutations.poll()) != null) {

            table.appendRow(resultRow);

            Table reducedTable = tableFactory.create();
            reduce(0, table.columns().collect(Collectors.toList()), reducedTable);

            if (reducedTable.getColumns() > 0) {

                reducedTable.insertColumn(0, table.getColumn(0));
                reduced.add(reducedTable);

            }

        }
    }

    private void reduce(int row, List<Column> columnsList, Table table) {

        Collection<ArrayList<Column>> columnsByValue = pickOutSameValues(columnsList, row);

        columnsByValue.forEach(columns -> {

            if (columns.size() > 1) {

                if (columns.stream()
                    .map(column -> column.getEndingPoint())
                    .distinct()
                    .count() <= 1) { // All ending points the same.

                    table.appendColumn(columns.get(0).replaceAllFromRow(row + 1, "*"));

                } else {
                    reduce(row + 1, columns, table);
                }
            }
        });
    }

    private Collection<ArrayList<Column>> pickOutSameValues(List<Column> columnsList, int row) {

        Map<String, ArrayList<Column>> columnsByValue = new HashMap<>();

        columnsList.forEach(column -> {

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
