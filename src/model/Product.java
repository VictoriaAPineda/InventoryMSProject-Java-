package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** The Product class works with the parts and products.
 * This class works with products and their parts
 * */
public class Product {


    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /** Constructor for a product object.
     * @param id id of product
     * @param name name of product
     * @param price price of a product
     * @param stock quantity of a product
     * @param min minimum quantity of a product
     * @param max maximum quantity of a product
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.name= name;
        this.min = min;
        this.max = max;
    }
// SETTERS AND GETTERS - - -

    /**
     @return the id of product
     */
    public int getId() {
        return id;
    }
    /**
     * @param id id to set
     * */
    public void setId(int id) {
        this.id = id;
    }
    /**
     @return the name of product
     */
    public String getName() {
        return name;
    }
    /**
     * @param name name to set
     * */
    public void setName(String name) {
        this.name = name;
    }
    /**
     @return the price of a product
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price price to set
     * */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     @return the stock quantity of a product
     */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock stock to set
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     @return the  minimum of stock quantity of a product
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min min to set
     * */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     @return the  max of stock quantity of a product
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max max to set
     * */
    public void setMax(int max) {
        this.max = max;
    }


    /** Method adds a associated part.
     * Adds a associated part to a product
     * @param part accepts a part object to add to the associated table
     * */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);

    }
    /** Method to delete a associated part.
     * deletes a associated part to a product
     * @param part accepts a part object to remove from the associated table
     * @return returns all parts and removes the associated part
     * */
    public boolean deleteAssociatedPart(Part part){
        for(Part index : Inventory.getAllParts()){
            if(index == part){
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    /**
     @return associated parts of a product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
