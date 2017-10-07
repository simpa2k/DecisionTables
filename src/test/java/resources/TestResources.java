package resources;

import table.Table;
import table.TableFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestResources {

    public static final String VALID_EXISTING_FILE = "./src/test/java/resources/table.csv";
    public static final String VALID_EMPTY_FILE = "./src/test/java/resources/emptyFile.csv";

    public static final Stream<String> THREE_ROW_THREE_COLUMN_CSV = Stream.of(",c1,c2,c3\nr1,v1,v2,v3\nr2,v4,v5,v6");

    private List<String[]> onlyRowHeaders;
    private List<String[]> fourCombinations;
    private List<String[]> threeByFourValues;

    private List<String[]> simpleDecisionTable;

    private TableFactory mockTableFactory = mock(TableFactory.class);
    private Table mockTable = mock(Table.class);

    public TestResources() {

        when(mockTableFactory.create()).thenReturn(mockTable);

        constructCombinationTables();
        constructDecisionTables();

    }

    private void constructCombinationTables() {

        String[] r1 = {"r1", "v1", "v2"};
        String[] r2 = {"r2", "v3", "v4"};

        fourCombinations = Arrays.asList(r1, r2);

        String[] r3 = {"r1"};
        String[] r4 = {"r2"};

        onlyRowHeaders = Arrays.asList(r3, r4);

        String[] r5 = {"r1", "v1", "v2", "v3"};
        String[] r6 = {"r2", "v4", "v5", "v6", "v7"};

        threeByFourValues = Arrays.asList(r5, r6);

    }

    private void constructDecisionTables() {

        String[] r1 = {""  , "c1", "c2", "c3", "c4"};
        String[] r2 = {"r1", "T" , "T" , "F" , "F"};
        String[] r3 = {"r2", "T" , "F" , "T" , "F"};
        String[] r4 = {"r3", "Y" , "Y" , "N" , "N"};

        simpleDecisionTable = Arrays.asList(r1, r2, r3, r4);

    }

    public List<String[]> getOnlyRowHeaders() {
        return onlyRowHeaders;
    }

    public List<String[]> getFourCombinations() {
        return fourCombinations;
    }

    public List<String[]> getThreeByFourValues() {
        return threeByFourValues;
    }

    public List<String[]> getSimpleDecisionTable() {
        return simpleDecisionTable;
    }

    public TableFactory getMockTableFactory() {
        return mockTableFactory;
    }

    public Table getMockTable() {
        return mockTable;
    }
}
