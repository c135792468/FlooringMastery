package mohamed.dao;

import mohamed.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryTaxDaoImpl implements FlooringMasteryTaxDao{
    private final String ITEM_FILE = "Data/Taxes.txt";
    private final String DELIMITER = "::";

    private Map<String, Tax> taxes = new HashMap<>();

    @Override
    public void loadTaxRates() throws Exception {
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
                Tax currentTax = new Tax();

                BigDecimal cost = new BigDecimal(currentTokens[2]);

                currentTax.setState(currentTokens[0]);
                currentTax.setTaxRate(cost);

                taxes.put(currentTokens[0] ,currentTax);
            }

        }
        scanner.close();
    }
    
    @Override
    public Tax getTax(String state) throws Exception {
        loadTaxRates();
        return taxes.get(state.toUpperCase());
    }   
}
