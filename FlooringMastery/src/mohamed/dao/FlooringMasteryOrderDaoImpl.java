package mohamed.dao;

import mohamed.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryOrderDaoImpl implements FlooringMasteryOrderDao {

    public static final String DELIMITER = "::";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //the client must use this pattern when specifying the date that will be used for later functions
    public static final String header = ("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost,PerSquareFoot,MaterialCost,LaborCost,Tax,Total");

    private List<Order> orders = new ArrayList<>();
    private List<Order> allOrders = new ArrayList<>();

    @Override
    public Order addOrder(Order order) {
        int orderNumber = 0;
        List<Order> allOrders = dataExport(null);
        for(Order o : allOrders) {
        	if(orderNumber <= o.getOrderNumber()) orderNumber = o.getOrderNumber() + 1;
        }
        order.setOrderNumber(orderNumber);

        try {
            //store the time and simply add the order object to the list
            LocalDate date = order.getTimeStamp();
            orders = searchForOrders(date);
            orders.add(order);
            allOrders.add(order);
            //creating the order file and pushing the values of the order in it.
            
            writeOrder(date, orders);
            date = null;
            writeOrder(date, allOrders);

        } catch (Exception exc){
            System.out.println("Couldn't Write Order " + exc);
        }
        return order;
    }

    @Override
    public Order getOrder(List<Order> orderList, int orderNumber) {
        //very simple we check for the order number and if that order number is in the orders list then we return it
        Order retrievedOrder = new Order();
        for (Order order : orderList) {
            if (orderNumber == order.getOrderNumber()) {
                retrievedOrder = order;
            }
        }
        return retrievedOrder;
    }

    @Override
    public List<Order> searchForOrders(LocalDate date) {
        try {
            //scroll down for the read order function
            readOrder(date);
            return new ArrayList<>(orders);
        } catch (Exception ex) {
            System.out.println("Could Not Find Orders");
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Order> dataExport(LocalDate date) {
        try {
            //scroll down for the read order function
            readOrder(null);
        } catch (Exception ex) {
            System.out.println("Could Not Find Orders");
        }
        return new ArrayList<>(allOrders);
    }
    

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
    	Order removedOrder = null;
        try {
            List<Order> currentOrder = readOrder(date);
            for (Order order : currentOrder) {
                if (orderNumber == order.getOrderNumber()) {
                	removedOrder = order;
                    currentOrder.remove(order);
                    writeOrder(date, currentOrder);
                    date = null;
                    currentOrder = dataExport(date);
                    currentOrder.remove(order);
                    writeOrder(date, currentOrder);
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Order Not Removed");
            ex.printStackTrace();
        }
        return removedOrder;
    }

    @Override
    public void editOrder(LocalDate date, Order order) {

        try {
        	for(Order o : orders) {
        		if(o.getOrderNumber() == order.getOrderNumber()) {
        			orders.remove(o);
        			orders.add(order);
        			break;
        		}
        	}
            writeOrder(date, orders);
            date = null;
            allOrders = dataExport(date);
        	for(Order o : allOrders) {
        		if(o.getOrderNumber() == order.getOrderNumber()) {        			
        			allOrders.remove(o);
        			allOrders.add(order);
        			break;
        		}
        	}
            writeOrder(date, allOrders);
        } catch (Exception ex) {
            System.out.println("Order Not Updated!");
        }
    }



    public List<Order> readOrder(LocalDate date) throws Exception {
    //Looks for the files based on the date and order keyword
        String ITEM_FILE;
        if(null!=date){
            ITEM_FILE = "Orders/order_" + date;
        }
        else
            ITEM_FILE = "DataExport";
        
        System.out.println(ITEM_FILE);
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ITEM_FILE)));
        } catch (Exception e) {
            throw new Exception(
                    "-_- Could not Load Orders.", e);
        }
        
        String currentLine;

        String[] currentTokens;
        
        allOrders.clear();
        orders.clear();

        if (orders.isEmpty()) {
            scanner.nextLine();//Basically skipping the header by going to a new line
            //if there is a next line which is the case, since its the one with delimiters, retrieve data.
            while (scanner.hasNextLine()) {
            	
                currentLine = scanner.nextLine();

                currentTokens = currentLine.split(DELIMITER);

                if (currentTokens.length == 13) {
                    Order currentOrder = new Order();

                    //we store the values of the current lines
                    int id = Integer.parseInt(currentTokens[0]);
                    BigDecimal rate = new BigDecimal(currentTokens[3]);
                    BigDecimal area = new BigDecimal(currentTokens[5]);
                    BigDecimal costSQ = new BigDecimal(currentTokens[6]);
                    BigDecimal costLabSQ = new BigDecimal(currentTokens[7]);
                    BigDecimal material = new BigDecimal(currentTokens[8]);
                    BigDecimal labor = new BigDecimal(currentTokens[9]);
                    BigDecimal tax = new BigDecimal(currentTokens[10]);
                    BigDecimal total = new BigDecimal(currentTokens[11]);

                    //we set the values the values of the current line to the current Order object.
                    currentOrder.setOrderNumber(id);
                    currentOrder.setCustomerName(currentTokens[1]);
                    currentOrder.setState(currentTokens[2]);
                    currentOrder.setTaxRate(rate);
                    currentOrder.setProductType(currentTokens[4]);
                    currentOrder.setArea(area);
                    currentOrder.setCostPerSqFt(costSQ);
                    currentOrder.setLaborCostPerSqFt(costLabSQ);
                    currentOrder.setMaterialCost(material);
                    currentOrder.setLaborCost(labor);
                    currentOrder.setTotalTax(tax);
                    currentOrder.setTotalCost(total);
                    currentOrder.setTimeStamp(LocalDate.parse(currentTokens[12], formatter));

                    //we add that order to the orders list.
                    orders.add(currentOrder);
                    allOrders.add(currentOrder);
                }
            }
        }

        //return the list
        scanner.close();
        if(null!=date)
            return orders;
        else
            return allOrders;
    }

    private void writeOrder(LocalDate date, List<Order> completeOrders ) throws Exception{
        String ITEM_FILE;
        if(null!=date){
            ITEM_FILE = "Orders/order_" + date;
        }
        else
            ITEM_FILE = "DataExport";
        PrintWriter out;//Java PrintWriter class is the implementation of Writer class. It is used to print the formatted representation of objects to the text-output stream.

        //exception if we couldn't write to the file.
        try{
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        }catch (IOException e){
            throw new Exception(
                    "Could not save inventory data.", e
            );
        }
        
        out.print(header+"\n");//header will first be inserted then a for loop to store each value in the corresponding header

        //retrieve all the data from the current order and write them to the file
        for (Order currentOrder : completeOrders) {
            out.println(+currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getCostPerSqFt() + DELIMITER
                    + currentOrder.getLaborCostPerSqFt() + DELIMITER
                    + currentOrder.getMaterialCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTotalTax() + DELIMITER
                    + currentOrder.getTotalCost() + DELIMITER
                    + currentOrder.getTimeStamp().format(formatter));

            out.flush();
        }
        out.close();
    }
}