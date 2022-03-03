package mohamed.ui;

import mohamed.dto.Order;
import mohamed.dto.Product;
import mohamed.service.FlooringMasteryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryView {
    private UserIO io;
    private String types;

    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int getSelection() {
        io.print("Welcome To The Masters Of Flooring!");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. DataExport");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public LocalDate getDate() {
        LocalDate dateIn = io.readLocalDate("Please Enter Order Date (MM/DD/YYYY) Format only");
        return dateIn;
    }

    public int getOrderNum() {
        System.out.println("\n");
        int orderNumber = io.readInt("Please Enter your Order Number");
        System.out.println("\n");
        return orderNumber;
    }

    public String acceptOrder() {
        String acceptOrder = io.readString("Confirm Order, Y/N ??");
        return acceptOrder;
    }

    public void orderSummary(Order order) {
        System.out.println("\n");
        System.out.println("Material Cost Per Square Foot: " + order.getCostPerSqFt());
        System.out.println("Labor Cost Per Sqaure Foot " + order.getLaborCostPerSqFt());
        System.out.println("Total Material Cost " + order.getMaterialCost());
        System.out.println("Total Labor Cost " + order.getLaborCost());
        System.out.println("Total Taxes " + order.getTotalTax());
        System.out.println("Total Cost " + order.getTotalCost());
        System.out.println("Order To Be Received at " + order.getTimeStamp());
        System.out.println("\n");
    }

    public Order editOrder(Order order, List<Product> productList) {

        BigDecimal zero = new BigDecimal("100");

        System.out.println("Leave Blank if no Changes are Needed");
        System.out.println("\n");
        String customerName = io.readString("Change Customer Name To? (" + order.getCustomerName() + ")");
        String state = io.readString("Change Location To? (" + order.getState() + ")");
        String productType = io.readOptionalProduct("Change Product Type To? (" + order.getProductType() + ")", productList);
        BigDecimal area = io.readOptionalBigDecimal("Change Area of Product?(" + order.getArea() + ")");

        if (customerName.trim().length() == 0) {
            customerName = order.getCustomerName();
        } else {
            order.setCustomerName(customerName);
        }
        if (state.trim().length() == 0) {
            state = order.getState();
        } else {
            order.setState(state);
        }
        if (productType.trim().length() == 0) {
            productType = order.getProductType();
        } else {
            order.setProductType(productType);
        }
        if (area.compareTo(zero) <= 0) {
            area = order.getArea();
        } else {
            order.setArea(area);
        }

        return order;
    }

    public Order getOrderData(List<Product> productList) throws Exception {
    	String customerName = "";
    	String regex = "^[a-zA-Z0-9., ]+$";
    	Pattern pattern = Pattern.compile(regex);
    	boolean nameIsValid = false;
    	
    	do {
            customerName = io.readString("Please Enter Customer Name");
        	Matcher matcher = pattern.matcher(customerName);
            if(matcher.matches()) nameIsValid = true;
    	} while(!nameIsValid);
    	
        String state = io.readString("Please enter the State of Where the Sale is Occuring " );
        String productType = io.readProduct("Please select the Product Type:" + "\n", productList);
        BigDecimal area = io.readBigDecimal("Please enter the Area in SqFeet that you want to Cover (min 100 sq ft)"
        		, 100.0, Double.MAX_VALUE);
        LocalDate time = LocalDate.now();
        Order currentOrder = new Order();

        currentOrder.setOrderNumber(currentOrder.getOrderNumber());
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);
        currentOrder.setTimeStamp(time);
        return currentOrder;
    }

    public void displayByDate(LocalDate date, List<Order> orders) {
    	io.print(String.format("%n%-13s%-22s%-6s%-10s%-12s%s", "Order_number", "Customer_name", "State", 
    			"Product", "Total", "Date"));
    	orders.stream()
    		.forEach(o -> {
    			if(o.getTimeStamp().equals(date)) {
	    			io.print(
	    				String.format("%-13s%-22s%-6s%-10s%-12s%s", 
							o.getOrderNumber(), 
							o.getCustomerName(), 
							o.getState(),
							o.getProductType(),
							o.getTotalCost(),
							o.getTimeStamp()
	    				)
	    			);
    			}
	    	}); 		 		
    }
    
    public void displayAllDate(List<Order> orders) {
    	io.print(String.format("%n%-13s%-22s%-6s%-10s%-12s%s", "Order_number", "Customer_name", "State", 
    			"Product", "Total", "Date"));
    	orders.stream()
    		.forEach((o) -> 
    			io.print(
    				String.format("%-13s%-22s%-6s%-10s%-12s%s", 
	    				o.getOrderNumber(), 
	    				o.getCustomerName(), 
	    				o.getState(),
	    				o.getProductType(),
	    				o.getTotalCost(),
	    				o.getTimeStamp()
    				)
    			)
    		);
    }
    
    public void orderSuccesfullBanner() {
        System.out.println("Your Order Has Been Successfully Stored In The System.");
        System.out.println("\n");
    }

    public void SearchBanner() {
        System.out.println("===SEARCH BY DATE===");
    }

    public void editBanner() {
        System.out.println("===ORDER EDIT===");
    }

    public void createOrderBanner() {
        System.out.println("===CREATE AN ORDER===");
    }

    public void removeBanner() {
        System.out.println("===REMOVE ORDER===");
    }
    
    public void dataExport() {
        System.out.println("===Data Export===");
    }

    public void removeSucessBanner() {
        System.out.println("Your Order Has Been Successfully Removed From the System.");
    }

    public void editSuccessBanner() {
        System.out.println("Your Order Has Been Successfully Updated.");
    }

    public void orderNotSavedBanner() {
        System.out.println("Order not Saved, Returning To Main Menu!");
        System.out.println("\n");
    }
}
