package com.example.group14_hw07;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingList implements Serializable {

    String name;
    ArrayList<Item> items;
    String ownerId;
    ArrayList<String> userIds;
    String documentId;


    public ShoppingList(String name, ArrayList<Item> items, String ownerId, ArrayList<String> userIds, String documentId) {
        this.items = items;
        this.name = name;
        this.ownerId = ownerId;
        this.userIds = userIds;
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
