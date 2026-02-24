package designpatterns.behavioral.strategy.client.models;

public class Product implements Comparable<Product> {

    private String name;
    private double price;
    private int quantity;
    private double discount;

    public Product(String name, double price, int quantity, double discount) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", discount=" + discount +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        if( name != null && o.name != null)
            return name.compareTo(o.name);
        else
            return -1; // null for either
    }
}
