package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the actions and functions of the ModifyPart view.
 * <p>RUNTIME ERROR</p>
 * <p>An error I occurred here was catching part sources separately. I corrected this
 * by using if/ else if statement and casting grab subclass methods. @see public void sendPart()</p>*/
public class ModifyPart implements Initializable {

    // Text fields
    @FXML
    private TextField partIDTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private Label PartSource;
    @FXML
    private TextField partSourceTxt;
    @FXML
    private TextField partMinTxt;
    @FXML
    private RadioButton inHouseRB;
    @FXML
    private RadioButton outSourcedRB;

    Stage stage;
    Parent scene;

    boolean isInHouseRB;
    boolean isOutSourcedRB;

    /** This method selects radio buttons for In-House and Outsourced parts.
     * @param event The selection event. */
    @FXML
    public void onActionRadioButton(ActionEvent event) {
        System.out.println("Radio button clicked");

        if(inHouseRB.isSelected())
            PartSource.setText("Machine ID");
        else if (outSourcedRB.isSelected())
            PartSource.setText("Company Name");

    }
    /** This method saves the input provided for editable fields, as well as
     * checks for whether InHouse or Outsource is selected before saving.
     * @param event The selection event. */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {

        // Logical check for radio buttons
        if (!inHouseRB.isSelected() && !outSourcedRB.isSelected()) {
            // Error dialog box
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select In-House or Outsource radio button.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return;
            }
        }
        System.out.println("Save part button clicked");

        // Java Exception handling here
        // InHouse Parts
        if (inHouseRB.isSelected())
            try {
                System.out.println("InHouse part saved");

                int id = Integer.parseInt(partIDTxt.getText());
                String name = partNameTxt.getText();
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                int machineId = Integer.parseInt(partSourceTxt.getText());

                // Radio buttons
                if (inHouseRB.isSelected())
                    isInHouseRB = true;
                else
                    isInHouseRB = false;

                // Logical check for Name
                if (partNameTxt.getText().isEmpty()) {
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
                // Logical check for MachineID
                if ((machineId < 0)) {
                    // Error dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid MachineID.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        return;
                    }
                }

                Inventory.updatePart(id, new InHouse(id, stock, min, max, name, price, machineId));

                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
            catch (NumberFormatException e) {

                System.out.println("Please enter valid value here.");
                System.out.println("Exception: " + e);
                System.out.println("Exception: " + e.getMessage());

                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each Text Field!");
                alert.showAndWait();
            }
        // OutSourced Parts
        else if (outSourcedRB.isSelected())
            try {
                System.out.println("Outsourced part saved");

                int id = Integer.parseInt(partIDTxt.getText());
                String name = partNameTxt.getText();
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                String companyName = partSourceTxt.getText();

                // Radio buttons
                if(outSourcedRB.isSelected())
                    isOutSourcedRB = true;
                else
                    isOutSourcedRB = false;

                // Logical check for Name
                if (partNameTxt.getText().isEmpty()) {
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

                Inventory.updatePart(id, new OutSourced(id, stock, min, max, name, price, companyName));

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
    public void onActionCancelPart(ActionEvent event) throws IOException{
        System.out.println("Cancel part button clicked");

        // Confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            System.out.println("Cancel part button clicked");
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /** This method catches information selected from the Parts table
     * on the Main Screen
     * @param object  The part holding received data. */
    public void sendPart(Part object) {
        partIDTxt.setText(String.valueOf(object.getId()));
        partNameTxt.setText(object.getName());
        partInvTxt.setText(String.valueOf(object.getStock()));
        partPriceTxt.setText(String.valueOf(object.getPrice()));
        partMaxTxt.setText(String.valueOf(object.getMax()));
        partMinTxt.setText(String.valueOf(object.getMin()));

        //Casting to grab subclass method.
        if (object instanceof InHouse) {
            PartSource.setText("Machine ID");
            inHouseRB.setSelected(true);
            partSourceTxt.setText(String.valueOf(((InHouse) object).getMachineId()));
        }

           else if (object instanceof OutSourced) {
            PartSource.setText("Company Name");
            outSourcedRB.setSelected(true);
            partSourceTxt.setText(((OutSourced) object).getCompanyName());
        }

    }

    /** This allows for initialization of this screen.
     * @param url
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
