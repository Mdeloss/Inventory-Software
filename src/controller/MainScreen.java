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
import model.*;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the actions and functions of the MainScreen view.
 * <p>RUNTIME ERROR</p>
 * <p>An error I occurred here was searching for name and ID's. I corrected this by logically
 * filtering with "if" statements. I also used alert dialog boxes to prevent further program execution
 * when conditions are met.
 * @see public void onActionSearchPart() </p>*/
public class MainScreen implements Initializable {

        Stage stage;
        Parent scene;

        @FXML
        private TextField partIDTxt;
        @FXML
        private TextField productIDTxt;

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

        // Product Table views. Casting and Wrappers
        @FXML
        private TableView<Product> productTableView;
        @FXML
        private TableColumn<Product, Integer> productIDCol;
        @FXML
        private TableColumn<Product, String> productNameCol;
        @FXML
        private TableColumn<Product,Integer> prodInventoryCol;
        @FXML
        private TableColumn<Product, Double> productPriceCol;

        // Parts
        /** This method deletes a selected part.
         * @param event The selection event. */
        @FXML
        public void onActionDeletePart(ActionEvent event) throws IOException {
            System.out.println("Delete button clicked");

            Part selectedObj = null;
            int objectID = -1;

            // Delete from table selection
            partIDTxt.clear();
            selectedObj = partTableView.getSelectionModel().getSelectedItem();

            // Logical check for selected part
            if(selectedObj == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Choose a part.");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete part, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {

                if (selectedObj == null)
                    return;

                Inventory.deletePart(selectedObj.getId());
            }
        }
        /** This method sends a selected part to the ModifyPart screen
         * and launches the ModifyPart screen.
         * @param event The selection event. */
        @FXML
        public void onActionModifyPart(ActionEvent event) throws IOException{

            try {
                System.out.println("Modify button clicked");

                // Mouse click
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
                loader.load();

                ModifyPart selectedItem = loader.getController();

                selectedItem.sendPart(partTableView.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch(NullPointerException e){
                    System.out.println("Please select a part.");
                    System.out.println("Exception: " + e);
                    System.out.println("Exception: " + e.getMessage());

                    // Warning dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Please select a part.");
                    alert.showAndWait();
                }

        }
        /** This method launches the AddPart screen.
         * @param event The selection event. */
        @FXML
        public void onActionAddPart(ActionEvent event) throws IOException{
            System.out.println("Add button clicked");
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
            stage. setScene(new Scene(scene));
            stage.show();
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


                // Displays table with data
                partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }

        // Products
        /** This method deletes a selected product.
         * @param event The selection event. */
        @FXML
        public void onActionDeleteProduct(ActionEvent event) throws IOException{
                System.out.println("Delete button clicked");

                Product selectedObj = null;
                int objectID = -1;

                // Delete from table selection
                productIDTxt.clear();
                selectedObj = productTableView.getSelectionModel().getSelectedItem();

                // Logical check for selected Products
                if(selectedObj == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Choose a product.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.get() == ButtonType.OK) {
                        return;
                    }
                }

                // Logical check for Products with associated parts
                if(selectedObj.getAllAssociatedParts().size() > 0) {
                        // Error dialog box
                        Alert alert = new Alert(Alert.AlertType.ERROR, "This Product contains parts. Please modify and remove associated parts.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            return;
                        }
                    }

                // Confirmation dialog box
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete part, do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {

                    if (selectedObj == null)
                     return;

                Inventory.deleteProduct(selectedObj.getId());
            }
        }
        /** This method sends a selected product to the ModifyProduct screen
         * and launches the ModifyProduct screen.
         * @param event The selection event. */
        @FXML
        public void onActionModifyProduct(ActionEvent event) throws IOException {
            try{
            System.out.println("Modify button clicked");

            // Mouse click
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProduct selectedItem = loader.getController();
            selectedItem.sendProduct(productTableView.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
            catch(NullPointerException e){
                System.out.println("Please select a product.");
                System.out.println("Exception: " + e);
                System.out.println("Exception: " + e.getMessage());

                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please select a product.");
                alert.showAndWait();
            }

        }
        /** This method launches the AddProduct screen.
         * @param event The selection event. */
        @FXML
        public void onActionAddProduct(ActionEvent event) throws IOException{
            System.out.println("Add button clicked");
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
            stage. setScene(new Scene(scene));
            stage.show();

        }
        /** This method filters for product name or ID and will display an
         * error if neither is found.
         * @param event The selection event. */
        @FXML
        public void onActionSearchProduct(ActionEvent event) throws IOException {

        // Get user Input for name first
        String userInput = productIDTxt.getText();
        productTableView.setItems(Inventory.lookupProduct(userInput));

        // If list is empty, check for an Integer.
        if (Inventory.getAllFilteredProducts().size() == 0) {
            try {
                int inputID = Integer.parseInt(productIDTxt.getText());

                // Look up ID
                Product object = Inventory.lookupProduct(inputID);

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
                    Inventory.getAllFilteredProducts().add(object);
                    productTableView.setItems(Inventory.getAllFilteredProducts());
                }
            }
            catch (NumberFormatException e) {
                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Not found");
                alert.showAndWait();
            }

        }

        // Displays table with data
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

        // Exit Button
        /** This method will exit the application after a confirmation alert.
         * @param event The selection event. */
        @FXML
        public void onActionExitInventory(ActionEvent event) {
            System.out.println("Exit button clicked.");
            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.out.println("Exiting application");
                System.exit(0);
            }
        }

        /** This allows for initialization of this screen with table data populated.
         * @param url
         * @param resourceBundle */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // This shows data in the table
            partTableView.setItems(Inventory.getAllParts());
            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            productTableView.setItems(Inventory.getAllProducts());
            productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            prodInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));




        }


    }

