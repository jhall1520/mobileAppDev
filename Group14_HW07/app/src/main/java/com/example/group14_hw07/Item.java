package com.example.group14_hw07;

public class Item {

    String name;
    String cost;
    String status;
    String userId;
    String documentId;
    String shoppingListDocumentId;

    public Item(String name, String cost, String status, String userId, String documentId, String shoppingListDocumentId) {
        this.name = name;
        this.cost = cost;
        this.status = status;
        this.userId = userId;
        this.documentId = documentId;
        this.shoppingListDocumentId = shoppingListDocumentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getShoppingListDocumentId() {
        return shoppingListDocumentId;
    }

    public void setShoppingListDocumentId(String shoppingListDocumentId) {
        this.shoppingListDocumentId = shoppingListDocumentId;
    }
}
