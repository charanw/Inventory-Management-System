/**
 * @author Charan Williams 
 * */

package Model.Main;

import Model.Inventory.Inventory;
import Model.Part.Part;
import Model.Part.InHouse;
import Model.Part.Outsourced;
import Model.Product.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Starts the application and sets the primary stage title and window size
     * @param primaryStage the primary stage
     * */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        primaryStage.setTitle("C482 Inventory Management App");
        primaryStage.setScene(new Scene(root, 880, 400));
        primaryStage.show();
    }

    /**
     * The main method of the application. Loads the application with data and launches the application.
     * The JavaDoc files for this application are located in the JavaDoc folder in the InventoryAppFX main project folder.
     * FUTURE ENHANCEMENT An enhancement that I would make to this application would be to extend the functionality of the program by sending an email notification to a designated address for the business's purchaser whenever the stock levels are close to the part's or product's minimum amount. I would also improve the user interface of this application by using custom branding and colors for the business, as well as making the user interface resize responsively for large (eg. tv or desktop) or small (eg. phone or tablet) screens. Currently, if a business's inventory had a lot of parts or products, it could be cumbersome to view them all with the small part and product tableviews.
     * @param args stores Java command line arguments
     * */
    public static void main(String[] args) {
        //Create demonstration parts and products, then add them to the allParts and allProducts observable lists
        Part part1 = new InHouse(1,"Vanilla Ice Cream", 1.50, 3, 1, 4, 1 );
        Part part2 = new InHouse(2,"Waffle Cone", .50, 12, 4, 16, 2 );
        Part part3 = new InHouse(3,"Chocolate Syrup", .75, 2, 1, 3, 3 );
        Part part4 = new Outsourced(4,"Sprinkles", .25, 20, 10, 30, "Sprinkles-R-Us" );
        Part part5 = new InHouse(5,"Chocolate Ice Cream", 1.50, 4, 1, 4, 1 );
        Part part6 = new Outsourced(6,"Banana", .50, 6, 2, 8, "Bananas Inc." );

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);

        Product product1 = new Product(1, "Banana Split", 9.99, 3, 1, 4);
        Product product2 = new Product(2, "Vanilla Sundae", 5.99, 2, 1, 3);
        Product product3 = new Product(3, "Chocolate Cone", 4.99, 4, 1, 5);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part6);
        product1.addAssociatedPart(part4);

        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part3);

        product3.addAssociatedPart(part5);
        product3.addAssociatedPart(part2);

        //Launch the application
        launch(args);
    }
}
