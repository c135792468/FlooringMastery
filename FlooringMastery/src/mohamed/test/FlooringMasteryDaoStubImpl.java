/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mohamed.test;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import mohamed.dao.FlooringMasteryOrderDao;
import mohamed.dao.FlooringMasteryProductDao;
import mohamed.dao.FlooringMasteryTaxDao;
import mohamed.dto.Order;
import mohamed.dto.Product;
import mohamed.dto.Tax;

/**
 *
 * @author 69591
 */
public class FlooringMasteryDaoStubImpl implements FlooringMasteryOrderDao, FlooringMasteryTaxDao, FlooringMasteryProductDao {
    public Order onlyOrder;
    public Tax onlyState;
    public Product onlyProduct;
    
    public FlooringMasteryDaoStubImpl() {
    	onlyOrder = new Order();
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("yyc");
        onlyOrder.setState("TX");
        onlyOrder.setTaxRate(new BigDecimal("4.45"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("200"));
        onlyOrder.setCostPerSqFt(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSqFt(new BigDecimal("4.14"));
        onlyOrder.setMaterialCost(new BigDecimal("700"));
        onlyOrder.setLaborCost(new BigDecimal("830"));
        onlyOrder.setTotalTax(new BigDecimal("6808.5"));
        onlyOrder.setTotalCost(new BigDecimal("8338.5"));
        onlyOrder.setTimeStamp(LocalDate.parse("02/02/2022", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        onlyState = new Tax();
        onlyState.setState("TX");
        onlyState.setTaxRate(new BigDecimal("4.45"));
        onlyProduct = new Product();
        onlyProduct.setCostPerSqFt(new BigDecimal("3.50"));
        onlyProduct.setLaborCostPerSqFt(new BigDecimal("4.14"));
        onlyProduct.setProductType("Tile");
    }

    public FlooringMasteryDaoStubImpl(Order testOrder){
         this.onlyOrder = testOrder;
     }

    @Override
    public Order addOrder(Order order) {
        if (onlyOrder.getOrderNumber() == order.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> searchForOrders(LocalDate date) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        if(onlyOrder.getOrderNumber() == orderNumber){
           return onlyOrder;
        }
        else
            return null;
    }

    @Override
    public Order getOrder(List<Order> orderList, int orderNumber) {
        Order currentOrder = null;
        for(Order order: orderList){
            if(order.getOrderNumber() == orderNumber){
                currentOrder = order;
            }
        }
        return currentOrder;
    }

    @Override
    public List<Order> dataExport(LocalDate date) {
        List<Order> order = new ArrayList<>();
        order.add(onlyOrder);
        return order;
    }


    @Override
    public Tax getTax(String state) throws Exception {
        if(state.equals(onlyState.getState())){
            return onlyState;
        }
        else
            return null;
    }

    @Override
    public List<Product> loadProductList() throws Exception {
        List<Product> product = new ArrayList<>();
        product.add(onlyProduct);
        return product;
    }
    
    @Override
	public void loadTaxRates() throws Exception {
		// TODO Auto-generated method stub
		
	}
        
    @Override
    public void editOrder(LocalDate date, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
