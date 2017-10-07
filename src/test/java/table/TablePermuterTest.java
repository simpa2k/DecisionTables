package table;

import org.junit.Before;
import org.junit.Test;
import resources.TestResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Before
    public void setup() {

        mockTable = testResources.getMockTable();
        mockTableFactory = testResources.getMockTableFactory();

    }

    private TablePermuter createTablePermuter() {

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

        assertEquals(p1, permutations.get(0));
        assertEquals(p2, permutations.get(1));
        assertEquals(p3, permutations.get(2));
        assertEquals(p4, permutations.get(3));
        assertEquals(p5, permutations.get(4));
        assertEquals(p6, permutations.get(5));

    }
}
