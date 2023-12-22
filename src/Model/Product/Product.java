/**
 * @author Charan Williams
 * */

package Model.Product;

import Model.Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Declare class Model.Product
public class Product {
    /**A list of parts associated with the product*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**The product's ID*/
    private int id;
    /**The product's name*/
    private String name;
    /**The product's price*/
    private double price;
    /**The product's stock amount*/
    private int stock;
    /**The product's minimum amount*/
    private int min;
    /**The product's maximum amount*/
    private int max;
//Setters
    /**
     * Sets the associated parts to a product
     * @param associatedParts a list of associated parts for a product
     * */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    /**
     * Sets the product id
     * @param id the product id
     * */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Sets the product name
     * @param name the product name
     * */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the product price
     * @param price the product price
     * */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Sets the product stock amount
     * @param stock the stock amount of a product
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Sets the product minimum
     * @param min the minimum amount of a product
     * */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * Sets the product maximum
     * @param max the maximum amount of a product
     * */
    public void setMax(int max) {
        this.max = max;
    }

//Getters
    /**
     * Gets the product ID
     * @return id the product's ID
     * */
    public int getId() {
        return id;
    }
    /**
     * Gets the product name
     * @return name the product's name
     * */
    public String getName() {
        return name;
    }
    /**
     * Gets the product price
     * @return price the product's price
     * */
    public double getPrice() {
        return price;
    }
    /**
     * Gets the product stock
     * @return stock the product's stock amount
     * */
    public int getStock() {
        return stock;
    }
    /**
     * Gets the product min
     * @return min the product's minimum amount
     * */
    public int getMin() {
        return min;
    }
    /**
     * Gets the product ID
     * @return id the product's ID
     * */
    public int getMax() {
        return max;
    }
    /**
     * Gets the product's list of associated parts
     * @return associatedParts an observable list of the product's associated parts
     * */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
//Constructor
    /**
     * Creates a new product object
     * @param id the product's ID
     * @param name the product's name
     * @param price the product's price
     * @param stock the product's stock amount
     * @param min the product's minimum amount
     * @param max the product's maximum amount
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

//Methods
    /**
     * Adds a part to the product's list of associated parts
     * @part the part to be added to the list of associated parts
     * */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * Deletes a part from the product's list of associated parts
     * @part the part to be deleted from the list of associated parts
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

}
