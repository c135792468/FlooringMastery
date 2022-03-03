/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mohamed.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mohamed.dao.FlooringMasteryOrderDao;
import mohamed.dao.FlooringMasteryProductDao;
import mohamed.dao.FlooringMasteryProductDaoImpl;
import mohamed.dao.FlooringMasteryTaxDao;
import mohamed.dao.FlooringMasteryTaxDaoImpl;
import mohamed.dto.Order;
import mohamed.dto.Product;
import mohamed.dto.Tax;
import mohamed.service.FlooringMasteryService;
import mohamed.service.FlooringMasteryStateNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 69591
 */
public class FlooringMasteryServiceTest {
    FlooringMasteryOrderDao dao = new FlooringMasteryDaoStubImpl();
    private FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoImpl();
    private FlooringMasteryTaxDao taxDao = new FlooringMasteryTaxDaoImpl();

    FlooringMasteryService service = new FlooringMasteryService(dao, productDao, taxDao);
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testRemoveOrder() throws Exception {
        // ARRANGE
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("TX");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("200"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // ACT & ASSERT
        Order shouldBeYyc = service.removeOrder(currentOrder.getTimeStamp(), currentOrder.getOrderNumber());
        assertNotNull( shouldBeYyc, "Removing 0001 should be not null.");
        assertEquals( currentOrder, shouldBeYyc, "Student removed from 0001 should be Ada.");

        Order shouldBeNull = service.removeOrder(currentOrder.getTimeStamp(), 4);    
        assertNull( shouldBeNull, "Removing 0042 should be null.");

    }

    @Test
    public void testDataExport(){
        // ARRANGE
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("TX");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("200"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // ACT & ASSERT
        assertEquals( 1, service.dataExport(currentOrder.getTimeStamp()).size(), 
                                       "Should only have one order.");
        assertTrue( service.dataExport(currentOrder.getTimeStamp()).contains(currentOrder),
                                  "The one order should be yyc.");
    }

    @Test
    public void testAddOrder(){
        // ARRANGE
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("TX");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("200"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // ACT
        try {
            service.addOrder(currentOrder);
        } catch (Exception e) {
        // ASSERT
            fail("Student was valid. No exception should have been thrown.");
        }
    }
    @Test
    public void testGetOrder(){
        // ARRANGE
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("TX");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("200"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        Order shouldBeYyc = service.getOrder(dao.searchForOrders(currentOrder.getTimeStamp()), 1);
        assertNotNull(shouldBeYyc, "Getting 1 should be not null.");
        assertEquals(currentOrder, shouldBeYyc,
                                       "Order stored under =1 should be yyc.");

        Order shouldBeNull = service.getOrder(dao.searchForOrders(currentOrder.getTimeStamp()), 5);    
        assertNull( shouldBeNull, "Getting 5 should be null.");

}
    
    @Test
    public void testSearchOrder() throws Exception {
        // ARRANGE
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("TX");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("200"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        assertEquals(1, service.searchOrders(currentOrder.getTimeStamp()).size(), 
                                       "Should only have one order.");
        assertTrue( service.searchOrders(currentOrder.getTimeStamp()).contains(currentOrder),
                                  "The one order should be yyc.");
    }
    
    @Test
    public void testLoadProductList() throws Exception {
        Product currentProduct = new Product();
        // ARRANGE
        currentProduct.setCostPerSqFt(new BigDecimal("3.50"));
        currentProduct.setLaborCostPerSqFt(new BigDecimal("4.15"));
        currentProduct.setProductType("Tile");

        assertEquals(4, service.getProductList().size(), 
                                       "Should only have 4 products.");
        assertTrue(service.getProductList().contains(currentProduct),
                                  "The list contains Tile.");
    }
    
    @Test
    public void testStateNotFound() throws Exception {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("bbc");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("200"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        try{
            service.calculateCost(currentOrder);
            fail("Expected StateNotFound Exception was not thrown");
        } catch(FlooringMasteryStateNotFoundException e){
            return;
        }
    }
    @Test
    public void testCulateCost() throws Exception {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1);
        currentOrder.setCustomerName("yyc");
        currentOrder.setState("bbc");
        currentOrder.setTaxRate(new BigDecimal("4.45"));
        currentOrder.setProductType("Tile");
        currentOrder.setArea(new BigDecimal("0"));
        currentOrder.setCostPerSqFt(new BigDecimal("3.50"));
        currentOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        currentOrder.setMaterialCost(new BigDecimal("700"));
        currentOrder.setLaborCost(new BigDecimal("830"));
        currentOrder.setTotalTax(new BigDecimal("6808.5"));
        currentOrder.setTotalCost(new BigDecimal("8338.5"));
        currentOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        try{
            service.calculateCost(currentOrder);
            fail(" Exception was not thrown");
        } catch(Exception e){
            return;
        }
    }
}


