import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Database;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BunAbsenceNameTest {
    private final String name;
    private final float price;
    private Database database;

    public BunAbsenceNameTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters(name = "Название булочки: {0}, цена: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {null, 300},
                {null, 0}

        });
    }

    @Before
    public void setup() {
        database = new  Database();
    }

    @Test
    public void testBunCreationWithNullName() {
        SoftAssertions softly = new SoftAssertions();

        Bun bunFromDatabase = database.availableBuns().stream()
                .filter(bun -> bun.getName() != null && bun.getName().equals(name))
                .findFirst()
                .orElse(null);

        softly.assertThat(bunFromDatabase).as("Ожидали, что булочка с отсутствующим именем не создается, но она была найдена.").isNull();
        softly.assertAll();
    }
}
