package mohamed.controller;

import mohamed.dto.Order;
import mohamed.service.FlooringMasteryService;
import mohamed.ui.FlooringMasteryView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryController {
    private FlooringMasteryView view;
    private FlooringMasteryService service;

    @Autowired
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            System.out.println("\n");
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        searchOrders();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        dataExport();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }

            exitMessage();
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        }
    }

    private int getMenuSelection() throws Exception {
        return view.getSelection();
    }

    private void searchOrders() {
        view.SearchBanner();
        LocalDate date = view.getDate();
        List<Order> orderedDates = service.searchOrders(date);
        view.displayByDate(date, orderedDates);

    }

    private void createOrder() {
        try {
            view.createOrderBanner();
            Order order = view.getOrderData(service.getProductList());
            Order completedOrder = service.calculateCost(order);
            view.orderSummary(completedOrder);
            String accepted = view.acceptOrder();
            if (accepted.equalsIgnoreCase("Y")) {
                service.addOrder(completedOrder);
                view.orderSuccesfullBanner();
            } else {
                view.orderNotSavedBanner();
            }

        } catch (Exception ex) {
            System.out.println("\n");
            System.out.println(ex);
        }
    }

    private void editOrder() {
        try {
            view.editBanner();
            LocalDate date = view.getDate();
            int orderNumber = view.getOrderNum();
            List<Order> orderedDates = service.searchOrders(date);
            Order updated = service.getOrder(orderedDates, orderNumber);
            Order updatedOrder = view.editOrder(updated, service.getProductList());
            Order finalOrder = service.calculateCost(updatedOrder);
            view.orderSummary(finalOrder);
            service.editOrder(date, finalOrder);
            view.editSuccessBanner();
        } catch (Exception ex) {
            System.out.println("\n");
            System.out.println(ex);

        }
    }

    private void removeOrder() {
        view.removeBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNum();
        try {
            service.removeOrder(date, orderNumber);
            view.removeSucessBanner();
        } catch (Exception ex) {
            System.out.println("Order Not Removed!");
        }
    }
    
    private void dataExport(){
        LocalDate date = null;
        view.dataExport();
        List<Order> orderedDates = service.dataExport(date);
        view.displayAllDate(orderedDates);
    }
    
    private void unknownCommand() {
        System.out.println("Unknown Command! Please Enter a Valid Selection");
    }

    private void exitMessage() {
        System.out.println("Thanks for Visiting! See You Next Time!");
    }
}
