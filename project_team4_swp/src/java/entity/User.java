/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author phamtung
 */
public class User {

//    `id`, `userName`,
//    `passWord`, 
//   `role`, 
//    `updatedBy`, `updatedAt`, 
//    `createdBy`, `createdAt`
    
    private int id, role, isDelete;
    private String name, phone, email, account,
            password, createdBy, updatedBy, deletedBy;
    private long updatedAt, createdAt, deletedAt;
    private int balance;
    private String code, activityLog;
    

    public User() {
    }

    public User(int id, int role, int isDelete, String name, String phone, String email, String account, String password, String createdBy, String updatedBy, String deletedBy, long updatedAt, long createdAt, long deletedAt, int balance, String code, String activityLog) {
        this.id = id;
        this.role = role;
        this.isDelete = isDelete;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.account = account;
        this.password = password;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.balance = balance;
        this.code = code;
        this.activityLog = activityLog;
    }

    public User(int id, int role, int isDelete, String name, String phone, String email, String account, String password, String createdBy, String updatedBy, String deletedBy, long updatedAt, long createdAt, long deletedAt, int balance, String activityLog) {
        this.id = id;
        this.role = role;
        this.isDelete = isDelete;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.account = account;
        this.password = password;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.balance = balance;
        this.activityLog = activityLog;
    }
    
    public User(int id, int role, int isDeleted, String name,
            String phone, String email, String account,
            String password, String createdBy, String updatedBy,
            String deletedBy, long updatedAt, long createdAt,
            long deletedAt, int balance) {
        this.id = id;
        this.role = role;
        this.isDelete = isDelete;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.account = account;
        this.password = password;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.balance = balance;
    }

  

    public User(String email, String account) {
        this.email = email;
        this.account = account;
    }
    
    public User(int id, String name, String phone, String email,
            String updatedBy, long updatedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
    
     public User(int id, String name, String phone, String email,
            String updatedBy, long updatedAt, String activityLog) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.activityLog = activityLog;
    }
     
     
    public User(int id, int role, String name, String phone, String email, String account) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.account = account;
    }

    public User(String password, String updatedBy, long updatedAt, int id, String activityLog) {
        this.password = password;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.id = id;
        this.activityLog = activityLog;
    }

    public User(String name, String email, String code) {
        this.name = name;
        this.email = email;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public int getRole() {
        return role;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
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

    public long getUpdatedAt() {
        return updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public int getBalance() {
        return balance;
    }

    public String getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCode(String code) {
        this.code = code;
    }
  public String getActivityLog() {
        return activityLog;
    }

    public void setActivityLog(String activityLog) {
        this.activityLog = activityLog;
    }
}
