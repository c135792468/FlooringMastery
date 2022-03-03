package mohamed.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import mohamed.dto.Product;

@Component
public class UserIOConsoleImpl implements UserIO{
    private final Scanner input = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return input.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        boolean valid = false;
        int result = 0;
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = Integer.parseInt(value);
                valid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number.\n", value);
            }
        } while (!valid);
        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        boolean valid = false;
        int result = 0;

        do {
            result = readInt(prompt);
            if (result >= min && result <= max) {
                valid = true;
            } else {
                System.out.printf("The value must be between %d and %d.\n", min, max);
            }
        } while (!valid);

        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean isValid = false;
        BigDecimal result = BigDecimal.ZERO;
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = new BigDecimal(value);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number. \n", ex);
            }
        } while (!isValid);
        return result;
    }

    public BigDecimal readBigDecimal(String prompt, double min, double max) {
        boolean isValid = false;
        BigDecimal result = BigDecimal.ZERO;
        do {
            String value = null;
            try {
                value = readString(prompt);
                double in = Double.parseDouble(value);
                result = new BigDecimal(value);
                if(in >= min && in <= max) isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("Please enter an area value of at least 100 sq ft. \n", ex);
            }
        } while (!isValid);
        return result;
    }
    
    @Override
    public BigDecimal readOptionalBigDecimal(String prompt) {
        boolean isValid = false;
        BigDecimal result = BigDecimal.ZERO;
        do {
            String value = null;
            try {
                value = readString(prompt);
                if (!value.equals("")) {
                    result = new BigDecimal(value);
                    isValid = true;
                } else {
                    result = BigDecimal.ZERO;
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number. \n");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        boolean isValid = false;
        LocalDate result = LocalDate.MAX;
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = LocalDate.parse(value, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                isValid = true;
            } catch (DateTimeParseException ex) {
                System.out.printf("The value '%s' is not a Date. \n", ex);
            }
        } while (!isValid);
        return result;
    }
/*
    @Override
    public String readState(String prompt) {
        boolean valid = false;
        String result = "";
        do {
            String value = null;

            value = readString(prompt);
            if (value.equalsIgnoreCase("OH") || value.equalsIgnoreCase("PA") || value.equalsIgnoreCase("MI") || value.equalsIgnoreCase("IN")) {
                result = value;
                valid = true;
            } else {
                System.out.printf("You Must Select One Of The Options Provided!  ");
            }

        } while (!valid);
        return result;
    }

    @Override
    public String readOptionalState(String prompt) {
        boolean valid = false;
        String result = "";
        do {
            String value = null;

            value = readString(prompt);
            if (value.equals("OH") || value.equalsIgnoreCase("PA") || value.equalsIgnoreCase("MI") || value.equalsIgnoreCase("IN")) {
                result = value;
                valid = true;
            } else if(value.equals("")) {
                result = value;
                valid = true;
            } else {
                System.out.printf("You Must Select One Of The Options Provided!  ");
            }

        } while (!valid);
        return result;
    }
*/
    @Override
    public String readProduct(String prompt, List<Product> productList) {
        for(Product p : productList) {
        	System.out.println(p.getProductType() + ", Material Cost per sq ft: " + p.getCostPerSqFt()
        		+ " Labor Cost per sq ft: " + p.getLaborCostPerSqFt());
        }
        boolean valid = false;
        String result;
        	do {
                result = readString(prompt);
                for(Product p : productList) {
                	if(p.getProductType().equalsIgnoreCase(result)) valid = true;
                }
        	} while(!valid);
        return result;
    }

    @Override
    public String readOptionalProduct(String prompt, List<Product> productList) {
        for(Product p : productList) {
        	System.out.println(p.getProductType() + ", Material Cost per sq ft: " + p.getCostPerSqFt()
        		+ " Labor Cost per sq ft: " + p.getLaborCostPerSqFt());
        }
        boolean valid = false;
        String result;
        	do {
                result = readString(prompt);
                if (result.trim().length() == 0) valid = true;
                for(Product p : productList) {
                	if(p.getProductType().equalsIgnoreCase(result)) valid = true;
                }
        	} while(!valid);
        return result;
    }
}
