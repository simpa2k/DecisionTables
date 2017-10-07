package generator;

import org.junit.Before;
import org.junit.Test;
import resources.TestResources;
import table.Table;
import table.TableFactory;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DecisionTableGeneratorTest {

    private List<String[]> simpleDecisionTable;
    private List<String[]> onlyRowHeaders;

    private TableFactory mockTableFactory;
    private Table mockTable;

    @Before
    public void setup() {

        TestResources testResources = new TestResources();

        simpleDecisionTable = testResources.getSimpleDecisionTable();
        onlyRowHeaders = testResources.getOnlyRowHeaders();

        mockTableFactory = testResources.getMockTableFactory();
        mockTable = testResources.getMockTable();

    }

    private DecisionTableGenerator createDecisionTableGeneratorWithValidFactory(List<String[]> lines) {
        return new DecisionTableGenerator(lines, mockTableFactory);
    }

    private DecisionTableGenerator createDecisionTableGeneratorWithValidLines(TableFactory tableFactory) {
        return new DecisionTableGenerator(simpleDecisionTable, tableFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidLines() {
        createDecisionTableGeneratorWithValidFactory(onlyRowHeaders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullFactory() {
        createDecisionTableGeneratorWithValidLines(null);
    }

    @Test
    public void testGenerateSimpleDecisionTable() {

        DecisionTableGenerator decisionTableGenerator = createDecisionTableGeneratorWithValidFactory(simpleDecisionTable);
        Table table = decisionTableGenerator.generate();

        verify(mockTableFactory, times(1)).create();

        String[] columnHeaders = new String[] {""  , "c1", "c2", "c3", "c4"};
        String[] resultRow = new String[] {"r3", "Y" , "Y" , "N" , "N"};

        verify(table).appendColumnHeaders(columnHeaders);
        verify(table, times(0)).appendRow(columnHeaders);

        verify(table).appendRow(new String[] {"r1", "T" , "T" , "F" , "F"});
        verify(table).appendRow(new String[] {"r2", "T" , "F" , "T" , "F"});

        verify(table).appendResultRow(resultRow);
        verify(table, times(0)).appendRow(resultRow);

    }
}
