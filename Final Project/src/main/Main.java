package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/** This class instantiates the beginning screen of an application that displays inventory.
 * JavaDoc files are located at "Mdeloss\Inventory-Software\Final Project\javadoc"
 *<p>FUTURE ENHANCEMENT </p>
 *<p>Main class: I would add a welcome message and an option to begin a search with parts or products.</p>
 * <p>AddPart and ModifyPart class: I would show In-House and Outsource text fields simultaneously,
 * but disable whichever is not selected by the radio buttons.</p>
 * <p>AddProduct and ModifyProduct class: I would add a function that removes a selected part
 * from the top table when adding it to the bottom table. </p>
 * <p>InHouse and OutSource class: I would add a maintenance date or expiration date. </p>
 * <p>Inventory class: I would add a automatic resupply method for parts that are repeatedly ordered. </p>
 * <p>Product class: I would a total price method to calculate the price of all associated parts. </p>
 * <p>Part class: I would add an attribute for used or new condition of the parts. </p>
 * <p>RUNTIME ERROR</p>
 * <p>An error I occurred was not loading the MainScreen view when calling for a new scene. I fixed this by
 * adding "/view/" to the resource name. @see public void start()</p> */

//Scene initialization here
public class Main extends Application {

    /** This is the start method. This calls to show the MainScreen view.
     * @param stage The scene object. */
    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method.
     * This is the first method that gets called, which will also preload parts and products.
     * @param args Used to launch method */
    public static void main(String[] args){

        // Parts
        InHouse part1 = new InHouse(1, 1, 0,0, "Part A", 0.0, 1);
        InHouse part2 = new InHouse(2, 2, 0,0, "Part B", 0.0, 1);
        InHouse part3 = new InHouse(3, 2, 0,0, "Part C", 0.0, 1);
        OutSourced part4 = new OutSourced(4, 1, 0,0, "Part D", 0.0, "Company A");
        OutSourced part5 = new OutSourced(5, 1, 0,0, "Part E", 0.0, "Company B");
        OutSourced part6 = new OutSourced(6, 1, 0,0, "Part F", 0.0, "Company C");

        // Products
        Product product1 = new Product(1,1,1,1,"Product A", 1);
        Product product2 = new Product(2,2,1,1,"Product B", 1);
        Product product3 = new Product(3,3,1,1,"Product C", 1);
        Product product4 = new Product(4,4,1,1,"Product D", 1);
        Product product5 = new Product(5,5,1,1,"Product E", 1);
        Product product6 = new Product(6,6,1,1,"Product F", 1);

        // Add parts
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);

        // Add products
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);
        Inventory.addProduct(product6);

        // Add associated parts
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);

        // Launch method
        launch(args);
    }


}
