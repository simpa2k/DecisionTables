package generator;

import org.junit.Before;
import org.junit.Test;
import resources.TestResources;
import table.Table;
import table.TableFactory;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CombinationGeneratorTest {

    private List<String[]> onlyRowHeaders;
    private List<String[]> fourCombinations;
    private List<String[]> threeByFourValues;

    private TableFactory mockTableFactory;
    private Table mockTable;

    @Before
    public void setup() {

        TestResources testResources = new TestResources();

        onlyRowHeaders = testResources.getOnlyRowHeaders();
        fourCombinations  = testResources.getFourCombinations();
        threeByFourValues = testResources.getThreeByFourValues();

        mockTableFactory = testResources.getMockTableFactory();
        mockTable = testResources.getMockTable();

    }

    private CombinationGenerator createCombinationGeneratorWithValidFactory(List<String[]> lines) {
        return new CombinationGenerator(lines, mockTableFactory);
    }

    private CombinationGenerator createCombinationWithValidLines(TableFactory tableFactory) {
        return new CombinationGenerator(fourCombinations, tableFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidLines() {
        createCombinationGeneratorWithValidFactory(onlyRowHeaders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullFactory() {
        createCombinationWithValidLines(null);
    }

    @Test
    public void testGenerateFromSimpleCsv() {

        CombinationGenerator combinationGenerator = createCombinationGeneratorWithValidFactory(fourCombinations);
        Table table = combinationGenerator.generate();

        verify(mockTableFactory, times(1)).create();

        /*
         * The table should look like this:
         *
         * r1 v1 v1 v2 v2
         * r2 v3 v4 v3 v4
         *
         */

        verify(table).add(1, 0, "v1");
        verify(table).add(2, 0, "v1");
        verify(table).add(3, 0, "v2");
        verify(table).add(4, 0, "v2");

        verify(table).add(1, 1, "v3");
        verify(table).add(2, 1, "v4");
        verify(table).add(3, 1, "v3");
        verify(table).add(4, 1, "v4");

        verify(table).add(0, 0, "r1");
        verify(table).add(0, 1, "r2");

    }

    @Test
    public void testGenerateFromRowsWithDifferentNumberOfValues() {

        CombinationGenerator combinationGenerator = createCombinationGeneratorWithValidFactory(threeByFourValues);
        Table table = combinationGenerator.generate();

        verify(mockTableFactory, times(1)).create();

        /*
         * The table should look like this:
         *
         * r1 v1 v1 v2 v2 v3 v3 v1 v1 v2 v2 v3 v3
         * r2 v4 v5 v6 v7 v4 v5 v6 v7 v4 v5 v6 v7
         *
         */

        verify(table).add(1, 0, "v1");
        verify(table).add(2, 0, "v1");
        verify(table).add(3, 0, "v2");
        verify(table).add(4, 0, "v2");
        verify(table).add(5, 0, "v3");
        verify(table).add(6, 0, "v3");

        verify(table).add(1, 1, "v4");
        verify(table).add(2, 1, "v5");
        verify(table).add(3, 1, "v6");
        verify(table).add(4, 1, "v7");

        verify(table).add(5, 1, "v4");
        verify(table).add(6, 1, "v5");
        verify(table).add(7, 1, "v6");
        verify(table).add(8, 1, "v7");

        verify(table).add(9, 1, "v4");
        verify(table).add(10, 1, "v5");
        verify(table).add(11, 1, "v6");
        verify(table).add(12, 1, "v7");

        verify(table).add(0, 0, "r1");
        verify(table).add(0, 1, "r2");

    }
}
