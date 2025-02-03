import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

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
        assertEquals(bun, burger.bun);
        assertEquals("black bun", burger.bun.getName());
        assertEquals(100f, burger.bun.getPrice(), 0.001);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(sauce);
        assertEquals(1, burger.ingredients.size());
        assertEquals(sauce, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals(filling, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.moveIngredient(0, 1);
        assertEquals(filling, burger.ingredients.get(0));
        assertEquals(sauce, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        float expectedPrice = (bun.getPrice() * 2) + sauce.getPrice() + filling.getPrice();
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    @Test
    public void testGetReceipt() {

        when(sauce.getType()).thenReturn(SAUCE);
        when(filling.getType()).thenReturn(FILLING);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(bun.getName()));
        assertTrue(receipt.contains(sauce.getName()));
        assertTrue(receipt.contains(filling.getName()));
        assertTrue(receipt.contains(String.format("Price: %f", burger.getPrice())));
    }
}

