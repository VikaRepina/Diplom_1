import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import org.assertj.core.api.SoftAssertions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static praktikum.IngredientType.FILLING;
import static praktikum.IngredientType.SAUCE;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @InjectMocks
    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    @Before
    public void setup() {
        // Настраиваем моки
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getPrice()).thenReturn(100f);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getPrice()).thenReturn(100f);

        // Устанавливаем типы для моков
        when(sauce.getType()).thenReturn(SAUCE);
        when(filling.getType()).thenReturn(FILLING);

        burger.setBuns(bun);
    }

    @Test
    public void testSetBuns() {
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(burger.bun).as("Булочка не установлена").isEqualTo(bun);
        softly.assertThat(burger.bun.getName()).as("Имя булочка не установлено").isEqualTo("black bun");
        softly.assertThat(burger.bun.getPrice()).as("Цена булочка не установлена").isEqualTo(100f);
        softly.assertAll();
    }

    @Test
    public void testAddIngredient() {
        SoftAssertions softly = new SoftAssertions();

        burger.addIngredient(sauce);

        softly.assertThat(burger.ingredients.size()).as("Количество ингредиентов после добавляения не установлено").isEqualTo(1);
        softly.assertThat(burger.ingredients.get(0)).as("Ингредиент не установлен").isEqualTo(sauce);
        softly.assertAll();
    }

    @Test
    public void testRemoveIngredient() {
        SoftAssertions softly = new SoftAssertions();

        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.removeIngredient(0);

        softly.assertThat(burger.ingredients.size()).as("Количество ингредиентов после удаления не установлено").isEqualTo(1);
        softly.assertThat(burger.ingredients.get(0)).as("Оставшийся ингредиент не установлен").isEqualTo(filling);

        softly.assertAll();
    }

    @Test
    public void testMoveIngredient() {
        SoftAssertions softly = new SoftAssertions();

        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.moveIngredient(0, 1);

        softly.assertThat(burger.ingredients.get(0)).as("Ингредиент после перемещения не установлен").isEqualTo(filling);
        softly.assertThat(burger.ingredients.get(1)).as("Ингредиент после перемещения не установлен").isEqualTo(sauce);

        softly.assertAll();
    }

    @Test
    public void testGetPrice() {

        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        float expectedPrice = (bun.getPrice() * 2) + sauce.getPrice() + filling.getPrice();
        assertEquals("Цена на бургер не установлена",expectedPrice, burger.getPrice(), 0.001);

    }

    @Test
    public void testGetReceipt() {
        SoftAssertions softly = new SoftAssertions();

        when(sauce.getType()).thenReturn(SAUCE);
        when(filling.getType()).thenReturn(FILLING);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        softly.assertThat(receipt).contains(bun.getName()).as("Не найдена булочка в чеке");
        softly.assertThat(receipt).contains(sauce.getName()).as("Не найден соус в чеке");
        softly.assertThat(receipt).contains(filling.getName()).as("Не найдена начинка в чеке");
        softly.assertThat(receipt).contains(String.format("Price: %f", burger.getPrice())).as("Не найдена цена в чеке");

        softly.assertAll();
    }
}

