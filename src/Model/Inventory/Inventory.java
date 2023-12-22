/**
 * @author Charan Williams
 * */
package Model.Inventory;

import Model.Part.Part;
import Model.Product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Declare class Model.Product.Inventory
public class Inventory {
    /**A list of all parts in inventory*/
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**A list of all products in inventory*/
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

//Getters
    /**
     * Gets a list of all parts from inventory
     * @return Returns the list of all parts from inventory
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Gets a list of all products from inventory
     * @return Returns the list of all products from inventory
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

//Methods
    /**
     * Adds a new part to inventory
     * @param newPart the new part to be added to inventory
     * */
    public static void addPart(Part newPart){
        allParts.add(newPart);

    }
    /**
     * Adds a new product to inventory
     * @param newProduct the new product to be added to inventory
     * */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /**
     * Looks up a part in inventory by the part's ID
     * @param partId the part ID
     * @return Returns the part or returns null if no part is found
     * */
 public static Part lookupPart(int partId){
     for (Part part : allParts){
         if (part.getId() == partId){
             return part;
         }
    }
     return null;
 }
    /**
     * Looks up a product in inventory by the product's ID
     * @param productId the product's ID
     * @return Returns the product or returns null if no product is found
     * */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
    /**
     * Looks up a part in inventory by the part's name
     * @param name the part's name
     * @return Returns the part or returns null if no part is found
     * */
    public static Part lookupPart(String name) {
        for (Part part : allParts) {
            if (part.getName().contains(name)) {
                return part;
            }
        }
        return null;
    }
    /**
     * Looks up a product in inventory by the product's name
     * @param name the product's name
     * @return Returns the product or returns null if no product is found
     * */
    public static Product lookupProduct(String name) {
        for (Product product : allProducts) {
            if (product.getName().contains(name)) {
                return product;
            }
        }
        return null;
    }
    /**
     * Replaces a part in inventory by the part's index in the list of all parts in inventory
     * @param index the index of the part to be replaced in the list of all parts in inventory
     * @param selectedPart the part to be inserted
     * */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * Replaces a product in inventory by the product's index in the list of all parts in inventory
     * @param index the index of the part to be replaced in the list of all parts in inventory
     * @param newProduct the product to be inserted
     * */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    /**
     * Removes a part from the list of all parts in inventory
     * @param selectedPart the part to be removed
     * @return Returns true when the part is removed
     * */
    public static boolean deletePart(Part selectedPart){return allParts.remove(selectedPart);}
    /**
     * Removes a product from the list of all parts in inventory
     * @param selectedProduct the product to be removed
     * @return Returns true when the product is removed
     * */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
}
