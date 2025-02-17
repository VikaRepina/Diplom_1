import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import praktikum.Database;
import praktikum.Bun;

import static org.hamcrest.MatcherAssert.assertThat;

public class BunNegativePriceTest {
    private static final String name = "red bun";
    private static final float price = - 300;
    private Database database;

    @Before
    public void setup() {
        database = new  Database();
    }

    @Test
    public void testBunCreationWithNegativePrice() {
        SoftAssertions softly = new SoftAssertions();

        Bun bunFromDatabase = database.availableBuns().stream()
                .filter(bun -> bun.getName() != null && bun.getName().equals(name))
                .findFirst()
                .orElse(null);

        softly.assertThat(bunFromDatabase).as("Ожидали, что булочка с отрицательной ценой не создается, но она была найдена.").isNull();

        softly.assertAll();
    }
}
