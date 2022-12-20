package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Main class that is the main form. */
public class FXMLMainFormController implements Initializable {

    // used to help navigation
    Stage stage;
    Parent scence;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableColumn<Part, Integer> partsInventoryLevelCol;

    @FXML
    private TextField partsSearchBarTxt;

    @FXML
    private TableView<Part> partsTableView;


    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField productsSearchbarTxt;

    @FXML
    private TableView<Product> productsTableView;

    /** Method deletes a part.
     * Method deletes a part from the part table based on user row selection
     * */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        // Confirmation for user to delete part from the part table
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        alert.setTitle("Parts");

        // waits for user to confirm
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){

            // once confirmed will now delete
            Inventory part = new Inventory();
            part.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        }
        // if user click 'Cancel' will not delete selected part
    }

    /** Method deletes a product.
     * Method deletes a product from the product table based on user row selection. Checks to see if the product
     * has associated parts, if it does, unable to delete, if none, continues on to delete.
     * */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
            // product obj that user selects to be deleted
            Product productToDelete = productsTableView.getSelectionModel().getSelectedItem();

            // if the product has associated parts (not empty )then it cannot be deleted
            // till all the associated parts are
            if (!productToDelete.getAllAssociatedParts().isEmpty()) {
                System.out.println("test");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete a product that has a associated part");
                alert.setTitle("Products");
                Optional<ButtonType> result = alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
                alert.setTitle("Products");

                // pop-up dialog box
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // once confirmed will now delete
                    Inventory.deleteProduct(productToDelete);
                    productsTableView.getItems();
                }
            }
    }

    @FXML
    /** Method displays the Add Part form.
      Upon clicking the add button, moves to the AddPartForm.fxml view
     RUNTIME ERROR could get error if fxml is not correctly linked
      */
    void onActionDisplayAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scence = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scence));
        stage.show();

    }

    @FXML
    /** Method displays the Add Product Form.
     * Displays the Add Product form on mouse click
     * RUNTIME ERROR could get error if fxml is not correctly linked
     * */
    void onActionDisplayAddProductForm(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scence = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));

        stage.setScene(new Scene(scence));
        stage.show();
    }

    @FXML
    /** Method displays the Modify Part Form.
     * Displays the Modify Part form on mouse click
     * RUNTIME ERROR could get error if fxml is not correctly linked
     * */
    void onActionDisplayModifyPart(ActionEvent event) throws IOException {

        // which view to use
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
        loader.load();

        // need to use get controller via getControlller() method
        //  to ref another controller to access that controller's public members
        FXMLModifyPartController FMPController = loader.getController();
        FMPController.sendAddedParts(partsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // ref to a container of the fxml doc
        Parent scence = loader.getRoot();

        stage.setScene(new Scene(scence));
        stage.show(); // buggy showAndWait can't use
    }

    @FXML
    /** Method displays the Modify Product Form.
     * Displays the Modify Product form on mouse click
     * RUNTIME ERROR could get error if fxml is not correctly linked
     * */
    void onActionDisplayModifyProductForm(ActionEvent event) throws IOException {

        // which view to use
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
        loader.load();

        // need to use get controller via getControlller() method
        //  to ref another controller to access that controller's public members
        FXMLModifyProductFormController FMPFController = loader.getController();
        // will send the associated parts that were added in the add product form
        // on ahead to the modify to view
        FMPFController.sendAssociatedParts(productsTableView.getSelectionModel().getSelectedItem()); //??

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // ref to a container of the fxml doc
        Parent scence = loader.getRoot();

        stage.setScene(new Scene(scence));
        stage.show(); // buggy showAndWait can't use

    }

    @FXML
    /** Method exits.
     *  Exits the application.
     * */
    void onActionExit(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    /** Method searches parts table.
     * Accepts input from user to use to search the parts table.
      */
    public void onActionGetsResultsFromPartsSearch(ActionEvent event) {

        String input = partsSearchBarTxt.getText();
        ObservableList<Part> parts = Inventory.lookupPart(input);

        if(parts.size() == 0 ){
            try {
                int id = Integer.parseInt(input);
                Part partName = Inventory.lookupPart(id);

                if (partName != null) {
                    parts.add(partName);
                }


            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part not Found");
                alert.setTitle("Parts");

                // waits for user to confirm
                Optional<ButtonType> result = alert.showAndWait();
            }

        }
        partsTableView.setItems(parts);
        partsSearchBarTxt.setText("");
    }

    @FXML
    /** Method searches product table.
     * Accepts input from user to use to search the products table
     * */
    void onActionGetResultsFromProductSearch(ActionEvent event) {

        String input = productsSearchbarTxt.getText();

        ObservableList<Product> products = Inventory.lookupProduct(input);

        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(input);
                Product pN = Inventory.lookupProduct(id);

                if (pN != null) {
                    products.add(pN);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product not Found");
                alert.setTitle("Products");

                // waits for user to confirm
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        productsTableView.setItems(products);
        productsSearchbarTxt.setText("");
    }


    /** Initialize method.
     * Initializes the main form page
     * */
    @Override
    /** FUTURE ENHANCEMENT
     * Could add more detail about type of part inhouse/outsource
     * and display how many associated parts a product has
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    // PARTS TABLES - - -

        // takes info from ADDPartController partsTableView table
        // set up the cells for display
        partsTableView.setItems(Inventory.getAllParts());
            partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            partsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

            // display those through searchbar filter
            ObservableList<Part> parts = Inventory.getAllParts();
            partsTableView.setItems(parts);

    // PRODUCT TABLE - - -

        // takes info from ADDProductsController  productsTableView table
        // set up the cells for display
        productsTableView.setItems(Inventory.getAllProducts());
            productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

            // display those through searchbar filter
            ObservableList<Product> products = Inventory.getAllProducts();
            productsTableView.setItems(products);

    }
}



