package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class holds attributes for products and parts.
 * <p>RUNTIME ERROR</p>
 * <p>The error I occurred was that I found it difficult to implement search methods for part and product
 * names with only the given observable list. To correct this, I added a second set of observable lists
 * to filter results. </p>*/
public class Inventory {

    //Private Fields
     private static ObservableList<Part> allParts = FXCollections.observableArrayList();
     private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
     private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    // Parts Methods
    /** @return the list of parts */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** @return the list of filtered parts */
    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }
    /** @param partName part name to search */
    public static ObservableList<Part> lookupPart(String partName) {
            // This makes sure data ia not repopulated.
            if (!(Inventory.getAllFilteredParts().isEmpty()))
                Inventory.getAllFilteredParts().clear();

            for (Part object : Inventory.getAllParts())
            {
                if (object.getName().contains(partName))
                    Inventory.getAllFilteredParts().add(object);
            }
            // This lets all data populate if not found
            if (Inventory.getAllFilteredParts().isEmpty())
                return Inventory.getAllParts();
            else
                return Inventory.getAllFilteredParts();
        }
    /** @param partId part ID to search */
    public static Part lookupPart(int partId) {
        // This makes sure data ia not repopulated.
        if (!(Inventory.getAllFilteredParts().isEmpty()))
            Inventory.getAllFilteredParts().clear();

        for (Part object : Inventory.getAllParts()) {
            if (object.getId() == partId)
                return object;
        }
            return null;
    }
    /** @param newPart part to add to list of part */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /** @param index ID to search
     * @param selectedPart part object to set */
    public static void updatePart(int index, Part selectedPart) {

        int id = -1;
        for (Part object : Inventory.getAllParts()) {
            id++;

            if (object.getId() == index) {
                Inventory.getAllParts().set(id, selectedPart);
            }
        }
    }
    /** @param selectedPart part to delete */
    public static boolean deletePart(int selectedPart) {

        for (Part object : getAllParts()) {
            if (object.getId() == selectedPart)
                return Inventory.getAllParts().remove(object);
        }
        return false;
    }

    // Product Methods
    /** @return the list of products */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /** @return the list of filtered products */
    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }
    /** @param productName product name to search */
    public static ObservableList<Product> lookupProduct(String productName) {
        // This makes sure data ia not repopulated.
            if (!(Inventory.getAllFilteredProducts().isEmpty()))
                Inventory.getAllFilteredProducts().clear();

            for (Product object : Inventory.getAllProducts())
            {
                if (object.getName().contains(productName))
                    Inventory.getAllFilteredProducts().add(object);
            }
            // This lets all data populate if not found
            if (Inventory.getAllFilteredProducts().isEmpty())
                return Inventory.getAllProducts();
            else
                return Inventory.getAllFilteredProducts();
}
    /** @param newProduct product to add to list of products */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /** @param productId product ID to search */
    public static Product lookupProduct(int productId) {
        // This makes sure data ia not repopulated.
        if (!(Inventory.getAllFilteredProducts().isEmpty()))
            Inventory.getAllFilteredProducts().clear();

        for (Product object : Inventory.getAllProducts()) {
            if (object.getId() == productId)
                return object;
        }
        return null;
    }
    /** @param index ID to search
     * @param selectedProduct product object to set */
    public static void updateProduct(int index, Product selectedProduct) {

        int id = -1;
        for (Product object : Inventory.getAllProducts()) {
            id++;

            if (object.getId() == index) {
                Inventory.getAllProducts().set(id, selectedProduct);
            }
        }
    }
    /** @param selectedProduct product to delete */
    public static boolean deleteProduct(int selectedProduct) {
        for (Product object : getAllProducts()) {
            if (object.getId() == selectedProduct)
                return Inventory.getAllProducts().remove(object);
        }
        return false;
    }
}
