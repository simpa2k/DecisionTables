package generator;

import table.Table;
import table.TableFactory;

import java.util.List;

public class DecisionTableGenerator extends Generator {

    public DecisionTableGenerator(List<String[]> lines, TableFactory tableFactory) {
        super(lines, tableFactory);
    }

    @Override
    public Table generate() {

        Table table = tableFactory.create();

        table.appendColumnHeaders(lines.get(0));

        for (int i = 1; i < lines.size() - 1; i++) {
            table.appendRow(lines.get(i));
        }

        table.appendResultRow(lines.get(lines.size() - 1));

        return table;

    }
}
