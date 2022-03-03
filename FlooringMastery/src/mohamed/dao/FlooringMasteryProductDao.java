package mohamed.dao;

import mohamed.dto.Product;

import java.util.List;

public interface FlooringMasteryProductDao {
    List<Product> loadProductList() throws Exception;

}
