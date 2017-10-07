package table;

import java.util.ArrayList;
import java.util.List;

public class TablePermuter {

    private Table table;
    private TableFactory tableFactory;

    public TablePermuter(Table table, TableFactory tableFactory) {

        this.table = table;
        this.tableFactory = tableFactory;

    }

    /*
     * The following method is a translation of a Python implementation found at:
     * https://www.quora.com/How-do-I-generate-all-the-row-permutations-from-a-given-2D-array-recursively
     */
    public List<Table> getPermutations() {
        return getPermutations(new ArrayList<>(), tableFactory.create(), new ArrayList<>());
    }

    private List<Table> getPermutations(ArrayList<Integer> indicesPlaced,
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

    }
}
