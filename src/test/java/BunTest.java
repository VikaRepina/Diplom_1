import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Database;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.Collection;


import static org.hamcrest.MatcherAssert.assertThat;
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

    @Parameterized.Parameters(name = "Название булочки: {0}, цена: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"white bun", 200},
                {"black bun", 100},
                {"red bun", 300},
                {"black bun", 0}

        });
    }

    @Before
    public void setup() {
        database = new  Database();
    }

    @Test
    public void testBunCreation() {
        SoftAssertions softly = new SoftAssertions();

        Bun bunFromDatabase = database.availableBuns().stream()
                .filter(bun -> bun.getName() != null && bun.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (price == 0) {
            softly.assertThat(bunFromDatabase).as("Ожидали, что булочка с ценой 0 не создается, но она была найдена.").isNotNull();
        } else {
            softly.assertThat(bunFromDatabase).as("Ожидали, что булочка создается корректно, но она не была найдена.").isNotNull();
            softly.assertThat(bunFromDatabase.getName()).as("Ожидали, что имя булочки будет '" + name + "', но получили '" + bunFromDatabase.getName() + "'.").isEqualTo(name);
            softly.assertThat(bunFromDatabase.getPrice()).as("Ожидали, что цена булочки будет " + price + ", но получили " + bunFromDatabase.getPrice() + ".").isEqualTo(price);
        }
        softly.assertAll();
    }
}
