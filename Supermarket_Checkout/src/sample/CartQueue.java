package sample;

public class CartQueue {

    Products products;
    private int amount;

    public CartQueue(Products products)
    {
        this.products = products;
        this.amount = 1;
    }

    public int getAmount(){return amount;}
    public void setAmount(int amount){this.amount = amount;}

    //public Products getProducts() {return products;}
    //public void setProducts(Products products) {this.products = products;}

    //public String getName(){return products.getName();}
    public double getCode(){return products.getCode();}
    //public double getPrice(){return products.getPrice();}

    //@Override
    public String toString(){return products.getName()+"("+ amount+")";}
}
