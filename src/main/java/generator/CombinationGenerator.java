package generator;

import table.Table;
import table.TableFactory;

import java.util.List;

public class CombinationGenerator extends Generator {

    public CombinationGenerator(List<String[]> lines, TableFactory tableFactory) {

        super(lines, tableFactory);

    }

    @Override
    public Table generate() {

        Table table = tableFactory.create();

        int numberOfColumns = lines.stream()
                .map((array) -> array.length)
                .reduce(1, (arrALength , arrBLength) -> arrALength * arrBLength);

        for (int row = 0; row < lines.size(); row++) {

            int currentIndex = 0;

            for (int repetitions = 0; repetitions < numberOfColumns && currentIndex < numberOfColumns; repetitions++) {
                for (int column = 1; column < lines.get(row).length; column++) {

                    double characterRepetitions = Math.pow(2, lines.size() - 1) - row;

                    for (int i = 0; i < characterRepetitions; i++) {

                        String valueToAdd = lines.get(row)[column];
                        int columnToAddTo = ++currentIndex;

                        table.add(columnToAddTo, row, valueToAdd);

                    }
                }
            }
        }

        /*
         * Add row labels
         */
        for (int row = 0; row < lines.size(); row++) {
            table.add(0, row, lines.get(row)[0]);
        }

        return table;

    }
}
