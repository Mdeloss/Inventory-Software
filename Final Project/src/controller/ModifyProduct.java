package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the actions and functions of the ModifyProduct view.
 * <p>RUNTIME ERROR</p>
 * <p>An error I occurred here was not being able to manipulate
 * non-static data. I corrected by creating a Product object to hold data
 * that is used in this class. </p>*/
public class ModifyProduct implements Initializable {


    //Text Fields
    @FXML
    private TextField productIDTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productInvTxt;
    @FXML
    private TextField productPriceTxt;
    @FXML
    private TextField productMaxTxt;
    @FXML
    private TextField productMinTxt;
    @FXML
    private TextField partIDTxt;

    // Part Table views. Casting and Wrappers
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part,Integer> inventoryCol;
    @FXML
    private TableColumn<Part, Double> priceCol;

    // Associated Table views. Casting and Wrappers
    @FXML
    private TableView<Part> AssocProdTableView;
    @FXML
    private TableColumn<Part, Integer> AssocIDCol;
    @FXML
    private TableColumn<Part, String> AssocNameCol;
    @FXML
    private TableColumn<Part, Integer> AssocInvCol;
    @FXML
    private TableColumn<Part, Double> AssocPriceCol;

    Stage stage;
    Parent scene;

    // Object used to hold data
    int id = 0, stock = 0, min = 0, max = 0;
    String name = null;
    double price = 0;

    public Product object = new Product(id, stock,  min, max, name, price);

    /** This method sends a selected part from the top table to the
     * bottom table and adds the part to the associated parts list of a product.
     * @param event The selection event. */
    @FXML
    public void onActionAddProduct(ActionEvent event) {
        System.out.println("Add product button clicked");

        // Select and transfer from table selection
        partIDTxt.clear();
        Part selectedObj = partTableView.getSelectionModel().getSelectedItem();

        // Logical check for part selected
        if(selectedObj == null) {
            // Error dialog box
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                return;
            }
        }

        this.object.addAssociatedPart(selectedObj);
    }
    /** This method removes a selected part from the bottom table to the
     * and removes the part from the associated parts list of a product.
     * @param event The selection event. */
    @FXML
    public void onActionRemoveProduct(ActionEvent event) {

        // Select from bottom table
        partIDTxt.clear();
        Part selectedObj = AssocProdTableView.getSelectionModel().getSelectedItem();

        // Logical check for Associated part selection
        if(selectedObj == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Choose an associated part.");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                return;
            }
        }

        // Confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove value, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            System.out.println("Remove product button clicked");

            this.object.deleteAssociatedPart(selectedObj);
        }
    }
    /** This method saves the input provided for editable fields, saves associated parts list,
     * and returns to the Main Screen.
     * @param event The selection event. */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException{

        try {
            System.out.println("Save product button clicked");


            int id = Integer.parseInt(productIDTxt.getText());
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(productInvTxt.getText());
            double price = Double.parseDouble(productPriceTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());

            this.object.setId(id);
            this.object.setStock(stock);
            this.object.setMin(min);
            this.object.setMax(max);
            this.object.setName(name);
            this.object.setPrice(price);

            // Logical check for Name
            if (productNameTxt.getText().isEmpty()) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a Name");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }

            // Logical check for Min and Max
            if ((min > max) || (min < 0)) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Min and Max value.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }
            // Logical check for Stock
            if ((stock < min) || (stock > max)) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Inventory level.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }
            // Logical check for Price
            if (price < 0) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Price.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }

            Inventory.updateProduct(id, this.object);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e) {
            System.out.println("Please enter valid value here.");
            System.out.println("Exception: " + e);
            System.out.println("Exception: " + e.getMessage());

            // Warning dialog box
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }
    /** This method cancels all actions and returns to Main Screen,
     * but will first ask for confirmation.
     * @param event The selection event. */
    @FXML
    public void onActionCancelProduct(ActionEvent event) throws IOException{
        System.out.println("Cancel product button clicked");

        // Confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            System.out.println("Cancel part button clicked");
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /** This method filters for part name or ID and will display an
     * error if neither is found.
     * @param event The selection event. */
    @FXML
    public void onActionSearchPart(ActionEvent event) throws IOException {

        // Get user Input for name first
        String userInput = partIDTxt.getText();
        partTableView.setItems(Inventory.lookupPart(userInput));

        // If list is empty, check for an Integer.
        if (Inventory.getAllFilteredParts().size() == 0) {
            try {
                int inputID = Integer.parseInt(partIDTxt.getText());

                // Look up ID
                Part object = Inventory.lookupPart(inputID);

                // Alert for incorrect ID search
                if (object == null) {
                    Part selectedObj = null;
                    // Confirmation dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING, "ID not found");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {

                        if (selectedObj == null)
                            return;
                    }
                }
                if (object != null) {
                    Inventory.getAllFilteredParts().add(object);
                    partTableView.setItems(Inventory.getAllFilteredParts());
                }
            } catch (NumberFormatException e) {
                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Not found");
                alert.showAndWait();
            }

        }
    }

    /** This method catches information selected from the Products
     * table on the Main Screen
     * @param object  The product holding received data. */
    public void sendProduct(Product object) {
        productIDTxt.setText(String.valueOf(object.getId()));
        productNameTxt.setText(object.getName());
        productInvTxt.setText(String.valueOf(object.getStock()));
        productPriceTxt.setText(String.valueOf(object.getPrice()));
        productMaxTxt.setText(String.valueOf(object.getMax()));
        productMinTxt.setText(String.valueOf(object.getMin()));

        // This transfers the associated parts
        int id = -1;
        while (id < (object.getAllAssociatedParts().size() - 1)) {
                id++;
            this.object.addAssociatedPart(object.getAllAssociatedParts().get(id));
        }
    }

    /** This allows for initialization of this screen.
     * @param url
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // This shows data in the top table
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // This shows data in the bottom table
        AssocProdTableView.setItems(this.object.getAllAssociatedParts());
        AssocIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}