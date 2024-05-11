package sample;

import javafx.beans.property.SimpleStringProperty;

public class Products implements Comparable<Products> {

   //private SimpleStringProperty name;
   private String name;
   private double price;
   private int code;

   public Products (String name, double price, int code)
   {
      //this.name = new SimpleStringProperty(name);
      this.name = name;
      this.price = price;
      this.code = code;
   }

  // public String getName() {return name.get();}
   public String getName() {return name;}

   //public void setName(String name) {this.name = new SimpleStringProperty(name);}
   public void setName(String name) {this.name = name;}

   public double getCode() {return code;}

   public void setCode(int code) { this.code = code; }

   public double getPrice() { return price;}

   public void setPrice(double price) {this.price = price;}


  @Override
  //It is used to compare the current object with the specified object to help sort the Product List
   public int compareTo(Products oProd) {return name.compareTo(oProd.getName()); }

   public String toString() { return name;}
}
