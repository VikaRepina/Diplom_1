import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class IngredientTypeTest {

    private final IngredientType type;
    private final String expectedName;
    private final int expectedOrdinal;

    public IngredientTypeTest(IngredientType type, String expectedName, int expectedOrdinal) {
        this.type = type;
        this.expectedName = expectedName;
        this.expectedOrdinal = expectedOrdinal;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { IngredientType.SAUCE, "SAUCE", 0 },
                { IngredientType.FILLING, "FILLING", 1 }
        });
    }

    @Test
    public void testIngredientTypeName() {
        assertEquals(expectedName, type.name());
    }

    @Test
    public void testIngredientTypeOrdinal() {
        assertEquals(expectedOrdinal, type.ordinal());
    }
}
