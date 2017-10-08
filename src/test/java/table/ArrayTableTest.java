package table;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ArrayTableTest {

    private RowFactory mockRowFactory = mock(RowFactory.class);
    private ColumnFactory mockColumnFactory = mock(ColumnFactory.class);

    private final ArrayList<String> THREE_ROW_ITEMS = new ArrayList<>(Arrays.asList("v1", "v2", "v3"));

    private ArrayTable createEmptyArrayTable() {
        return new ArrayTable(mockRowFactory, mockColumnFactory);
    }

    private ArrayTable createFromOtherTable(Table other) {
        return new ArrayTable(other, mockRowFactory, mockColumnFactory);
    }

    private ArrayTable createArrayTableWithTwoRows() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        ArrayTable arrayTable = createEmptyArrayTable();

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);

        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(r1, r2);

        return arrayTable;

    }

    private ArrayTable createArrayTableWithFourRows() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);
        Row r3 = mock(Row.class);
        Row r4 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r3.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r4.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(r1, r2, r3, r4);

        ArrayTable arrayTable = createEmptyArrayTable();

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);
        arrayTable.appendRow(r3);
        arrayTable.appendRow(r4);

        return arrayTable;

    }

    private ArrayTable createArrayTableWithOneOutputRow() {

        Row inputRow = mock(Row.class);
        when(inputRow.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        Row outputRow = mock(Row.class);
        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(outputRow);

        ArrayTable arrayTable = createEmptyArrayTable();

        arrayTable.appendRow(inputRow);

        return arrayTable;

    }

    @Test
    public void testConstructFromOtherTable() {

        ArrayTable arrayTable = createArrayTableWithTwoRows();
        ArrayTable copy = createFromOtherTable(arrayTable);

        assertEquals(arrayTable.getColumns(), copy.getColumns());
        assertEquals(arrayTable.getRows(), copy.getRows());

    }

    @Test
    public void testConstructFromTwoDimensionalList() {

        List<String> r1 = Arrays.asList("v1", "v2", "v3");
        List<String> r2 = Arrays.asList("v4", "v5", "v6");
        List<String> r3 = Arrays.asList("v7", "v8", "v9");

        List<List<String>> allValues = Arrays.asList(r1, r2, r3);

        ArrayTable arrayTable = new ArrayTable(allValues, mockRowFactory, mockColumnFactory);

        assertEquals(3, arrayTable.getColumns());
        assertEquals(3, arrayTable.getRows());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructFromTwoDimensionalListWithNullRowFactory() {

        List<String> r1 = Arrays.asList("v1", "v2", "v3");
        List<String> r2 = Arrays.asList("v4", "v5", "v6");
        List<String> r3 = Arrays.asList("v7", "v8", "v9");

        List<List<String>> allValues = Arrays.asList(r1, r2, r3);

        new ArrayTable(allValues, null, mockColumnFactory);

    }

    @Test
    public void testAddRow() {

        ArrayTable arrayTable = createArrayTableWithOneOutputRow();
        arrayTable.getRow(0);

        verify(mockRowFactory).create(THREE_ROW_ITEMS);

    }

    @Test
    public void testRemoveLastRow() {

        ArrayTable arrayTable = createArrayTableWithTwoRows();
        arrayTable.removeLastRow();

        assertEquals(1, arrayTable.getRows());

    }

    @Test
    public void testGetRows() {

        ArrayTable arrayTable = createArrayTableWithOneOutputRow();
        assertEquals(1, arrayTable.getRows());

    }

    @Test
    public void testGetColumns() {

        ArrayTable arrayTable = createArrayTableWithOneOutputRow();
        assertEquals(3, arrayTable.getColumns());

    }

    @Test
    public void testGetColumnsOnEmptyTable() {

        ArrayTable arrayTable = createEmptyArrayTable();
        assertEquals(0, arrayTable.getColumns());

    }

    @Test
    public void testToString() {

        ArrayTable arrayTable = createArrayTableWithTwoRows();
        assertEquals("[v1, v2, v3]\n[v1, v2, v3]\n", arrayTable.toString());


    }

    @Test
    public void testRowsAsStream() {

        ArrayTable arrayTable = createArrayTableWithFourRows();

        arrayTable.rows().forEach(row -> {
            assertEquals(row.asArrayList(), THREE_ROW_ITEMS);
        });

    }

    @Test
    public void testColumnsAsStream() {

        ArrayTable arrayTable = createArrayTableWithFourRows();

        Column m1 = mock(Column.class);
        Column m2 = mock(Column.class);
        Column m3 = mock(Column.class);

        when(mockColumnFactory.create()).thenReturn(m1, m2, m3);

        ArrayList<Column> columns = new ArrayList<>(arrayTable.columns().collect(Collectors.toList()));

        for (int i = 0; i < columns.size(); i++) {

            String currentCorrectValue = THREE_ROW_ITEMS.get(i);

            for (int j = 0; j < 3; j++) {
                verify(columns.get(i), times(4)).append(currentCorrectValue);
            }
        }
    }

    @Test
    public void testEqualToEquivalentTable() {

        ArrayTable arrayTable = createArrayTableWithTwoRows();
        ArrayTable copy = createFromOtherTable(arrayTable);

        assertEquals(arrayTable, copy);

    }
}
