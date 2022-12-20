package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class adds a part. */
public class FXMLAddPartController implements Initializable{
    // Label field that will change upon selected radio Buttons

    /** Label that will change its output.*/
    public Label ChangeLabel;
    /** radio button for outsource. */
    public RadioButton outsourceRBtn;
    /** radio button for in house. */
    public RadioButton inHouseBtn;


    Stage stage;
    Parent scence;

    @FXML
    private TextField autoGenIDtxt;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField machineIDTxt;

    @FXML
    private TextField maxValueTxt;

    @FXML
    private TextField minValueTxt;

    @FXML
    private TextField nameText;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField priceTxt;

    @FXML
    private ToggleGroup SourceTG;

    @FXML
    private Label errorIDCoNameOutput;

    @FXML
    private Label errorInvOutput;

    @FXML
    private Label errorMinMaxOutput;

    @FXML
    private Label errorNameOutput;

    @FXML
    private Label errorPriceOutput;
    @FXML
    private Label errorMissingInput;

    @FXML
    /**Method displays the main form.
     * on button click goes back to main form view
     * RUNTIME ERROR can throw an exception if the view is incorrect.
     * */
    void onActionDisplayMainForm(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        stage.setScene(new Scene(scence));
        stage.show();

    }

    @FXML
    /** Method saves.
     * Saves user ipnut for a part object
     * RUNTIME ERROR exceptions occur is what is inputted is unacceptable.
     * Upon incorrect input, will display error though an label output.
     * */
    void onActionSave(ActionEvent event) throws IOException {
        try{

            String partName = nameText.getText();
            Integer inv =    Integer.parseInt(inventoryTxt.getText());
            Double  price = Double.parseDouble(priceTxt.getText());
            Integer maxVal= Integer.parseInt(maxValueTxt.getText());
            Integer minVal= Integer.parseInt(minValueTxt.getText());

                if(inHouseRBtn.isSelected()){
                    // gets input from user and places them into variables to be
                    // be ultilized
                    Integer machineID = Integer.parseInt(machineIDTxt.getText());

                        if (minVal > maxVal) { // if min is greater than max value
                            errorMinMaxOutput.setText("Min must be less than Max value");
                            errorMinMaxOutput.setVisible(true);
                        }
                        else if ((inv < minVal) || (inv > maxVal)) { // if inv is out of range
                            errorMinMaxOutput.setText("Inventory must be between Min and Max values");
                            errorMinMaxOutput.setVisible(true);
                        }
                        else{
                            // adds a new part obj tot the
                            InHouse iH = new InHouse(Inventory.getNextPartId(),partName, price, inv, minVal, maxVal, machineID);
                            Inventory.addPart(iH);

                            // goes back to main screen to display input with the
                            // assigned variables
                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

                            stage.setScene(new Scene(scence));
                            stage.show();

                        }
                }
                if (outsourceRBtn.isSelected()){
                    // gets input from user and places them into variables to be
                    // be ultilized
                    String companyName = machineIDTxt.getText();

                        if (minVal > maxVal) { // if min is greater than max value
                            errorMinMaxOutput.setText("Min must be less than Max value");
                            errorMinMaxOutput.setVisible(true);

                        }
                        else if ((inv < minVal) || (inv > maxVal)) { // if inv is out of range
                            errorMinMaxOutput.setText("Inventory must be between Min and Max values");
                            errorMinMaxOutput.setVisible(true);

                        } else{
                            // adds a new part obj
                            Outsourced iO = new Outsourced(Inventory.getNextPartId(),partName, price, inv, minVal, maxVal, companyName);
                            Inventory.addPart(iO);

                            // goes back to main screen to display input with the
                            // assigned variables
                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scence = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

                            stage.setScene(new Scene(scence));
                            stage.show();
                        }
                }

        }catch (NumberFormatException e){// trigger if user does not fill some/all input fields

            errorMissingInput.setText("Please fill out all fields with the correct data type");
            errorMissingInput.setVisible(true);
        }

    }

    /** Method changes label.
     * Changes label based on which radio button is selected
     * @param actionEvent clicking the in house radio button
     * */
    public void onActionInHouse(ActionEvent actionEvent) {
        ChangeLabel.setText("Machine ID");
    }
    /** Method changes label.
     * Changes label based on which radio button is selected
     * @param actionEvent clicking the Outsourced radio button
     * */
    public void onActionOutsourced(ActionEvent actionEvent) {
        ChangeLabel.setText("Company Name");
    }


    @Override
    /** Method initializes add part page.
     * FUTURE ENHANCEMENTS could display accpetable types of data that can be inputted into each field.
     **/
    public void initialize(URL url, ResourceBundle resourceBundle) {


        SourceTG = new ToggleGroup();
        inHouseRBtn.setToggleGroup(SourceTG);
        outsourceRBtn.setToggleGroup(SourceTG);

      errorIDCoNameOutput.setVisible(false);
      errorInvOutput.setVisible(false);
      errorMinMaxOutput.setVisible(false);
      errorNameOutput.setVisible(false);
      errorPriceOutput.setVisible(false);

      errorMissingInput.setVisible(false);



    }
}
