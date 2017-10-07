package generator;

import table.Table;
import table.TableFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Generator {

    protected TableFactory tableFactory;
    protected ArrayList<String[]> lines = new ArrayList<>();

    public Generator(List<String[]> lines, TableFactory tableFactory) {

        if (lines.size() <= 1 || lines.get(0).length <= 1) {
            throw new IllegalArgumentException("There must be values to generate from!");
        }

        if (tableFactory == null) {
            throw new IllegalArgumentException("Table factory may not be null.");
        }

        this.tableFactory = tableFactory;
        this.lines.addAll(lines);

    }

    public abstract Table generate();

}
