package table;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TablePermuter {

    private Table table;
    private TableFactory tableFactory;

    public TablePermuter(Table table, TableFactory tableFactory) {

        this.table = table;
        this.tableFactory = tableFactory;

    }

    private LinkedList<Table> createJobs() {

        LinkedList<Table> jobs = new LinkedList<>();

        IntStream.range(0, table.getRows() - 1)
                .forEach(i -> {

                    Table permutedTable = tableFactory.create(table);

                    Row first = permutedTable.removeRow(0);
                    Row next = permutedTable.removeRow(permutedTable.getRows() - (i + 1));

                    permutedTable.insertRow(0, next);
                    permutedTable.appendRow(first);

                    jobs.add(permutedTable);

                });

        jobs.add(tableFactory.create(table));

        return jobs;

    }

    public List<Table> getPermutations() {

        LinkedList<Table> jobs = createJobs();
        return getPermutationsThreaded(jobs);
        //return getPermutations(new ArrayList<>(), tableFactory.create(), new ArrayList<>());

    }

    /*
     * The following method is a translation of a Python implementation found at:
     * https://www.quora.com/How-do-I-generate-all-the-row-permutations-from-a-given-2D-array-recursively
     */
    /*private List<Table> getPermutations(ArrayList<Integer> indicesPlaced,
                                        Table currentAnswer,
                                        List<Table> answers) {

        if (currentAnswer.getRows() == table.getRows()) {
            answers.add(tableFactory.create(currentAnswer));
        } else {

            for (int i = 0; i < table.getRows(); i++) {

                if (indicesPlaced.indexOf(i) == -1) {

                    currentAnswer.appendRow(table.getRow(i));
                    indicesPlaced.add(i);
                    getPermutations(indicesPlaced, currentAnswer, answers);

                    indicesPlaced.remove(new Integer(i));
                    currentAnswer.removeLastRow();

                }
            }
        }

        return answers;

    }*/

    private List<Table> getPermutationsThreaded(LinkedList<Table> jobs) {

        final int numThreads = Runtime.getRuntime().availableProcessors() + 1;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Table> permutations = new ArrayList<>(); // ToDo: Make this a HashSet

        executor.submit(() -> pickOutPermutations(jobs, permutations));
        executor.shutdown();

        try {

            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // ToDo: Might be better to return Futures
            return permutations;

        } catch (InterruptedException e) {

            e.printStackTrace();
            return null;

        }
    }

    private void pickOutPermutations(LinkedList<Table> jobs, List<Table> permutations) {

        Table job;
        while((job = jobs.poll()) != null) {

            permutations.add(job);
            job = tableFactory.create(job);

            for (int n = 1; n < job.getRows(); n++) {
                for (int i = 1; i < job.getRows(); i++) {
                    for (int j = 1; j < job.getRows(); j++) {

                        Table permutation = tableFactory.create(job);

                        if (i != j) {

                            swap(i, j, permutation);

                            if (!permutations.contains(permutation)) {
                                permutations.add(permutation);
                            }
                        }
                    }
                }
                swap(1, n, job);

            }
        }
    }

    private void swap(int i, int j, Table table) {

        Row first, second;
        int lower, higher;

        if (i > j) {

            first = table.removeRow(i);
            second = table.removeRow(j);

            lower = j;
            higher = i;

        } else {

            first = table.removeRow(j);
            second = table.removeRow(i);

            lower = i;
            higher = j;

        }

        table.insertRow(lower, first);
        table.insertRow(higher, second);

    }
}
