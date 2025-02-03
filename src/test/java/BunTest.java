import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Database;

import java.util.Arrays;
import java.util.Collection;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BunTest {
    private final String name;
    private final float price;
    private Database database;

    public BunTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"white bun", 200},
                {"black bun", 100},
                {"red bun", 300}
        });
    }

    @Before
    public void setup() {
        database = new  Database();
    }

    @Test
    public void testBunCreation() {
        Bun bunFromDatabase = database.availableBuns().stream()
                .filter(bun -> bun.getName().equals(name))
                .findFirst()
                .orElse(null);

        assertEquals(name, bunFromDatabase.getName());
        assertEquals(price, bunFromDatabase.getPrice(), 0.001);
    }
}
