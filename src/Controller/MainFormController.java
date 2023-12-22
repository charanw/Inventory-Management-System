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

public class MainFormController {
    /**The stage of the application*/
    private Stage stage;
    /**The scene of the application*/
    private Parent scene;
    /**A list of filtered parts for the search method*/
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    /**A list of filtered products for the search method*/
    private ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    @FXML
    private TextField partSearchBar;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TextField productSearchBar;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Double> partPriceOrCostPerUnitColumn;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Double> productPriceOrCostPerUnitColumn;

    /**Gets window from a button and casts it to a stage, and then loads a scene from the provided path.
     * @param event the event
     * @param path the path of the scene to be loaded
     * */
    void changeScene(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Initializes the MainForm controller and loads data into the part and product tableviews*/
    public void initialize() {
        //Load data into part tableview
        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceOrCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //Load data into product tableview
        productsTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceOrCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

    }

    /**Searches inventory by part id or name, and selects a part in the choose part table if found by ID, or filters the part table if matches are found by name. Search by name is not case sensitive, and returns partial matches.
     * @param searchText the ID or name to search for
     * */
    public void searchParts(String searchText) {
        try {
            Part part = Inventory.lookupPart(Integer.parseInt(searchText));
            if (part != null) {
                partsTable.getSelectionModel().select(part);
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("No parts found.");
                warning.showAndWait();
                partsTable.setItems(Inventory.getAllParts());
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
            partsTable.setItems(filteredParts);
            if (filteredParts.isEmpty()) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("No parts found.");
                warning.showAndWait();
                partsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**Searches inventory by product id or name, and selects a product in the product table if found by ID, or filters the product table if matches are found by name. Search by name is not case sensitive, and returns partial matches.
     * @param searchText the ID or name to search for
     * */
    public void searchProducts(String searchText) {
        try {
            Product product = Inventory.lookupProduct(Integer.parseInt(searchText));
            if (product != null) {
                productsTable.getSelectionModel().select(product);
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("No products found.");
                warning.showAndWait();
                productsTable.setItems(Inventory.getAllProducts());
            }
        } catch (NumberFormatException e) {
            if ((!(filteredProducts.isEmpty()))) {
                filteredProducts.clear();
            }
            for (Product product : Inventory.getAllProducts()) {
                if ((product.getName().toUpperCase().contains(searchText.toUpperCase()))) {
                    filteredProducts.add(product);
                }
            }
            productsTable.setItems(filteredProducts);
            if (filteredProducts.isEmpty()) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("No products found.");
                warning.showAndWait();
                productsTable.setItems(Inventory.getAllProducts());
            }
        }
    }

    /**Opens the add part form when the add part button is clicked
     * @param event the event triggered when the add button is clicked
     * */
    @FXML
    void addPartButtonClicked(ActionEvent event) throws IOException {
        changeScene(event, "/View/AddPartForm.fxml");
    }

    /**Opens the add product form when the add product button is clicked
     * @param event the event triggered when the add button is clicked
     * */
    @FXML
    void addProductButtonClicked(ActionEvent event) throws IOException{
    changeScene(event, "/View/AddProductForm.fxml");
    }

    /**Removes a part from inventory when the delete part button is clicked. Checks to see that a part is selected, and then displays a confirmation dialog.
     * @param event the event triggered when the delete part button is clicked
     * */
    @FXML
    void deletePartButtonClicked(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setContentText("No part selected. Please select a part and try again.");
            warning.showAndWait();
            return;
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete Confirmation");
            confirmation.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> button = confirmation.showAndWait();
            if (button.get() == ButtonType.OK) {
                Inventory.deletePart(part);
                partsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**Removes a product from inventory when the delete product button is clicked. Checks to see that a product is selected, and then displays a confirmation dialog.
     * @param event the event triggered when the delete product button is clicked
     * */
    @FXML
    void deleteProductButtonClicked(ActionEvent event) {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setContentText("No product selected. Please select a product and try again.");
            warning.showAndWait();
        } else {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Confirmation");
        confirmation.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> button = confirmation.showAndWait();
        if (button.get() == ButtonType.OK) {
                if (product.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(product);
                    productsTable.setItems(Inventory.getAllProducts());
                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setContentText("You can not delete a product that has associated parts. Please remove all parts from this product, then try again.");
                    error.showAndWait();
                }
            }
        }
    }

    /**Quits the application when the exit button is clicked
     * @param event the event triggered when the exit button is clicked
     * */
    @FXML
    void exitButtonClicked(ActionEvent event) throws IOException {
            System.exit(0);
    }

    /**Opens the modify part form for the selected part in the parts table and passes the part to the modify part form controller when the modify part button is clicked. Checks if a part is selected and displays an error dialog if a part is not selected.
     * @param event the event triggered when the modify part button is clicked
     * */
    @FXML
    void modifyPartButtonClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartFormController controller = loader.getController();
            controller.retrievePartData(partsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setContentText("No part selected. Please select a part and try again.");
            warning.showAndWait();
        }
    }
    /**Opens the modify product form for the selected product in the products table and passes the product to the modify part form controller when the modify product button is clicked. Checks if a product is selected and displays an error dialog if a product is not selected.
     * @param event the event triggered when the modify part button is clicked
     * */
    @FXML
    void modifyProductButtonClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductFormController controller = loader.getController();
            controller.retrieveProductData(productsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setContentText("No product selected. Please select a part and try again.");
            warning.showAndWait();
        }
    }
    /**Searches the parts table by ID or name whenever a key is typed in the part search bar
     * @param event the event triggered when a key is typed in the part search bar
     * */
    @FXML
    void partSearchBarKeyTyped(KeyEvent event) {
        searchParts(partSearchBar.getText());
    }
    /**Searches the product table by ID or name whenever a key is typed in the product search bar
     * @param event the event triggered when a key is typed in the product search bar
     * */
    @FXML
    void productSearchBarKeyTyped(KeyEvent event) {
    searchProducts(productSearchBar.getText());
    }
}

