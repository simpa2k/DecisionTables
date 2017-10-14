package table;

import org.junit.Before;
import org.junit.Test;
import resources.TestResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TablePermuterTest {

    private TestResources testResources = new TestResources();

    private Table mockInitialTable = mock(Table.class);
    private Table mockTable;
    private TableFactory mockTableFactory;

    private List<String> r1Values = Arrays.asList("v1", "v2", "v3");
    private List<String> r2Values = Arrays.asList("v4", "v5", "v6");
    private List<String> r3Values = Arrays.asList("v7", "v8", "v9");
    private List<String> r4Values = Arrays.asList("v10", "v11", "v12");

    @Before
    public void setup() {

        mockTable = testResources.getMockTable();
        mockTableFactory = testResources.getMockTableFactory();

    }

    private Table createThreeRowTable() {

        Row r1 = mock(Row.class);
        Row r2 = mock(Row.class);
        Row r3 = mock(Row.class);

        when(r1.asArrayList()).thenReturn(new ArrayList<>(r1Values));
        when(r2.asArrayList()).thenReturn(new ArrayList<>(r2Values));
        when(r3.asArrayList()).thenReturn(new ArrayList<>(r3Values));

        Table table = new ArrayTable(new RowFactory(), new ColumnFactory());

        table.appendRow(r1);
        table.appendRow(r2);
        table.appendRow(r3);

        return table;

    }

    private TablePermuter createTablePermuter() {
        return new TablePermuter(createThreeRowTable(), new TableFactory());
    }

    private TablePermuter createLargeTableTablePermuter() {

        Table table = createThreeRowTable();

        Row r4 = mock(Row.class);
        when(r4.asArrayList()).thenReturn(new ArrayList<>(r4Values));

        table.appendRow(r4);

        return new TablePermuter(table, new TableFactory());

    }

    @Test
    public void testPermuteTable() {

        TablePermuter tablePermuter = createTablePermuter();
        List<Table> permutations = tablePermuter.getPermutations();

        assertEquals(6, permutations.size());

        /*
         * The permutations:
         *
         * v1, v2, v3
         * v4, v5, v6
         * v7, v8, v9
         *
         * v1, v2, v3
         * v7, v8, v9
         * v4, v5, v6
         *
         * v4, v5, v6
         * v1, v2, v3
         * v7, v8, v9
         *
         * v4, v5, v6
         * v7, v8, v9
         * v1, v2, v3
         *
         * v7, v8, v9
         * v1, v2, v3
         * v4, v5, v6
         *
         * v7, v8, v9
         * v4, v5, v6
         * v1, v2, v3
         *
         */

        TableFactory tableFactory = new TableFactory();

        Table p1 = tableFactory.create(Arrays.asList(r1Values, r2Values, r3Values));
        Table p2 = tableFactory.create(Arrays.asList(r1Values, r3Values, r2Values));
        Table p3 = tableFactory.create(Arrays.asList(r2Values, r1Values, r3Values));
        Table p4 = tableFactory.create(Arrays.asList(r2Values, r3Values, r1Values));
        Table p5 = tableFactory.create(Arrays.asList(r3Values, r1Values, r2Values));
        Table p6 = tableFactory.create(Arrays.asList(r3Values, r2Values, r1Values));

        assertTrue(permutations.contains(p1));
        assertTrue(permutations.contains(p2));
        assertTrue(permutations.contains(p3));
        assertTrue(permutations.contains(p4));
        assertTrue(permutations.contains(p5));
        assertTrue(permutations.contains(p6));

    }

    @Test
    public void testPermuteLargerTable() {

        TablePermuter tablePermuter = createLargeTableTablePermuter();
        List<Table> permutations = tablePermuter.getPermutations();

        assertEquals(24, permutations.size());

        TableFactory tableFactory = new TableFactory();

        Table p1 = tableFactory.create(Arrays.asList(r1Values, r2Values, r3Values, r4Values));
        Table p2 = tableFactory.create(Arrays.asList(r1Values, r3Values, r2Values, r4Values));
        Table p3 = tableFactory.create(Arrays.asList(r1Values, r4Values, r3Values, r2Values));
        Table p4 = tableFactory.create(Arrays.asList(r1Values, r4Values, r2Values, r3Values));
        Table p5 = tableFactory.create(Arrays.asList(r1Values, r2Values, r4Values, r3Values));
        Table p6 = tableFactory.create(Arrays.asList(r1Values, r3Values, r4Values, r2Values));
        Table p7 = tableFactory.create(Arrays.asList(r2Values, r1Values, r3Values, r4Values));
        Table p8 = tableFactory.create(Arrays.asList(r2Values, r3Values, r1Values, r4Values));
        Table p9 = tableFactory.create(Arrays.asList(r2Values, r1Values, r4Values, r3Values));
        Table p10 = tableFactory.create(Arrays.asList(r2Values, r3Values, r4Values, r1Values));
        Table p11 = tableFactory.create(Arrays.asList(r2Values, r4Values, r3Values, r1Values));
        Table p12 = tableFactory.create(Arrays.asList(r2Values, r4Values, r1Values, r3Values));
        Table p13 = tableFactory.create(Arrays.asList(r3Values, r1Values, r2Values, r4Values));
        Table p14 = tableFactory.create(Arrays.asList(r3Values, r2Values, r1Values, r4Values));
        Table p15 = tableFactory.create(Arrays.asList(r3Values, r1Values, r4Values, r2Values));
        Table p16 = tableFactory.create(Arrays.asList(r3Values, r2Values, r4Values, r1Values));
        Table p17 = tableFactory.create(Arrays.asList(r3Values, r4Values, r2Values, r1Values));
        Table p18 = tableFactory.create(Arrays.asList(r3Values, r4Values, r1Values, r2Values));
        Table p19 = tableFactory.create(Arrays.asList(r4Values, r1Values, r2Values, r3Values));
        Table p20 = tableFactory.create(Arrays.asList(r4Values, r2Values, r1Values, r3Values));
        Table p21 = tableFactory.create(Arrays.asList(r4Values, r1Values, r3Values, r2Values));
        Table p22 = tableFactory.create(Arrays.asList(r4Values, r2Values, r3Values, r1Values));
        Table p23 = tableFactory.create(Arrays.asList(r4Values, r3Values, r2Values, r1Values));
        Table p24 = tableFactory.create(Arrays.asList(r4Values, r3Values, r1Values, r2Values));

        assertTrue(permutations.contains(p1));
        assertTrue(permutations.contains(p2));
        assertTrue(permutations.contains(p3));
        assertTrue(permutations.contains(p4));
        assertTrue(permutations.contains(p5));
        assertTrue(permutations.contains(p6));
        assertTrue(permutations.contains(p7));
        assertTrue(permutations.contains(p8));
        assertTrue(permutations.contains(p9));
        assertTrue(permutations.contains(p10));
        assertTrue(permutations.contains(p11));
        assertTrue(permutations.contains(p12));
        assertTrue(permutations.contains(p13));
        assertTrue(permutations.contains(p14));
        assertTrue(permutations.contains(p15));
        assertTrue(permutations.contains(p16));
        assertTrue(permutations.contains(p17));
        assertTrue(permutations.contains(p18));
        assertTrue(permutations.contains(p19));
        assertTrue(permutations.contains(p20));
        assertTrue(permutations.contains(p21));
        assertTrue(permutations.contains(p22));
        assertTrue(permutations.contains(p23));
        assertTrue(permutations.contains(p24));

    }
}
