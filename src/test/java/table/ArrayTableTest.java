package table;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArrayTableTest {

    private RowFactory mockRowFactory = mock(RowFactory.class);

    private final ArrayList<String> THREE_ROW_ITEMS = new ArrayList<>(Arrays.asList("v1", "v2", "v3"));

    @Test
    public void testConstructFromOtherTable() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);

        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(r1, r2);

        ArrayTable copy = new ArrayTable(arrayTable, mockRowFactory);

        assertEquals(arrayTable.getColumns(), copy.getColumns());
        assertEquals(arrayTable.getRows(), copy.getRows());

    }

    @Test
    public void testConstructFromTwoDimensionalList() {

        List<String> r1 = Arrays.asList("v1", "v2", "v3");
        List<String> r2 = Arrays.asList("v4", "v5", "v6");
        List<String> r3 = Arrays.asList("v7", "v8", "v9");

        List<List<String>> allValues = Arrays.asList(r1, r2, r3);

        ArrayTable arrayTable = new ArrayTable(allValues, mockRowFactory);

        assertEquals(3, arrayTable.getColumns());
        assertEquals(3, arrayTable.getRows());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructFromTwoDimensionalListWithNullRowFactory() {

        List<String> r1 = Arrays.asList("v1", "v2", "v3");
        List<String> r2 = Arrays.asList("v4", "v5", "v6");
        List<String> r3 = Arrays.asList("v7", "v8", "v9");

        List<List<String>> allValues = Arrays.asList(r1, r2, r3);

        new ArrayTable(allValues, null);

    }

    @Test
    public void testAddRow() {

        Row inputRow = mock(Row.class);
        when(inputRow.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        Row outputRow = mock(Row.class);
        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(outputRow);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(inputRow);
        arrayTable.getRow(0);

        verify(mockRowFactory).create(THREE_ROW_ITEMS);

    }

    @Test
    public void testRemoveLastRow() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);

        arrayTable.removeLastRow();

        assertEquals(1, arrayTable.getRows());

    }

    @Test
    public void testGetRows() {

        Row inputRow = mock(Row.class);
        when(inputRow.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        Row outputRow = mock(Row.class);
        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(outputRow);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(inputRow);

        assertEquals(1, arrayTable.getRows());
    }

    @Test
    public void testGetColumns() {

        Row inputRow = mock(Row.class);
        when(inputRow.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        Row outputRow = mock(Row.class);
        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(outputRow);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(inputRow);

        assertEquals(3, arrayTable.getColumns());
    }

    @Test
    public void testGetColumnsOnEmptyTable() {

        Row inputRow = mock(Row.class);
        when(inputRow.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        Row outputRow = mock(Row.class);
        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(outputRow);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        assertEquals(0, arrayTable.getColumns());
    }

    @Test
    public void testToString() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);

        assertEquals("[v1, v2, v3]\n[v1, v2, v3]\n", arrayTable.toString());

    }

    @Test
    public void testRowsAsStream() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);
        Row r3 = mock(Row.class);
        Row r4 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r3.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r4.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(r1, r2, r3, r4);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);
        arrayTable.appendRow(r3);
        arrayTable.appendRow(r4);

        arrayTable.rows().forEach(row -> {
            assertEquals(row.asArrayList(), THREE_ROW_ITEMS);
        });
    }

    @Test
    public void testEqualToEquivalentTable() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(THREE_ROW_ITEMS);
        when(r2.asArrayList()).thenReturn(THREE_ROW_ITEMS);

        ArrayTable arrayTable = new ArrayTable(mockRowFactory);

        arrayTable.appendRow(r1);
        arrayTable.appendRow(r2);

        when(mockRowFactory.create(THREE_ROW_ITEMS)).thenReturn(r1, r2);

        ArrayTable copy = new ArrayTable(arrayTable, mockRowFactory);

        assertEquals(arrayTable, copy);

    }
}
