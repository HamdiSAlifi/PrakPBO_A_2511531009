package shop.model;

import shop.shipping.Shippable;

public class PhysicalProduct extends Product implements Shippable {
    private final int weightGrams;
    
    public PhysicalProduct(String sku, String name, long price, int weightGrams) {
        super(sku, name, price);
        if (weightGrams <= 0) {
            throw new IllegalArgumentException("Weight must not be negative nor zero");
        }
        this.weightGrams = weightGrams;
    }
    @Override
    public int getWeightGrams() {
        return weightGrams;
    }

    @Override
    public String category() {
        return "Fisik";
    }
}