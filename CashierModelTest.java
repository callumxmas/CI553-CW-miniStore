import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import catalogue.Basket;
import catalogue.Product;
import clients.Setup; // Make sure to import your Setup class
import clients.cashier.CashierModel;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

public class CashierModelTest {

  private CashierModel cashierModel;

  @BeforeClass
  public static void setUpClass() {
	  Setup.main(null); // Run the Setup before tests
  }

  @Before
  public void setUp() {
    MiddleFactory mf = new LocalMiddleFactory();
    cashierModel = new CashierModel(mf);
  }

  @Test
  public void testDoCheck() {
	// Assuming "0001" is a valid product number for a 40 inch LED HD TV
      cashierModel.doCheck("0001"); 

      // Check if the state is updated correctly
      assertEquals(CashierModel.State.checked, cashierModel.getState()); 

      // Check if the correct product details are retrieved
      Product product = cashierModel.getProduct();
      assertEquals("0001", product.getProductNum());
      assertEquals("40 inch LED HD TV", product.getDescription());
      assertEquals(269.00, product.getPrice(), 0.001);
      assertEquals(1, product.getQuantity());
  }

  @Test
  public void testDoBuy() {
	// Assuming "0001" is a valid product number and in stock
	    cashierModel.doCheck("0001"); 
	    cashierModel.doBuy(); 

	    // Assertions to check the expected behaviour of doBuy

	    // 1. Check if the state is updated correctly
	    assertEquals(CashierModel.State.process, cashierModel.getState());

	    // 2. Check if the product is added to the basket
	    Basket basket = cashierModel.getBasket();
	    assertEquals(1, basket.size()); 

	    // Iterate through the basket to find the product
	    Product productInBasket = null;
	    for (Product p : basket) {
	        if (p.getProductNum().equals("0001")) {
	            productInBasket = p;
	            break;
	        }
	    }

	    // Assert that the product is found in the basket
	    assertNotNull(productInBasket); 
	    assertEquals("0001", productInBasket.getProductNum());
  }
  
  @Test
  public void testDoRem() {
      // Assuming "0001" is a valid product number and in stock
      cashierModel.doCheck("0001"); 
      cashierModel.doBuy(); 

      // Now remove the product from the basket
      cashierModel.doRem(); 

      // Assertions to check the expected behaviour of doRem

      // 1. Check if the state is updated correctly
      assertEquals(CashierModel.State.process, cashierModel.getState());

      // 2. Check if the product is removed from the basket
      Basket basket = cashierModel.getBasket();
      assertEquals(0, basket.size()); // Basket should be empty
  }
  
  @Test
  public void testDoCle() {
      // Assuming "0001" is a valid product number and in stock
      cashierModel.doCheck("0001");
      cashierModel.doBuy();  // Add product to basket
      cashierModel.doCheck("0001");
      cashierModel.doBuy();  // Add product to basket
      cashierModel.doCheck("0002");
      cashierModel.doBuy();  // Add product to basket
      cashierModel.doCheck("0004");
      cashierModel.doBuy();  // Add product to basket

      // Now clear the basket
      cashierModel.doCle();

      // Assertions to check the expected behaviour of doCle

      // 1. Check if the state is updated correctly
      assertEquals(CashierModel.State.process, cashierModel.getState());

      // 2. Check if the basket is empty
      Basket basket = cashierModel.getBasket();
      assertEquals(0, basket.size());
  }
  
  

  // Add more test methods here later...
}