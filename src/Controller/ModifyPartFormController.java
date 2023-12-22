/**
 * @author Charan Williams
 * */
package Controller;

import Model.Inventory.Inventory;
import Model.Part.InHouse;
import Model.Part.Outsourced;
import Model.Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartFormController {
    /**The stage of the application*/
    Stage stage;
    /**The scene of the application*/
    Parent scene;
    /**The part being modified*/
    Part part;

    @FXML
    private TextField idField;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outSourcedRadioButton;

    @FXML
    private TextField invField;

    @FXML
    private TextField machineIdOrCompanyNameField;

    @FXML
    private Label machineIdOrCompanyNameLabel;

    @FXML
    private TextField maxField;

    @FXML
    private TextField minField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceCostField;

    /**Gets window from a button and casts it to a stage, and then loads a scene from the provided path.
     * @param event the event
     * @param path the path of the scene to be loaded
     * */
    void changeScene(ActionEvent event, String path) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Retrieves a part from another controller when called and sets the text fields to display the part's data. Checks if the in-house or outsourced radio button is selected and creates the appropriate part. Checks if the part is in-house or outsourced, then selects the appropriate radio button, and changes the machine ID or company name label to the appropriate text.
     * @param part the part to retrieve
     * */
    public void retrievePartData(Part part) {
        this.part = part;
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(String.valueOf(part.getName()));
        priceCostField.setText(String.valueOf(part.getPrice()));
        invField.setText(String.valueOf(part.getStock()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            machineIdOrCompanyNameLabel.setText("Machine ID");
            machineIdOrCompanyNameField.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else {
            outSourcedRadioButton.setSelected(true);
            machineIdOrCompanyNameLabel.setText("Company Name");
            machineIdOrCompanyNameField.setText(((Outsourced) part).getCompanyName());

        }
    }

    /**Sets the machine ID or company name label to Machine ID when the in-house radio button is selected
     * @param event the event triggered when the in-house radio button is selected
     * */
    @FXML
    void inHouseSelected(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Machine ID");
    }
    /**Sets the machine ID or company name label to Company Name when the outsourced radio button is selected
     * @param event the event triggered when the in-house radio button is selected
     * */
    @FXML
    void outsourcedSelected(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Company Name");
    }

    /**Returns to the main form without saving any changes when the cancel button is clicked
     * @param event the event triggered when the cancel button is clicked
     * */
    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "/View/MainForm.fxml");
    }

    /**Saves the changes made to the part when the save button is clicked. Checks the data types of the price, stock, min, max, and machine ID fields, and checks if name or company name is blank. Also checks that min is less than max, and that stock is in-between min and max.
     * @param event the event triggered when the save button is clicked
     * */
    @FXML
    void saveButtonClicked(ActionEvent event) throws IOException {
        int id = part.getId();
        String name = nameField.getText();
        double price;
        int stock;
        int min;
        int max;
        if (name.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data in Name field. Name cannot be blank.");
            error.showAndWait();
            return;
        }
        try {
            price = Double.parseDouble(priceCostField.getText());
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data in Price/Cost field. Price must be entered as a double.");
            error.showAndWait();
            return;
        }
        try {
            stock = Integer.parseInt(invField.getText());
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data in Inv field. Inv must be entered as an integer.");
            error.showAndWait();
            return;
        }
        try {
            min = Integer.parseInt(minField.getText());
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data in Min field. Min must be entered as an integer.");
            error.showAndWait();
            return;
        }
        try {
            max = Integer.parseInt(maxField.getText());
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data in Max field. Max must be entered as an integer.");
            error.showAndWait();
            return;
        }
        //Check to make sure that min is less than max, and stock is in-between min and max
        if ((min > max)) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data. Min must be less than max.");
            error.showAndWait();
        } else if (stock > max || stock < min) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Data");
            error.setContentText("Invalid data. Inv must be in-between min and max.");
            error.showAndWait();
        }
        else {
            if (inHouseRadioButton.isSelected()) {
                int machineId;
                try{
                    machineId = Integer.parseInt(machineIdOrCompanyNameField.getText());
                } catch (NumberFormatException e){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Invalid Data");
                    error.setContentText("Invalid data in Machine ID field. Machine ID must be an integer.");
                    error.showAndWait();
                    return;
                }
                Inventory.updatePart(Inventory.getAllParts().indexOf(part),new InHouse(id, name, price, stock, min, max, machineId));
            } else {
                String companyName = machineIdOrCompanyNameField.getText();
                if (companyName.isEmpty()){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Invalid Data");
                    error.setContentText("Invalid data in Company Name field. Company Name cannot be blank.");
                    error.showAndWait();
                    return;
                }
                Inventory.updatePart(Inventory.getAllParts().indexOf(part), new Outsourced(id, name, price, stock, min, max, companyName));
            }
            changeScene(event, "/View/MainForm.fxml");
        }
    }
}

