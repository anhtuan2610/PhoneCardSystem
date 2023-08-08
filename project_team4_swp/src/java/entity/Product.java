/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author phamtung
 */
public class Product {                            		
     private int id,isDelete,isShow,quantity;
     private String name,createdBy,deletedBy,updatedBy,description,image,category;
     private long updatedAt,createdAt,deletedAt;
     private int price;
     
    public Product() {
    }

    public Product(int id, int isDelete, int isShow, int quantity, String name, String createdBy, String deletedBy, String updatedBy, String description, String image, String category, long updatedAt, long createdAt, long deletedAt, int price) {
        this.id = id;
        this.isDelete = isDelete;
        this.isShow = isShow;
        this.quantity = quantity;
        this.name = name;
        this.createdBy = createdBy;
        this.deletedBy = deletedBy;
        this.updatedBy = updatedBy;
        this.description = description;
        this.image = image;
        this.category = category;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.price = price;
    }

    public Product(int id, int quantity, String name, String createdBy, String updatedBy, String description, String image, String category, long createdAt, int price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.description = description;
        this.image = image;
        this.category = category;
        this.createdAt = createdAt;
        this.price = price;
    }

    
    public int getId() {
        return id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public int getIsShow() {
        return isShow;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setPrice(int price) {
        this.price = price;
    }
     
     
}
