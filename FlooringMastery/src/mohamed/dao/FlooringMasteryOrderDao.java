package mohamed.dao;

import mohamed.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryOrderDao {
    Order addOrder(Order order); //will call the order object and calculates what is necessary

    List<Order> searchForOrders(LocalDate date); //will look for an order based on a date

    Order removeOrder(LocalDate date, int orderNumber); //removes an order from based on the date and order number

    void editOrder(LocalDate date, Order order); //edit an order based on the date

    Order getOrder(List<Order> orderList, int orderNumber); //from the orderlist we will look for a specific order based on the order number
    
    public List<Order> dataExport(LocalDate date);
}
