package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class holds attributes for products and associated parts.
 * <p>RUNTIME ERROR</p>
 * An error I occurred was not being able to delete the correct associated part. I corrected this
 * by getting the part ID which is the key and deleting the object with this ID. */
public class Product {

    // Private Fields
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id, stock, min, max;
    private String name;
    private double price;

    /** @param part adds part to associatedParts list */
    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }
    /** @param selectedAssociatedPart removes selectedAssociatedPart from
     * associatedParts list */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart) {

        for (Part object : getAllAssociatedParts()) {
            if (object.getId() == selectedAssociatedPart.getId())
                return associatedParts.remove(selectedAssociatedPart);
        }
        return false;
    }
    /** @return the list of associated part */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public Product(int id, int stock, int min, int max, String name, double price) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.price = price;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /** @param id the id to set */
    public void setId(int id) {
        this.id = id;
    }
    /** @return the stock */
    public int getStock() {
        return stock;
    }
    /** @param stock the stock to set */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** @return the min */
    public int getMin() {
        return min;
    }
    /** @param min the min to set */
    public void setMin(int min) {
        this.min = min;
    }
    /** @return the max */
    public int getMax() {
        return max;
    }
    /** @param max the max to set */
    public void setMax(int max) {
        this.max = max;
    }
    /** @return the name */
    public String getName() {
        return name;
    }
    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }
    /** @return the price */
    public double getPrice() {
        return price;
    }
    /** @param price the price to set */
    public void setPrice(double price) {
        this.price = price;
    }

}