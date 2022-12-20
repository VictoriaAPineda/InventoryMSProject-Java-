package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The inventory class works with the parts and products.
 * This class works with manipulating the part and products table
 * */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int trackPartId = 1;
    private static int trackProductId = 1;

    /**
     @return the id of part
     */
    public static int getNextPartId() {
        return trackPartId++;
    }
    /**
     * @return  the id of product
     * */
    public static int getNextProductId() {
        return trackProductId++;
    }

    /** Method adds a part.
     * Adds a part to the part table
     * @param part accepts a part object to add to the part table*/
    public static void addPart(Part part){
        allParts.add(part);
    }

    /** Method returns all parts.
     * Retrieves all part objects of the parts table
     * @return all parts
     * */
    public  static ObservableList<Part> getAllParts(){
        return  allParts;
    }

    /** Method adds a product.
     * Adds a product to the product table
     * @param product product objects to be added to table
     * */
    public  static  void addProduct(Product product){
        allProducts.add(product);
    }

    /** Method returns all products.
     * Retrieves all product objects of the products table
     * @return all products
     * */
    public  static  ObservableList<Product> getAllProducts(){
       return allProducts;
    }

// [ LOOKING FOR BY INPUT ]

    /** Method looks up part by id.
     * Searches for part object by part id
     * @param partId id of part
     * @return part or null if no part is found
     * */
    public static Part lookupPart ( int partId){

        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {

            Part part = allParts.get(i);

            if (part.getId() == partId) {
                return part; // adds obj to the table when there's a match
                // there should only be one since unique id
            }
        }
        return null;
    }
    /** Method looks up product by id.
     * Searches for part object by product id
     * @param productId id of product
     * @return product or null if no product is found
     * */
    public static Product lookupProduct (int productId){
        ObservableList<Part> allParts = Inventory.getAllParts();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++){

            Product product = allProducts.get(i);

            if (product.getId() == productId){
                return product;
            }
        }
        return  null; // returns nothing if no matches
    }
    /** Method looks up part by name.
     * Searches for part object by part name.
     * @param partName id of part
     * @return part or null if no part is found
     * */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part pN : allParts) {
            if (pN.getName().toLowerCase().contains(partName.toLowerCase())) {
                namedParts.add(pN); // adds obj to the table when there's a match
            }
        }
        return namedParts;
    }
    /** Method looks up product by name.
     * Searches for part object by part name.
     * @param productName id of product
     * @return product or null if no product is found
     * */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product name : allProducts){
            if(name.getName().toLowerCase().contains(productName.toLowerCase())){
                namedProducts.add(name);
            }

        }
        return  namedProducts;
    }

// [ UPDATE ]
    /** Method updates part table.
     * Updates a part object after it is modified
     * @param index of part
     * @param selectedPart part that is selected to be changed
     **/
    public static void updatePart(int index, Part selectedPart){
        int i = -1;

        for (Part part: Inventory.getAllParts()){
            i++;
            if(part.getId() == index){ // need index not id
                Inventory.getAllParts().set(i,selectedPart);

            }
        }
    }
    /** Method updates product table.
     * Updates a product object after it is modified
     * @param index of product
     * @param newProduct product that is selected to be changed
     **/
    public static void  updateProduct(int index, Product newProduct){
        int i =-1;

        for (Product product:Inventory.getAllProducts()) {
            i++;
            if (product.getId() == index) {
                Inventory.getAllProducts().set(i, newProduct);
            }
        }
    }

//  [ DELETE ITEMS ]
    /** Method deletes a part.
     * Deletes a part object from part table.
     * @param selectedPart part selected to be deleted from table.
     * @return all parts of table and removes the deleted part.
     **/
    public static boolean deletePart(Part selectedPart){
        for (Part part:Inventory.getAllParts()){
                if(part == selectedPart){
                    return Inventory.getAllParts().remove(part);
                }
            }
        return false;
    }
    /** Method deletes a product.
     * Deletes a product object from product table.
     * @param selectedProduct product selected to be deleted from table.
     * @return all products of table and removes the deleted product.
     **/
    public static boolean deleteProduct(Product selectedProduct){
        for (Product product:Inventory.getAllProducts()){
            if(product == selectedProduct){
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }

}