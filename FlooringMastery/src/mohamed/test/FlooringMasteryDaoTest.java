package mohamed.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mohamed.dao.FlooringMasteryOrderDaoImpl;
import mohamed.dao.FlooringMasteryProductDaoImpl;
import mohamed.dao.FlooringMasteryTaxDaoImpl;
import mohamed.dto.Order;

class FlooringMasteryDaoTest {

	FlooringMasteryTaxDaoImpl taxDao = new FlooringMasteryTaxDaoImpl();
	FlooringMasteryProductDaoImpl productDao = new FlooringMasteryProductDaoImpl();
	FlooringMasteryOrderDaoImpl orderDao = new FlooringMasteryOrderDaoImpl();
	
	@Test
	public void testAddOrder() throws Exception {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1000);
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
        currentOrder.setTimeStamp(LocalDate.parse("02/02/1995", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        assertEquals(currentOrder, orderDao.addOrder(currentOrder));      
	}

	@Test
	public void testGetOrder() {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1000);
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
        currentOrder.setTimeStamp(LocalDate.parse("02/02/1995", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        List<Order> orders = new ArrayList<>();
        orders.add(currentOrder);
        
        assertEquals(currentOrder, orderDao.getOrder(orders, 1000));
	}
	
	@Test
	public void testSearchForOrders() throws Exception {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1000);
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
        currentOrder.setTimeStamp(LocalDate.parse("02/02/1995", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
		assertEquals(orderDao.searchForOrders(LocalDate.of(1995, 2, 2)), orderDao.readOrder(LocalDate.of(1995, 2, 2)));
	}
	
	@Test
	public void testDataExport() {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1000);
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
        currentOrder.setTimeStamp(LocalDate.parse("02/02/1995", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
		assertNotNull(orderDao.dataExport(null));
	}
	
	@Test
	public void testRemoveOrder() {
        Order currentOrder = new Order();
        currentOrder.setOrderNumber(1000);
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
        currentOrder.setTimeStamp(LocalDate.parse("02/02/1995", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        assertEquals(orderDao.removeOrder(null, 1000), null);
	}
}
