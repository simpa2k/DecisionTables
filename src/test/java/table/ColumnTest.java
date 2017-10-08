package table;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ColumnTest {

    private Column createFourItemcolumn() {
        return new Column(new ColumnFactory(), new ArrayList<>(Arrays.asList("v1", "v2", "v3", "v4")));
    }

    @Test
    public void testReplaceAllFromRow() {

        Column column = createFourItemcolumn();
        Column result = column.replaceAllFromRow(1, "*");

        assertEquals("v1", result.getValue(0));
        assertEquals("*", result.getValue(1));
        assertEquals("*", result.getValue(2));
        assertEquals("*", result.getValue(3));

    }
}
