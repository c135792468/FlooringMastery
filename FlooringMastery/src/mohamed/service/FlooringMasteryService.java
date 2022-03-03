package mohamed.service;

import mohamed.dao.FlooringMasteryOrderDao;
import mohamed.dao.FlooringMasteryProductDao;
import mohamed.dao.FlooringMasteryTaxDao;
import mohamed.dto.Order;
import mohamed.dto.Product;
import mohamed.dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.math.RoundingMode.HALF_UP;

@Component
public class FlooringMasteryService {
    private FlooringMasteryOrderDao orderDao;
    private FlooringMasteryProductDao productDao;
    private FlooringMasteryTaxDao taxDao;

    @Autowired
    public FlooringMasteryService(FlooringMasteryOrderDao dao1, FlooringMasteryProductDao dao2,
                                  FlooringMasteryTaxDao dao3) {
        this.orderDao = dao1;
        this.productDao = dao2;
        this.taxDao = dao3;
    }

    public Order addOrder(Order order) throws Exception {
        if (order.getCustomerName().equals("")) {
            throw new Exception("You must Enter a Customer Name!");
        } else {
            orderDao.addOrder(order);
        }
        return order;
    }

    public Order removeOrder(LocalDate date, int orderNumber) throws Exception {
       return orderDao.removeOrder(date, orderNumber);
    }

    public List<Order> searchOrders(LocalDate date) {
        return orderDao.searchForOrders(date);
    }
    
    public List<Order> dataExport(LocalDate date) {
        return orderDao.dataExport(date);
    }
    public Order getOrder(List<Order> orderList, int orderNumber) {
        return orderDao.getOrder(orderList, orderNumber);
    }

    public Order editOrder(LocalDate date, Order order) throws Exception {
        if (order.getCustomerName().equalsIgnoreCase("")) {
            throw new Exception("You must Enter a Customer Name!");
        } else {
            orderDao.editOrder(date, order);
            return order;
        }
    }

    public List<Product> getProductList() throws Exception {
    	return productDao.loadProductList();
    }
    
    public Order calculateCost(Order order) throws Exception,
    	FlooringMasteryStateNotFoundException {
    	
        BigDecimal taxRate = new BigDecimal("100");
        BigDecimal area = order.getArea();
        
        List<Product> products = productDao.loadProductList();
        if (area.compareTo(BigDecimal.ZERO) >= 0) {
        	
        	 if(null == taxDao.getTax(order.getState())){
                 throw new FlooringMasteryStateNotFoundException(
                     "ERROR: Could not get state. state name: "
                     + order.getState()
                     + " not found");
                 }
             else
                 taxRate = taxDao.getTax(order.getState()).getTaxRate();

            for (Product p : products) {

                if (p.getProductType().equalsIgnoreCase(order.getProductType())) {

                    BigDecimal costSqFt = (p.getCostPerSqFt());
                    BigDecimal laborCostSqFt = (p.getLaborCostPerSqFt());

                    BigDecimal material = area.multiply(costSqFt);
                    BigDecimal materialCost = material.setScale(2, HALF_UP);

                    BigDecimal labor = area.multiply(laborCostSqFt);
                    BigDecimal laborCost = labor.setScale(2, HALF_UP);

                    BigDecimal subTotal = materialCost.add(laborCost);

                    BigDecimal tax = subTotal.multiply(taxRate);
                    BigDecimal taxCost = tax.setScale(2, HALF_UP);

                    BigDecimal totalCost = subTotal.add(taxCost);

                    order.setTaxRate(taxRate);
                    order.setCostPerSqFt(costSqFt);
                    order.setLaborCostPerSqFt(laborCostSqFt);
                    order.setMaterialCost(materialCost);
                    order.setLaborCost(laborCost);
                    order.setTotalTax(taxCost);
                    order.setTotalCost(totalCost);
                }
            }
        } else {
            throw new Exception("Area Must be Larger than Zero!");
        }

        return order;
    }
}
