package controller;

import javafx.collections.FXCollections;
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
/** Class that adds product objects. */
public class FXMLAddProductFormController implements Initializable{

    Stage stage;
    Parent scence;

    private ObservableList<Part> assParts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> assInventoryLevelCol;

    @FXML
    private TableColumn<Part, Integer> assPartIdCol;

    @FXML
    private TableColumn<Part, String> assPartNameCol;

    @FXML
    private TableColumn<Part, Double> assPriceCol;

    @FXML
    private TableView<Part> assTableView;


    @FXML
    private TableColumn<Part, Integer> mainInventoryLevelCol;

    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;

    @FXML
    private TableColumn<Part, String> mainPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainPriceCol;

    @FXML
    private TableView<Part> mainProductTableView;


    @FXML
    private TextField autoGenIdTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField maxValueTxt;

    @FXML
    private TextField minValueTxt;

    @FXML
    private TextField nameProductTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField searchBarTxt;

    @FXML
    private Label errorOutput;



    /** Method adds a selected part from top table to the bottom table.
     * RUNTIME ERROR had an error when no part row was selected to add to
     * associated table, now throws a dialog box to have user select one
     * */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        // what user selects will be added to associated table below
        Part selectedPart = mainProductTableView.getSelectionModel().getSelectedItem();

        // if nothing is slected
        if( selectedPart == null){
            // alert add a part, alert user to select a part
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.setTitle("Add Products");
            // waits for user to click ok
            Optional<ButtonType> result = alert.showAndWait();

            return;
        }

        assParts.add(selectedPart); // adds the selected part to the associated table list
    }

    @FXML
    /** Method deletes an associated part from the associated table.
     * */
    void onActionRemovePart(ActionEvent event) {
        // put confirmation dialog box to delete

        // Confirmation for user to delete part from the part table
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this associated part?");
        alert.setTitle("Add Product");

       // Product selectedAssPart = assTableView.getSelectionModel().getSelectedItem();

        // waits for user to confirm
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            // if(){}  it has an associated part cannot delete
            // how to check??? else continue deletion [add later]
            Part selectedPart = assTableView.getSelectionModel().getSelectedItem();
            assTableView.getItems().remove(selectedPart);

        }
    }

    @FXML
    /** Method displays main form upon clicking cancel button
     * RUNTIME ERROR can get an error if not correctly linked to a fxml file
     * */
    void onActionDisplayMainForm(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        stage.setScene(new Scene(scence));
        stage.show();
    }

    @FXML
    /** Method saves data input and displays them in product table on main form
     * RUNTIME ERROR throws an exception if incorrect data type is submitted.
     * outputs error through label
     * */
    void onActionSave(ActionEvent event) throws IOException {

       try {
            // retrieves input from user and places text into variables
         //   Integer autoID = Integer.parseInt(autoGenIdTxt.getText());
            String productName = nameProductTxt.getText();
            Integer inv = Integer.parseInt(inventoryTxt.getText());
            Double price = Double.parseDouble(priceTxt.getText());
            Integer maxVal = Integer.parseInt(maxValueTxt.getText());
            Integer minVal = Integer.parseInt(minValueTxt.getText());

            if (minVal > maxVal) { // if min is greater than max value
                errorOutput.setText("Min must be less than Max value");
                errorOutput.setVisible(true);
            }
            else if ((inv < minVal) || (inv > maxVal)) { // if inv is out of range
                errorOutput.setText("Inventory must be between Min and Max values");
                errorOutput.setVisible(true);
            }

            else { // if no errors are found, place product into the Product table
                // adds a new part obj to the table
                Product product = new Product(Inventory.getNextProductId(), productName, price, inv, minVal, maxVal);

                for( Part part: assParts){
                    product.addAssociatedPart(part);
                }
                Inventory.addProduct(product);


                // takes user back to the main form screen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

                stage.setScene(new Scene(scence));
                stage.show();
            }

        }catch (NumberFormatException e){// trigger if user does not fill some/all input fields
           errorOutput.setText("Please fill out all fields with the correct data type");
           errorOutput.setVisible(true);
        }
    }


    // SEARCH BAR - - - [PART TABLE] - - -

    @FXML
    /** ActionEvent that returns search results.
    Takes in user input to scan part table
      */
    public void onActionGetsResultsFromSearch(ActionEvent event) {

        String input = searchBarTxt.getText();
        ObservableList<Part> parts = Inventory.lookupPart(input);

        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(input);
                Part pN = Inventory.lookupPart(id);

                if (pN != null) {
                    parts.add(pN);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part not Found");
                alert.setTitle("Parts");

                // waits for user to confirm
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        mainProductTableView.setItems(parts);
        searchBarTxt.setText("");
    }

    @Override
    /** FUTURE ENHANCEMENT
     *  provide more info parts such as quantity left to distribute
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        errorOutput.setVisible(false);


        // displays parts from main form in upper table
        mainProductTableView.setItems(Inventory.getAllParts());
            mainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            mainPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            mainInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // auto-generates an number for the product id field
        autoGenIdTxt.setText(String.valueOf(Inventory.getAllProducts().size()+1));

        // display output of searchbar filter
        ObservableList<Part> parts = Inventory.getAllParts();
        mainProductTableView.setItems(parts);

      // display associated table
        assTableView.setItems(assParts);
            assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            assInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }


}
