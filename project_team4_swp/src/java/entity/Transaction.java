package entity;

/**
 *
 * @author phamtung
 */
public class Transaction {

    public enum TransactionStatus {
        FAIL, SUCCESS, PROCESSING;
    }

    private int id, isDelete;
    private String createdBy, deletedBy, updatedBy, description, type;
    private long createdAt, updatedAt, deletedAt;
    private int money;
    private User user;
    private Order order;
    private TransactionStatus status;

    public Transaction() {
    }

    public Transaction(int id, int isDelete, User user, Order order, String createdBy, String deletedBy, String updatedBy, String description, String type, long createdAt, long updatedAt, long deletedAt, int money, TransactionStatus status) {
        this.id = id;
        this.isDelete = isDelete;
        this.user = user;
        this.order = order;
        this.createdBy = createdBy;
        this.deletedBy = deletedBy;
        this.updatedBy = updatedBy;
        this.description = description;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.money = money;
        this.status = status;
    }

    public Transaction(User user, Order order, String createdBy, String description, String type, long createdAt, int money) {
        this.user = user;
        this.order = order;
        this.createdBy = createdBy;
        this.description = description;
        this.type = type;
        this.createdAt = createdAt;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public User getUser() {
        return user;
    }

    public Order getOrder() {
        return order;
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

    public String getType() {
        return type;
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

    public int getMoney() {
        return money;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public void setType(String type) {
        this.type = type;
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

    public void setMoney(int money) {
        this.money = money;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionStatus convertToTransactionStatus(int value) {
        switch (value) {
            case 0:
                return TransactionStatus.FAIL;
            case 1:
                return TransactionStatus.SUCCESS;
            case 2:
                return TransactionStatus.PROCESSING;
            default:
                throw new IllegalArgumentException("Giá trị không hợp lệ: " + value);
        }
    }
}
