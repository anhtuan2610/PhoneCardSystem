/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import entity.Product;

/**
 *
 * @author phamtung
 */
public class Storage {

    private int id, status, isDelete;
    private String seri, code, createdBy, updatedBy, deletedBy;
    private long expiryDate, createdAt, deletedAt, updatedAt;
    private Product product;
    
    public Storage() {
    }

    public Storage(int id, int status, int isDelete, Product product, String seri, String code, String createdBy, String updatedBy, String deletedBy, long expiryDate, long createdAt, long deletedAt, long updatedAt) {
        this.id = id;
        this.status = status;
        this.isDelete = isDelete;
        this.product = product;
        this.seri = seri;
        this.code = code;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
        this.expiryDate = expiryDate;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

   

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public String getSeri() {
        return seri;
    }

    public String getCode() {
        return code;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
