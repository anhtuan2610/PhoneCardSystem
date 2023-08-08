/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dbcontext.DBContext;
import entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phamtung
 */
public class ProductDAO extends DBContext {

    public Product getProductById(int Id) {
        Product p = new Product();
        String sql = "SELECT  * "
                + " FROM `product` WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                p = new Product(rs.getInt("id"), rs.getInt("isDelete"),
                        rs.getInt("isShow"), rs.getInt("quantity"),
                        rs.getString("name"), rs.getString("createdBy"),
                        rs.getString("deletedBy"), rs.getString("updatedBy"),
                        rs.getString("description"), rs.getString("image"),
                        rs.getString("category"), rs.getLong("updatedAt"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getInt("price")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            p = null;
        }

        return p;
    }

    public boolean updateQuantityById(int id, int quantity) {
        String sql = "UPDATE `product` SET"
                + " `quantity`= ? WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, id);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateStatusNoneById(int id) {
        String sql = "UPDATE `product` SET `isShow`='0' WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM `product` WHERE isShow = 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int isDelete = rs.getInt(2);
                String createdBy = rs.getString(3);
                String deletedBy = rs.getString(4);
                String updatedBy = rs.getString(5);
                long updatedAt = rs.getLong(6);
                int price = rs.getInt(7);
                long createdAt = rs.getLong(8);
                long deletedAt = rs.getLong(9);
                String name = rs.getString(10);
                String description = rs.getString(11);
                String image = rs.getString(12);
                int isShow = rs.getInt(13);
                int quantity = rs.getInt(14);
                String category = rs.getString(15);
                list.add(new Product(id, isDelete, isShow, quantity, name, createdBy, deletedBy, updatedBy, description, image, category, updatedAt, createdAt, deletedAt, price));
            }
        } catch (Exception e) {
            System.out.println("CheckProduct:" + e.getMessage());
        }
        return list;
    }

    public List<Product> getListProductPagination(int limit, int offset) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM `product`"
                + " WHERE isShow = 1 AND isDelete = 0 LIMIT ? OFFSET ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, limit);
            st.setInt(2, offset);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int isDelete = rs.getInt(2);
                String createdBy = rs.getString(3);
                String deletedBy = rs.getString(4);
                String updatedBy = rs.getString(5);
                long updatedAt = rs.getLong(6);
                int price = rs.getInt(7);
                long createdAt = rs.getLong(8);
                long deletedAt = rs.getLong(9);
                String name = rs.getString(10);
                String description = rs.getString(11);
                String image = rs.getString(12);
                int isShow = rs.getInt(13);
                int quantity = rs.getInt(14);
                String category = rs.getString(15);
                list.add(new Product(id, isDelete, isShow, quantity, name, createdBy, deletedBy, updatedBy, description, image, category, updatedAt, createdAt, deletedAt, price));
            }
        } catch (Exception e) {
            System.out.println("CheckProduct:" + e.getMessage());
        }
        return list;
    }

    public int getQuantityById(int productId) {
        int quantity = 0;
        try {
            String strSelect = "SELECT quantity FROM `product` WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setInt(1, productId);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                quantity = rs.getInt(1);

            }
        } catch (Exception e) {
            System.out.println("getEmailUser" + e.getMessage());
            quantity = -1;

        }
        return quantity;
    }

// Phân trang
    public double countProduct(String sql) {

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

//    public List<Product> pagingProduct(int index, int option) {
//        List<Product> list = new ArrayList<>();
//        String sql = "SELECT * FROM product LIMIT ? OFFSET ?";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setInt(1, option);
//            st.setInt(2, (index - 1) * 3);
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt(1);
//                int isDelete = rs.getInt(2);
//                String createdBy = rs.getString(3);
//                String deletedBy = rs.getString(4);
//                String updatedBy = rs.getString(5);
//                long updatedAt = rs.getLong(6);
//                double price = rs.getDouble(7);
//                long createdAt = rs.getLong(8);
//                long deletedAt = rs.getLong(9);
//                String name = rs.getString(10);
//                String description = rs.getString(11);
//                String image = rs.getString(12);
//                int isShow = rs.getInt(13);
//                int quantity = rs.getInt(14);
//                String category = rs.getString(15);
//                list.add(new Product(id, isDelete, isShow, quantity, name, createdBy, deletedBy, updatedBy, description, image, category, updatedAt, createdAt, deletedAt, price));
//            }
//        } catch (Exception e) {
//            System.out.println("CheckProduct:" + e.getMessage());
//        }
//        return list;
//    }
//     Chạy câu lệnh SQL để Filter 
    public List<Product> getListProductFilter(String sql) {
        List<Product> list = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int isDelete = rs.getInt(2);
                String createdBy = rs.getString(3);
                String deletedBy = rs.getString(4);
                String updatedBy = rs.getString(5);
                long updatedAt = rs.getLong(6);
                int price = rs.getInt(7);
                long createdAt = rs.getLong(8);
                long deletedAt = rs.getLong(9);
                String name = rs.getString(10);
                String description = rs.getString(11);
                String image = rs.getString(12);
                int isShow = rs.getInt(13);
                int quantity = rs.getInt(14);
                String category = rs.getString(15);
                list.add(new Product(id, isDelete, isShow, quantity, name, createdBy, deletedBy, updatedBy, description, image, category, updatedAt, createdAt, deletedAt, price));
            }
        } catch (Exception e) {
            System.out.println("CheckProduct:" + e.getMessage());
        }
        return list;
    }

    public List<Integer> getListPrice() {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT DISTINCT `price` FROM `product` ORDER BY `price` ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int price = rs.getInt(1);
                list.add(price);
            }
        } catch (Exception e) {
            System.out.println("Get list price product:" + e.getMessage());
        }
        return list;
    }

    public List<String> getListCategory() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT `category` FROM `product` ORDER BY `category` ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String category = rs.getString(1);
                list.add(category);
            }
        } catch (Exception e) {
            System.out.println("Get list price product:" + e.getMessage());
        }
        return list;
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
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();

        Product pr = pDAO.getProductById(1);

//        System.out.println(pr.getName());
//
//        System.out.println(pDAO.getListProduct());
        System.out.println(pDAO.getListCategory());
        System.out.println(pDAO.getListCategory().size());

    }
}
