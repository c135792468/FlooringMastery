package mohamed.dao;

import mohamed.dto.Tax;

import java.util.List;

public interface FlooringMasteryTaxDao {
    void loadTaxRates() throws Exception;

	Tax getTax(String state) throws Exception;
}
