import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.Products;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {

    private Products products;

    @BeforeEach
    void setUp() throws Exception{

        products = new Products("Apple",2.00,300);

    }

    //Test for Getters
    @Test
    void getName() {
        assertEquals("Apple",products.getName());
    }
    @Test
    void getCode() {
        assertEquals(2.00,products.getPrice());
    }
    @Test
    void getPrice() {
        assertEquals(300,products.getCode());
    }

    //Test for Setters
    @Test
    void setName() {
        products.setName("Lemon");
        assertEquals("Lemon",products.getName());
    }

    @Test
    void setCode() {
        products.setCode(200);
        assertEquals(200,products.getCode());
    }

    @Test
    void setPrice() {
        products.setPrice(7.00);
        assertEquals(7.00,products.getPrice());
    }

    //Test CompareTo
    @Test
    void compareTo() {
        Products prod1 = new Products("Banana",1.50,100);
        Products prod2 = new Products("Apple",1.50,400);
        assertNotEquals(0,prod1.compareTo(prod2));
    }

    //Test String
    @Test
    void testToString() {
        String expected = "Apple";
        assertEquals(expected,products.toString());
    }
}