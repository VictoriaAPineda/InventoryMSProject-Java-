import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
/** Main Class that starts up application.
 * */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    /** Main method.
     * Can initialize applaiction with pre-populated data.
     * @param args name of array
     * */
    public static void main(String[] args){
       // Prepopulate table for quick testing
        Outsourced iO1 = new Outsourced(Inventory.getNextPartId(),"Brakes", 12.45, 20, 1, 30, "depot");
        Inventory.addPart(iO1);

        InHouse iO2 = new InHouse(Inventory.getNextPartId(),"Wheel", 34.45, 20, 1, 30, 4545);
        Inventory.addPart(iO2);

        Outsourced iO3 = new Outsourced(Inventory.getNextPartId(),"Seat", 20.45, 20, 1, 30, "depot");
        Inventory.addPart(iO3);




        Product num1 = new Product(Inventory.getNextProductId(), "bicycle", 50.00, 10, 1, 20);
        Inventory.addProduct(num1);

        Product num2 = new Product(Inventory.getNextProductId(), "mountain bike", 60.00, 5, 1, 20);
        Inventory.addProduct(num2);

        Product num3 = new Product(Inventory.getNextProductId(), "tricycle", 30.00, 15, 1, 20);
        Inventory.addProduct(num3);


        launch(args);
    }


}
