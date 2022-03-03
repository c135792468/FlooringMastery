package mohamed.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    /*THIS CLASS WILL BE CALLED IN THE ORDER DTO*/

    private String productType; //the type of product that the user ordered
    private BigDecimal costPerSqFt; //the cost per square feet of that specific product
    private BigDecimal LaborCostPerSqFt; //Labor cost of that specific product


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return LaborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal LaborCostPerSqFt) {
        this.LaborCostPerSqFt = LaborCostPerSqFt;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.productType);
        hash = 41 * hash + Objects.hashCode(this.costPerSqFt);
        hash = 41 * hash + Objects.hashCode(this.LaborCostPerSqFt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.costPerSqFt, other.costPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.LaborCostPerSqFt, other.LaborCostPerSqFt)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Product [productType=" + productType + ", costPerSqFt=" + costPerSqFt + ", LaborCostPerSqFt="
				+ LaborCostPerSqFt + "]";
	}
}
