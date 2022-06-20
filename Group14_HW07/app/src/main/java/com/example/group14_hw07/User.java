package com.example.group14_hw07;

public class User {

    String name;
    String email;
    String id;
    String shoppingListDocument;
    boolean isAdded;

    public User(String name, String email, String id, String shoppingListDocument, boolean isAdded) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.shoppingListDocument = shoppingListDocument;
        this.isAdded = isAdded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShoppingListDocument() {
        return shoppingListDocument;
    }

    public void setShoppingListDocument(String shoppingListDocument) {
        this.shoppingListDocument = shoppingListDocument;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
