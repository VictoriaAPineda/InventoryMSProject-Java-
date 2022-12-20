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
/** Class that modifies a product object. */
public class FXMLModifyProductFormController implements Initializable{

    Stage stage;
    Parent scence;

    private ObservableList<Part> assParts = FXCollections.observableArrayList();

    Product product = new Product(0,null, 0.0, 0, 0 , 0);

    // botttom table that holds associated parts - - - v
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

    // - - - upper main form table [PART Table] v
    @FXML
    private TableColumn<Part, Integer> mainInventoryLevelCol;

    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;

    @FXML
    private TableColumn<Part, Integer> mainPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainPriceCol;

    @FXML
    private TableView<Part> mainProductTableView;

    // - - -

    @FXML
    private TextField autoGenIdTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField maxValueTxt;

    @FXML
    private TextField minValueTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField searchBartxt;

    @FXML
    private Label errorOutput;


    @FXML
    /** Method adds a selected part from top table to the bottom table.
     * RUNTIME ERROR had an error when no part row was selected to add to
     * associated table, now throws a dialog box to have user select one
     * */
    void onActionAddPart(ActionEvent event) {

        // want to get selected row and copy/add into the associated table below.
        Part selectedPart = mainProductTableView.getSelectionModel().getSelectedItem();
        if( selectedPart == null){
            // alert add a part, alert user to select a part
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.setTitle("Modify Products");

            // waits for user to click ok
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        product.addAssociatedPart(selectedPart);
        assTableView.setItems(product.getAllAssociatedParts());

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
    /** Method that deletes a part from the product's associated parts table.
     * */
    void onActionRemovePart(ActionEvent event) {
        // Confirmation for user to delete part from the associated part table
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this associated part?");
        alert.setTitle("Add Product");

        // waits for user to confirm
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part selectedPart = assTableView.getSelectionModel().getSelectedItem();
            assTableView.getItems().remove(selectedPart);//remove part from table

        }
    }

    @FXML
    /** Method saves data input and displays them in product table on main form
     * RUNTIME ERROR throws an exception if incorrect data type is submitted.
     * outputs error through label
     * */
    void onActionSave(ActionEvent event) throws IOException {

        try{
                // gets input from user and places them into variables to be
                // be ultilized
                Integer autoID= Integer.parseInt(autoGenIdTxt.getText());
                String partName =  nameTxt.getText();
                Integer inv =    Integer.parseInt( inventoryTxt.getText());
                Double  price = Double.parseDouble( priceTxt.getText());
                Integer maxVal= Integer.parseInt(maxValueTxt.getText());
                Integer minVal= Integer.parseInt(minValueTxt.getText());


                if (minVal > maxVal) { // if min is greater than max valuep
                    errorOutput.setText("Min must be less than Max value");
                    errorOutput.setVisible(true);
                }
                else if ((inv < minVal) || (inv > maxVal)) { // if inv is out of range
                    errorOutput.setText("Inventory must be between Min and Max values");
                    errorOutput.setVisible(true);

                }
                // when inappropriate user data is entered in the forms; error messages should be generated [need to add]
                else{

                    // changes the product object
                    Inventory inventory = new Inventory();
                    inventory.updateProduct(autoID, new Product(autoID, partName, price, inv, minVal, maxVal));

                    // goes back to main form to display change data
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

                    stage.setScene(new Scene(scence));
                    stage.show();
                }

        }catch (NumberFormatException e){
            errorOutput.setText("Please fill out all fields with the correct data type");
            errorOutput.setVisible(true);
        }

    }

    //send associated parts from add to mod
    /** Method sends associated parts of a product.
     * @param p a product object */
    public void sendAssociatedParts(Product p){
        autoGenIdTxt.setText(String.valueOf(p.getId()));
        nameTxt.setText(p.getName());
        inventoryTxt.setText(String.valueOf(p.getStock()));
        priceTxt.setText(String.valueOf(p.getPrice()));
        maxValueTxt.setText(String.valueOf(p.getMax()));
        minValueTxt.setText(String.valueOf(p.getMin()));

    }

    // SEARCH BAR - - - [PART TABLE] - - -

    @FXML
    /** Method that gets results from search.
     * ActionEvent user clicks enter/return on search bar
     * */
    public void onActionGetsResultsFromSearch(ActionEvent event) {

        String input = searchBartxt.getText();
        ObservableList<Part> parts = Inventory.lookupPart(input);

        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(input);
                Part pN = Inventory.lookupPart(id);

                if (pN != null) {
                    parts.add(pN);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Parts not Found");
                alert.setTitle("Parts");

                // waits for user to confirm
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        mainProductTableView.setItems(parts);
        searchBartxt.setText("");
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

            //  auto-generates an number for the product id field
          //  autoGenIdTxt.setText(String.valueOf(Inventory.getAllProducts().size()+1));


        // display output of searchbar filter
        ObservableList<Part> parts = Inventory.getAllParts();
        mainProductTableView.setItems(parts);


        //   display associated table
        assTableView.setItems(product.getAllAssociatedParts());
            assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            assInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
