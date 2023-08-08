package dal;

import dbcontext.DBContext;
import entity.Order;
import entity.Order.OrderStatus;
import entity.Product;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phamtung
 */
public class OrderDAO extends DBContext {

    public Order getOrderById(int Id) {
        Order o = new Order();
        String sql = "SELECT * FROM `order` WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                o.setId(rs.getInt("id"));
                o.setCreatedAt(rs.getLong("createdAt"));
                o.setCreatedBy(rs.getString("createdBy"));
                o.setDeletedBy(rs.getString("deletedBy"));
                o.setUpdatedBy(rs.getString("updatedBy"));
                o.setUpdatedAt(rs.getLong("updatedAt"));
                o.setDeletedAt(rs.getLong("deletedAt"));
                o.setIsDelete(rs.getInt("isDelete"));
                o.setPrice(rs.getInt("price"));
                o.setTotalPrice(rs.getInt("totalPrice"));
                o.setQuantity(rs.getInt("quantity"));
                ProductDAO pDAO = new ProductDAO();
                Product p = pDAO.getProductById(rs.getInt("product"));
                o.setProduct(p);

                int statusString = rs.getInt("status");
                OrderStatus status = o.convertToOrderStatus(statusString);
                
//                System.out.println("Status Int: " + statusString);
                
                o.setStatus(status);
                UserDAO uDAO = new UserDAO();
                User u = uDAO.getUserById(rs.getInt("user"));
                o.setUser(u);
                o.setProductLog(rs.getString("productLog"));
                o.setStorageLog(rs.getString("storageLog"));

            }
        } catch (SQLException e) {
            System.out.println(e);
            o = null;
        }

        return o;
    }

    public int insertOrder(Order o) {
        String sql = "INSERT INTO `order`( `createdAt`, `createdBy`, `price`,"
                + " `totalPrice`, `quantity`, `product`, `status`, `user`,"
                + " `productLog`, `storageLog`) "
                + "VALUES ( ?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, o.getCreatedAt());
            st.setString(2, o.getCreatedBy());
            st.setDouble(3, o.getPrice());
            st.setDouble(4, o.getTotalPrice());
            st.setInt(5, o.getQuantity());
            st.setInt(6, o.getProduct().getId());

            OrderStatus statusEnum = o.getStatus();
            int status = statusEnum.ordinal();

            st.setInt(7, status);
            st.setInt(8, o.getUser().getId());
            st.setString(9, o.getProductLog());
            st.setString(10, o.getStorageLog());

            st.executeUpdate();

            ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() AS id");
            if (rs.next()) {
                int orderId = rs.getInt(1);
                return orderId;
            }

            System.out.println("Insert Order success!!");
        } catch (Exception e) {
            System.out.println(e);

        }
        return 0;
    }

//    Phân trang
    public double countOrder(String sql) {

        try {

            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Order> getListOrderByUserId(String sql) {
        List<Order> listOrder = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order o = new Order();

                o.setId(rs.getInt("id"));
                o.setCreatedAt(rs.getLong("createdAt"));
                o.setCreatedBy(rs.getString("createdBy"));
                o.setDeletedBy(rs.getString("deletedBy"));
                o.setUpdatedBy(rs.getString("updatedBy"));
                o.setUpdatedAt(rs.getLong("updatedAt"));
                o.setDeletedAt(rs.getLong("deletedAt"));
                o.setIsDelete(rs.getInt("isDelete"));
                o.setPrice(rs.getInt("price"));
                o.setTotalPrice(rs.getInt("totalPrice"));
                o.setQuantity(rs.getInt("quantity"));

                ProductDAO pDAO = new ProductDAO();
                Product p = pDAO.getProductById(rs.getInt("product"));
//                Product p = new Product();
                o.setProduct(p);

                int statusString = rs.getInt("status");
                OrderStatus status = o.convertToOrderStatus(statusString);

                o.setStatus(status);

                UserDAO uDAO = new UserDAO();
                User u = uDAO.getUserById(rs.getInt("user"));
//                User u = new User();

                o.setUser(u);

                o.setProductLog(rs.getString("productLog"));
                o.setStorageLog(rs.getString("storageLog"));

                listOrder.add(o);
            }
        } catch (SQLException e) {
            System.out.println(e);
            listOrder = null;
        }

        return listOrder;
    }

    public boolean updateStatusByOrderId(int status, int id , String storageLog) {
        String sql = "UPDATE `order` SET `STATUS` = ?, `storageLog` = ? WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, status);
            st.setString(2, storageLog);
            st.setInt(3, id);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
