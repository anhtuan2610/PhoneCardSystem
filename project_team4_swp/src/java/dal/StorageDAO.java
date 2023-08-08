/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dbcontext.DBContext;
import entity.Product;
import entity.Storage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phamtung
 */
public class StorageDAO extends DBContext {

    public Storage getStorageById(int Id) {
        Storage s = new Storage();
        String sql = "SELECT  * "
                + " FROM `storage` WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ProductDAO pDAO = new ProductDAO();
                Product p = pDAO.getProductById(rs.getInt("product"));
                
                s = new Storage(
                        rs.getInt("id"), rs.getInt("status"),
                        rs.getInt("isDelete"), p,
                        rs.getString("seri"), rs.getString("code"),
                        rs.getString("createdBy"), rs.getString("updatedBy"),
                        rs.getString("deletedBy"), rs.getLong("expiryDate"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getLong("updatedAt")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            s = null;
        }

        return s;
    }

   
    
    public List<Storage> getStorageByIdAndQuantity(int Id, int quantity) {
        List<Storage> listStorage = new ArrayList<>();

        String sql = "SELECT * FROM `storage`"
                + " WHERE product = ? AND status = 1 LIMIT ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Id);
            st.setInt(2, quantity);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductDAO pDAO = new ProductDAO();
                Product p = pDAO.getProductById(rs.getInt("product"));

                Storage s = new Storage(
                        rs.getInt("id"), rs.getInt("status"),
                        rs.getInt("isDelete"), p,
                        rs.getString("seri"), rs.getString("code"),
                        rs.getString("createdBy"), rs.getString("updatedBy"),
                        rs.getString("deletedBy"), rs.getLong("expiryDate"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getLong("updatedAt")
                );
                listStorage.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
            listStorage = null;
        }

        return listStorage;
    }

    public boolean updateListStorage(String sql) {

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public int countStorage(String sql) {

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

    public ArrayList<String> storageDuplicate(String sql) {
        ArrayList<String> resultList = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String value = rs.getString(1);
                resultList.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public ArrayList<String> getListProductIdByPriceAndCategory(String sql) {
        ArrayList<String> resultList = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String value = rs.getString(1);
                resultList.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public boolean runSQL(String sql) {
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int result = st.executeUpdate(); // Sử dụng executeUpdate() thay cho executeQuery()

            if (result > 0) {
                return true; // Trả về true nếu thực thi thành công
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
     public boolean runSQLExecuteQuery(String sql) {

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();// Câu lệnh trả về data
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phân trang
    public List<Storage> getListStorageFilter(String sql) {
        List<Storage> listStorage = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductDAO pDAO = new ProductDAO();
                Product p = pDAO.getProductById(rs.getInt("product"));

                Storage s = new Storage(
                        rs.getInt("id"), rs.getInt("status"),
                        rs.getInt("isDelete"), p,
                        rs.getString("seri"), rs.getString("code"),
                        rs.getString("createdBy"), rs.getString("updatedBy"),
                        rs.getString("deletedBy"), rs.getLong("expiryDate"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getLong("updatedAt")
                );
                listStorage.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
            listStorage = null;
        }

        return listStorage;
    }

    public static void main(String[] args) {
        StorageDAO stDAO = new StorageDAO();

        List<Storage> list = new ArrayList<>();

        list = stDAO.getStorageByIdAndQuantity(1, 5);

        System.out.println(list.get(4).getCode());
    }
}
