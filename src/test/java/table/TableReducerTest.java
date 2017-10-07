package table;

import org.junit.Before;
import org.junit.Test;
import resources.TestResources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class TableReducerTest {

    private Table mockTable;
    private TableFactory mockTableFactory;

    private TablePermuter mockTablePermuter;

    private Column reducedC1 = mock(Column.class);
    private Column reducedC3 = mock(Column.class);

    @Before
    public void setup() {

        TestResources testResources = new TestResources();

        mockTable = testResources.getMockTable();
        mockTableFactory = testResources.getMockTableFactory();

        mockTablePermuter = mock(TablePermuter.class);

    }

    private TableReducer createTableReducerWithCustomTable(Table table) {
        return new TableReducer(table, mockTableFactory, mockTablePermuter);
    }

    private TableReducer createTableReducerWithCustomTableFactory(TableFactory tableFactory) {
        return new TableReducer(mockTable, tableFactory, mockTablePermuter);
    }

    private TableReducer createTableReducerWithCustomTablePermuter(TablePermuter tablePermuter) {
        return new TableReducer(mockTable, mockTableFactory, tablePermuter);
    }

    private Stream<Column> getMockColumnStream() {

        Column c1 = mock(Column.class);

        when(c1.getValue(1)).thenReturn("T");
        when(c1.getValue(2)).thenReturn("T");
        when(c1.getEndingPoint()).thenReturn("Y");
        when(c1.replaceAllFromRow(1, "*")).thenReturn(reducedC1);

        Column c2 = mock(Column.class);

        when(c2.getValue(1)).thenReturn("T");
        when(c2.getValue(2)).thenReturn("F");
        when(c2.getEndingPoint()).thenReturn("Y");

        Column c3 = mock(Column.class);

        when(c3.getValue(1)).thenReturn("F");
        when(c3.getValue(2)).thenReturn("T");
        when(c3.getEndingPoint()).thenReturn("N");
        when(c3.replaceAllFromRow(1, "*")).thenReturn(reducedC3);

        Column c4 = mock(Column.class);

        when(c4.getValue(1)).thenReturn("F");
        when(c4.getValue(2)).thenReturn("F");
        when(c4.getEndingPoint()).thenReturn("N");

        return Stream.of(c1, c2, c3, c4);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructTableReducerWithNullTable() {
        createTableReducerWithCustomTable(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructTableReducerWithNullTableFactory() {
        createTableReducerWithCustomTableFactory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructTableReducerWithNullTablePermuter() {
        createTableReducerWithCustomTablePermuter(null);
    }

    @Test
    public void testReduceSimpleTable() {

        Table initialMockTable = mock(Table.class);

        Stream<Column> mockColumnStream = getMockColumnStream();
        when(initialMockTable.columns()).thenReturn(mockColumnStream);

        Column c0 = mock(Column.class);
        Row r0 = mock(Row.class);

        when(initialMockTable.getColumn(0)).thenReturn(c0);
        when(initialMockTable.getColumnHeaders()).thenReturn(r0);

        TableReducer tableReducer = createTableReducerWithCustomTable(initialMockTable);
        Table reducedTable = tableReducer.reduce();

        assertFalse(reducedTable == null);

        verify(reducedTable).appendColumnHeaders(r0);
        verify(reducedTable, times(0)).appendRow(r0);

        verify(reducedTable).appendColumn(reducedC1);
        verify(reducedTable).appendColumn(reducedC3);

    }

    @Test
    public void testHandlesPermutationsOfSimpleTable() {

        List<String> r1 = Arrays.asList(""  , "c1", "c2", "c3", "c4");
        List<String> r2 = Arrays.asList("r1", "T" , "F" , "T" , "F");
        List<String> r3 = Arrays.asList("r2", "T" , "T" , "F" , "F");
        List<String> r4 = Arrays.asList("r3", "Y" , "Y" , "N" , "N");

        TableFactory tableFactory = new TableFactory();
        Table table = tableFactory.create(Arrays.asList(r1, r2, r3, r4));

        List<String> r5 = Arrays.asList("r2", "T", "F");
        List<String> r6 = Arrays.asList("r1", "*", "*");

        Table correct = tableFactory.create(Arrays.asList(r5, r6));

        TableReducer tableReducer = new TableReducer(table, tableFactory, new TablePermuter(table, tableFactory));
        Table reducedTable = tableReducer.reduce();

        assertEquals(correct, reducedTable);

    }
}
