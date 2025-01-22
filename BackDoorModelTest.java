import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clients.Setup;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

public class BackDoorModelTest {

    private BackDoorModel backDoorModel;
    private MiddleFactory mf;

    @BeforeClass
    public static void setUpClass() {
        Setup.main(null); // Run the Setup before tests
    }

    @Before
    public void setUp() {
        MiddleFactory mf = new LocalMiddleFactory();
        backDoorModel = new BackDoorModel(mf);
    }
    
    @Test
    public void testDoQuery() {
        // Assuming "0001" is a valid product number
        backDoorModel.doQuery("0001");

        // Assertions to check the expected behaviour of doQuery
        String result = backDoorModel.getTheAction(); // This line was incorrect
        assertTrue(result.contains("40 inch LED HD TV")); // Check if the result contains the description
        assertTrue(result.contains("269.00")); // Check if the result contains the product number
        assertTrue(result.contains("100")); // Check if the result contains the product number
        // Add more assertions to check other details in the result string
        
    }
    
    @Test
    public void testDoRStock() {
    	 // Assuming "0001" is a valid product number
        backDoorModel.doRStock("0001", "10", true); // Restock product "0001" with 10 items
        
        // Assertions to check the expected behaviour of doRStock
        String result = backDoorModel.getTheAction();
        assertEquals("", result); // Action message should be empty if successful
        // Add assertions to check if the stock level of product "0001" is updated correctly
        backDoorModel.doQuery("0001");
        result = backDoorModel.getTheAction();
        assertTrue(result.contains("40 inch LED HD TV"));
        assertTrue(result.contains("100"));
    }

    @Test
    public void testDoRemStock() {
    	 // Assuming "0001" is a valid product number
        backDoorModel.doRemStock("0001", "10", true); // Restock product "0001" with 10 items
        
        // Assertions to check the expected behaviour of doRStock
        String result = backDoorModel.getTheAction();
        assertEquals("", result); // Action message should be empty if successful
        // Add assertions to check if the stock level of product "0001" is updated correctly
        backDoorModel.doQuery("0001");
        result = backDoorModel.getTheAction();
        assertTrue(result.contains("40 inch LED HD TV"));
        assertTrue(result.contains("90"));
    }
    
   

    
}