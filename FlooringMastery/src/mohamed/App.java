package mohamed;

import mohamed.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
    	AnnotationConfigApplicationContext ctxt = new AnnotationConfigApplicationContext();
    	ctxt.scan("mohamed");
    	ctxt.refresh();
    	
    	FlooringMasteryController controller = ctxt.getBean(FlooringMasteryController.class);
        controller.run();
    }
}
