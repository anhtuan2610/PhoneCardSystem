/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author phamtung
 */
public class Order {

    public enum OrderStatus {
        FAIL, SUCCESS, PROCESSING;
    }

    private int id, isDelete, quantity;
    private OrderStatus status;
    private String createdBy, deletedBy, updatedBy, productLog, storageLog;
    private int price, totalPrice;
    private long createdAt, updatedAt, deletedAt;
    private User user;
    private Product product;

    public Order() {
    }

    public Order(int id, int isDelete, int quantity, OrderStatus status, User user, Product product, String createdBy, String deletedBy, String updatedBy, String productLog, String storageLog, int price, int totalPrice, long createdAt, long updatedAt, long deletedAt) {
        this.id = id;
        this.isDelete = isDelete;
        this.quantity = quantity;
        this.status = status;
        this.user = user;
        this.product = product;
        this.createdBy = createdBy;
        this.deletedBy = deletedBy;
        this.updatedBy = updatedBy;
        this.productLog = productLog;
        this.storageLog = storageLog;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Order(int quantity, OrderStatus status, User user, String createdBy, String productLog, String storageLog, Product product, int price, int totalPrice, long createdAt) {
        this.quantity = quantity;
        this.status = status;
        this.user = user;
        this.createdBy = createdBy;
        this.productLog = productLog;
        this.storageLog = storageLog;
        this.product = product;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
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

    public String getProductLog() {
        return productLog;
    }

    public String getStorageLog() {
        return storageLog;
    }

    public Product getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setProductLog(String productLog) {
        this.productLog = productLog;
    }

    public void setStorageLog(String storageLog) {
        this.storageLog = storageLog;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public OrderStatus convertToOrderStatus(int value) {
        switch (value) {
            case 0:
                return OrderStatus.FAIL;
            case 1:
                return OrderStatus.SUCCESS;
            case 2:
                return OrderStatus.PROCESSING;
            default:
                throw new IllegalArgumentException("Giá trị không hợp lệ: " + value);
        }
    }
}
