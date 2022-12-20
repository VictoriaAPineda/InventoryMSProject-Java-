package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** This class modifies part objects. */
public class FXMLModifyPartController implements Initializable{
    /** Label that changes its output. */
    public Label ChangeLabel;



    Stage stage;
    Parent scence;

    @FXML
    private TextField autoGenIdTxt;

    @FXML
    private RadioButton inHouseRbtn;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private TextField maxValueTxt;

    @FXML
    private TextField minValueTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField priceTxt;

    @FXML
    private Label errorOutput;

    @FXML
    /** Method that displays the Main form after clicking the cancel button
     * RUNTIME ERROR could cause exception if view is not correctly linked.
     * */
    void onActionDisplayMainForm(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        stage.setScene(new Scene(scence));
        stage.show();
    }

    @FXML
    /** Method that displays the Main form after clicking the save button
     * RUNTIME ERROR enforces an exception if input is unacceptable.
     * will generate a error message via a label
     * */
    void onActionSave(ActionEvent event) throws IOException {

        try{
            Integer autoID= Integer.parseInt(autoGenIdTxt.getText());
            String partName =  nameTxt.getText();
            Integer inv =    Integer.parseInt( inventoryTxt.getText());
            Double  price = Double.parseDouble( priceTxt.getText());
            Integer maxVal= Integer.parseInt(maxValueTxt.getText());
            Integer minVal= Integer.parseInt(minValueTxt.getText());


            if(inHouseRbtn.isSelected()){
                // gets input from user and places them into variables to be
                // be ultilized
                Integer machineID = Integer.parseInt(machineIdTxt.getText());

                if (minVal > maxVal) { // if min is greater than max value
                    errorOutput.setText("Min must be less than Max value");
                    errorOutput.setVisible(true);
                }
                if ((inv < minVal) || (inv > maxVal)) { // if inv is out of range
                    errorOutput.setText("Inventory must be between Min and Max values");
                    errorOutput.setVisible(true);
                }
                else{
                    // changes the part object
                    Inventory inventory = new Inventory();
                    inventory.updatePart(autoID, new InHouse(autoID,partName, price, inv, minVal, maxVal, machineID));

                    // goes back to main form to display change data
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

                    stage.setScene(new Scene(scence));
                    stage.show();

                }

            }
            if (outsourcedRBtn.isSelected()){
                // gets input from user and places them into variables to be
                // be ultilized
                String companyName = machineIdTxt.getText();

                if (minVal > maxVal) { // if min is greater than max value
                    errorOutput.setText("Min must be less than Max value");
                    errorOutput.setVisible(true);
                }
                else if ((inv < minVal) || (inv > maxVal)) { // if inv is out of range
                    errorOutput.setText("Inventory must be between Min and Max values");
                    errorOutput.setVisible(true);

                }
                else{
                    // changes the part object
                    Inventory inventory = new Inventory();
                    inventory.updatePart(autoID, new Outsourced(autoID,partName, price, inv, minVal, maxVal, companyName));

                    // goes back to main form to display change data
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

                    stage.setScene(new Scene(scence));
                    stage.show();
                }

            }

        }catch (NumberFormatException e){
            errorOutput.setText("Please fill out all fields with the correct data type");
            errorOutput.setVisible(true);
        }

    }

    // accepts data from MainForm that will then afterwards be retrieved by the MainForm
    // to display modified data

    /** Method sends an added part.
     * Sends an added part that has been modified
     * @param part takes a part object
     * */
    public void sendAddedParts(Part part){

        // gets the input to send back
        autoGenIdTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        inventoryTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxValueTxt.setText(String.valueOf(part.getMax()));
        minValueTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse){// inHouse is subclass of Part
            // need to cast part to access InHouse getMachineId method
            // also change int to a string
            ChangeLabel.setText("Machine ID");
            inHouseRbtn.setSelected(true);
            machineIdTxt.setText(String.valueOf(((InHouse) part).getMachineid()));
        }
        if (part instanceof Outsourced){// inHouse is subclass of Part
            // need to cast part to access InHouse getMachineId method
            // also change int to a string
            ChangeLabel.setText("Company Name");
            outsourcedRBtn.setSelected(true);
            machineIdTxt.setText(((Outsourced) part).getCompanyName());
        }

    }

    /** ActionEvent which changes a label.
     * Changes label based on which radio button is selected.
     * @param actionEvent clicking inHouse radio button
     * */
    public void onActionInHouse(ActionEvent actionEvent) {
        ChangeLabel.setText("Machine ID");
    }
    /** ActionEvent which changes a label.
     * Changes label based on which radio button is selected.
     * @param actionEvent clicking outsource radio button
     * */
    public void onActionOutsourced(ActionEvent actionEvent) {
        ChangeLabel.setText("Company Name");
    }


    @Override
    /* FUTURE ENHANCEMENT
      could put a save dialog box to further confirm that the user wants to change that part or another
      dialog box upon clicking cancel to confirm that no changes be made to product.
    * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        

        errorOutput.setVisible(false);

    }


}
