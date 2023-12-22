/**
 * @author Charan Williams
 * */
package Controller;

import Model.Inventory.Inventory;
import Model.Part.Part;
import Model.Product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddProductFormController {
    /**The stage of the application*/
    Stage stage;
    /**The scene of the application*/
    Parent scene;
    /**A list of filtered parts for the search method*/
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    /**A list of selected parts that are associated with the product when the product is saved*/
    private ObservableList<Part> selectedAssociatedParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableView<Part> choosePartTable;

    @FXML
    private TextField invField;

    @FXML
    private TextField maxField;

    @FXML
    private TextField minField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TextField priceCostField;

    @FXML
    private TableColumn<Part, Integer> associatedInventoryLevelColumn;

    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;

    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;

    @FXML
    private TableColumn<Part, Double> associatedPriceOrCostPerUnitColumn;

    @FXML
    private TableColumn<Part, Integer> chooseInventoryLevelColumn;

    @FXML
    private TableColumn<Part, Integer> choosePartIdColumn;

    @FXML
    private TableColumn<Part, String> choosePartNameColumn;

    @FXML
    private TableColumn<Part, Double> choosePriceOrCostPerUnitColumn;


    /**Gets window from a button and casts it to a stage, and then loads a scene from the provided path.
     * @param event the event
     * @param path the path of the scene to be loaded
     * */    void changeScene(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Initializes the add product form controller and loads data into the choose part and associated part tableviews*/
    public void initialize() {
        //Load data into part tableview
        choosePartTable.setItems(Inventory.getAllParts());
        choosePartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        choosePartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        chooseInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        choosePriceOrCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        associatedPartsTable.setItems(selectedAssociatedParts);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPriceOrCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
    }

    /**Searches inventory by part id or name, and selects a part in the choose part table if found by ID, or filters the choose part table if matches are found by name. Search by name is not case sensitive, and returns partial matches.
     * @param searchText the ID or name to search for
     * */
    public void searchParts(String searchText) {
        try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchText));
            if (part != null) {
                choosePartTable.getSelectionModel().select(part);
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("No parts found.");
                warning.showAndWait();
                choosePartTable.setItems(Inventory.getAllParts());
            }
        } catch (NumberFormatException e) {
            if ((!(filteredParts.isEmpty()))) {
                filteredParts.clear();
            }
            for (Part part : Inventory.getAllParts()) {
                if ((part.getName().toUpperCase().contains(searchText.toUpperCase()))) {
                    filteredParts.add(part);
                }
            }
            choosePartTable.setItems(filteredParts);
            if (filteredParts.isEmpty()) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("No parts found.");
                warning.showAndWait();
                choosePartTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**Adds a part selected in the choose part table to the selected associated parts table when the add button is clicked
     * @param event the event triggered when the add button is clicked
     * */
    @FXML
    void addButtonClicked(ActionEvent event) throws IOException {
        selectedAssociatedParts.add(choosePartTable.getSelectionModel().getSelectedItem());
    }

    /**Returns to the main form without saving any changes when the cancel button is clicked
     * @param event the event triggered when the cancel button is clicked
     * */
    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {

        changeScene(event, "/View/MainForm.fxml");
    }

    /**Searches the choose parts table by ID or name whenever a key is typed in the part search bar
     * @param event the event triggered when a key is typed in the part search bar
     * */
    @FXML
    void partSearchBarKeyTyped(KeyEvent event) {
        searchParts(partSearchBar.getText());
    }

    /**Removes a selected part from the selected associated parts table when the remove button is clicked
     * @param event the event triggered when the remove button is clicked
     * */
    @FXML
    void removeAssociatedPartButtonClicked(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Confirmation");
        confirmation.setContentText("Are you sure you want to remove this part from this product?");
        Optional<ButtonType> button = confirmation.showAndWait();
        if (button.get() == ButtonType.OK) {
            selectedAssociatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**Creates a new product in inventory when the save button is clicked. Checks the data types of the price, stock, min, and max fields, and checks if name is blank. Also checks that min is less than max, and that stock is in-between min and max.
     * @param event the event triggered when the save button is clicked
     * */
    @FXML
    void saveButtonClicked(ActionEvent event) throws IOException {
        //Get the last part in the all products observable list, then get the id of that product and adds 1 to generate a new unique part ID.
        int id = (Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId()) + 1;
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
        } else {
            Inventory.getAllProducts().add(new Product(id, name, price, stock, min, max));
            Inventory.lookupProduct(id).setAssociatedParts(selectedAssociatedParts);
            changeScene(event, "/View/MainForm.fxml");
        }
    }
}

