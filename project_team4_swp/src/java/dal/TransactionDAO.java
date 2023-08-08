/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dbcontext.DBContext;
import entity.Order;
import entity.Transaction;
import entity.Transaction.TransactionStatus;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.GetCurrentTime;

/**
 *
 * @author phamtung
 */
public class TransactionDAO extends DBContext {

    public Transaction getTransactionById(int Id) {
        Transaction tran = new Transaction();
        String sql = "SELECT * FROM `transaction` WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                tran.setId(rs.getInt("id"));
                tran.setCreatedAt(rs.getLong("createdAt"));
                tran.setCreatedBy(rs.getString("createdBy"));
                tran.setDeletedBy(rs.getString("deletedBy"));
                tran.setUpdatedBy(rs.getString("updatedBy"));
                tran.setUpdatedAt(rs.getLong("updatedAt"));
                tran.setDeletedAt(rs.getLong("deletedAt"));
                tran.setIsDelete(rs.getInt("isDelete"));

                UserDAO uDAO = new UserDAO();
                User u = uDAO.getUserById(rs.getInt("user"));
                tran.setUser(u);

                OrderDAO oDAO = new OrderDAO();
                Order o = oDAO.getOrderById(rs.getInt("order"));
                tran.setOrder(o);
                tran.setDescription(rs.getString("description"));

                tran.setType(rs.getString("type"));
                tran.setMoney(rs.getInt("money"));

                int statusInt = rs.getInt("status");
                TransactionStatus status = tran.convertToTransactionStatus(statusInt);

                tran.setStatus(status);
            }
        } catch (SQLException e) {
            System.out.println(e);
            tran = null;
        }

        return tran;
    }

//  Phân trang và filter
    public double countTransaction(String sql) {

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

    public List<Transaction> getListTransactionByUserId(String sql) {
        List<Transaction> list = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Transaction tran = new Transaction();

                tran.setId(rs.getInt("id"));
                tran.setCreatedAt(rs.getLong("createdAt"));
                tran.setCreatedBy(rs.getString("createdBy"));
                tran.setDeletedBy(rs.getString("deletedBy"));
                tran.setUpdatedBy(rs.getString("updatedBy"));
                tran.setUpdatedAt(rs.getLong("updatedAt"));
                tran.setDeletedAt(rs.getLong("deletedAt"));
                tran.setIsDelete(rs.getInt("isDelete"));

                UserDAO uDAO = new UserDAO();
                User u = uDAO.getUserById(rs.getInt("user"));
                tran.setUser(u);

                OrderDAO oDAO = new OrderDAO();
                Order o = oDAO.getOrderById(rs.getInt("order"));
                tran.setOrder(o);
                tran.setDescription(rs.getString("description"));

                tran.setType(rs.getString("type"));
                tran.setMoney(rs.getInt("money"));

                list.add(tran);
            }
        } catch (SQLException e) {
            System.out.println(e);
            list = null;
        }

        return list;
    }

    public void insertTransaction(Transaction tran) {
        String sql = "INSERT INTO `transaction`(`createdAt`, `createdBy`,"
                + " `user`, `order`, `description`, `type`, `money`)"
                + " VALUES ( ?,?,?,?,?,?,? );";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, tran.getCreatedAt());
            st.setString(2, tran.getCreatedBy());
            st.setInt(3, tran.getUser().getId());
            st.setInt(4, tran.getOrder().getId());
            st.setString(5, tran.getDescription());
            st.setString(6, tran.getType());
            st.setDouble(7, tran.getMoney());

            st.executeUpdate();

//            System.out.println("Insert transaction success!!");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Insert transaction fail!!");

        }
    }

}
