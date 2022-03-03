package mohamed.dao;

import mohamed.dto.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryProductDaoImpl implements FlooringMasteryProductDao {
    private final String ITEM_FILE = "Data/Products.txt";
    private final String DELIMITER = "::";
    
    public List<Product> loadProductList() throws Exception {
        List<Product> products = new ArrayList<>();
        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new Exception(
                    "-_- Could not Find Products.", e);
        }
        String currentLine;

        String[] currentTokens;
        scanner.nextLine(); //skip header
        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentTokens = currentLine.split(DELIMITER);

            if (currentTokens.length == 3) {
                Product currentProduct = new Product();

                BigDecimal cost = new BigDecimal(currentTokens[1]);
                BigDecimal labor = new BigDecimal(currentTokens[2]);

                currentProduct.setProductType(currentTokens[0]);
                currentProduct.setCostPerSqFt(cost);
                currentProduct.setLaborCostPerSqFt(labor);
                products.add(currentProduct);
            }
        }

        scanner.close();
        return products;
    }
}
