package mohamed.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import mohamed.dto.Product;

public interface UserIO {
    void print(String msg);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

//    String readOptionalState(String prompt);

    BigDecimal readBigDecimal(String prompt);
    
    BigDecimal readBigDecimal(String prompt, double min, double max);

    BigDecimal readOptionalBigDecimal(String prompt);

    LocalDate readLocalDate(String prompt);

 //   String readState(String prompt);

    String readProduct(String prompt, List<Product> list);

    String readOptionalProduct(String prompt, List<Product> productList);

    String readString(String prompt);
}
