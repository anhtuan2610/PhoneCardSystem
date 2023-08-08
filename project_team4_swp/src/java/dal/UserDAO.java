/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dbcontext.DBContext;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author phamtung
 */
public class UserDAO extends DBContext {

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM `user`";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt("id"), rs.getInt("role"),
                        rs.getInt("isDelete"), rs.getString("name"),
                        rs.getString("phone"), rs.getString("email"),
                        rs.getString("account"), rs.getString("passWord"),
                        rs.getString("createdBy"), rs.getString("updatedBy"),
                        rs.getString("deletedBy"), rs.getLong("updatedAt"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getInt("balance")
                );

                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public User getUserById(int Id) {
        User u = new User();
        String sql = "SELECT  * "
                + " FROM `user` WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

//                User(
//                );
                u = new User(rs.getInt("id"), rs.getInt("role"),
                        rs.getInt("isDelete"), rs.getString("name"),
                        rs.getString("phone"), rs.getString("email"),
                        rs.getString("account"), rs.getString("passWord"),
                        rs.getString("createdBy"), rs.getString("updatedBy"),
                        rs.getString("deletedBy"), rs.getLong("updatedAt"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getInt("balance"), rs.getString("activityLog")
                );

            }
        } catch (SQLException e) {
            System.out.println(e);
            u = null;
        }

        return u;
    }

    public User getUserByRole(int role) {
        User u = new User();
        String sql = "SELECT  * "
                + " FROM `user` WHERE role = ? LIMIT 1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, role);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                u = new User(rs.getInt("id"), rs.getInt("role"),
                        rs.getInt("isDelete"), rs.getString("name"),
                        rs.getString("phone"), rs.getString("email"),
                        rs.getString("account"), rs.getString("passWord"),
                        rs.getString("createdBy"), rs.getString("updatedBy"),
                        rs.getString("deletedBy"), rs.getLong("updatedAt"),
                        rs.getLong("createdAt"), rs.getLong("deletedAt"),
                        rs.getInt("balance")
                );

            }
        } catch (SQLException e) {
            System.out.println(e);
            u = null;
        }

        return u;
    }

    //    UPDATE `profile` SET `fullName`= ? ,`phone`= ?,`email`= ? ,`updateBy`= ? ,`updateAt`= ? WHERE id = ?
    public boolean updateById(User u) {
        String sql = "UPDATE `user` SET"
                + "`updatedBy`= ?,`updatedAt`= ?,`name`= ?,"
                + "`phone`=?,`email`=?, `activityLog` = ? WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, u.getUpdatedBy());
            st.setLong(2, u.getUpdatedAt());
            st.setString(3, u.getName());
            st.setString(4, u.getPhone());
            st.setString(5, u.getEmail());
            st.setString(6, u.getActivityLog());
            st.setInt(7, u.getId());

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    //    UPDATE `profile` SET `fullName`= ? ,`phone`= ?,`email`= ? ,`updateBy`= ? ,`updateAt`= ? WHERE id = ?

    public boolean updatePasswordById(User u) {
        String sql = "UPDATE `user` SET `password` = ?, `updatedBy` = ?,"
                + " `updatedAt` = ?, `activityLog` = ? WHERE id = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, u.getPassword());
            st.setString(2, u.getUpdatedBy());
            st.setLong(3, u.getUpdatedAt());
            st.setString(4, u.getActivityLog());
            st.setInt(5, u.getId());

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

//    Update blance
    public boolean updateBalanceById(double balance, int id) {
        String sql = "UPDATE `user` SET `balance`= ? "
                + " WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setDouble(1, balance);
            st.setInt(2, id);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean checkAccountExist(String user, String passWord) {

        User u = new User();

        String sql = "SELECT * FROM `user`"
                + " WHERE account = ? AND passWord = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user);
            st.setString(2, passWord);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
//                u = new User(rs.getInt("id"), rs.getInt("role"),
//                        rs.getString("name"),
//                        rs.getString("phone"), rs.getString("email"),
//                        rs.getString("account")
//                );
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);

        }

        return false;
    }

    public String getEmailByAccount(String account) {
        try {
            String strSelect = "SELECT email FROM user WHERE account = ?";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, account);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);
                return email;
            }
        } catch (Exception e) {
            System.out.println("getEmailUser" + e.getMessage());
        }
        return null;

    }

    public boolean changePassword(String account, String newPassword, String activityLog) {
        try {
            String strSelect = "UPDATE `user` SET `password` = ?, `activityLog` = ? WHERE `account` = ?";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, newPassword);
            ps.setString(2, activityLog);
            ps.setString(3, account);
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("getEmailUser" + e.getMessage());
        }
        return false;
    }

    public User checkUser(String account1, String password1) {
        try {
            String strSelect = "select * from User where account = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, account1);
            ps.setString(2, password1);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {

                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phone = rs.getString(3);
                String email = rs.getString(4);
                int role = rs.getInt(5);
                String account = rs.getString(6);
                String password = rs.getString(7);
                int isDelete = rs.getInt(8);
                String createdBy = rs.getString(9);
                String deletedBy = rs.getString(10);
                String updatedBy = rs.getString(11);
                long createdAt = rs.getLong(12);
                long updatedAt = rs.getLong(13);
                long deletedAt = rs.getLong(14);
                int balance = rs.getInt(15);
                User user = new User(id, role, isDelete, name, phone, email, account, password, createdBy, deletedBy, updatedBy, createdAt, updatedAt, deletedAt, balance);
                return user;
            }
        } catch (Exception e) {
            System.out.println("checkUser: " + e.getMessage());
        }
        return null;
    }

    public Boolean checkAccount(String account1) {
        try {
            String strSelect = "select * from User where account = ?";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, account1);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("checkUser: " + e.getMessage());
        }
        return false;
    }

    public Boolean checkUserStatus(String account1) {
        try {
            String strSelect = "select * from User where account = ? AND isDelete = 0";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, account1);
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("checkUser: " + e.getMessage());
        }
        return false;
    }

    public boolean kiemTraTenDangNhap(String account) throws ClassNotFoundException {
        boolean ketQua = false;
        try {
            String sql = "SELECT * FROM User WHERE account=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, account);

            // Bước 3: thực thi câu lệnh SQL
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                ketQua = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("checkAccount: " + e.getMessage());
        }
        return ketQua;
    }

    public boolean insert(User t) throws ClassNotFoundException {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            //Connection con = DBContext.makeConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "INSERT INTO user (name,phone,email,role,account,password,createdAt) "
                    + " VALUES (?,?,?,2,?,?,?)";

            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, t.getName());
            st.setString(2, t.getPhone());
            st.setString(3, t.getEmail());
            st.setString(4, t.getAccount());
            st.setString(5, t.getPassword());
            st.setLong(6, t.getCreatedAt());
            //st.setInt(4, t.getRole());
            // Bước 3: thực thi câu lệnh SQL
            ketQua = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("checkAddUser: " + e.getMessage());
            return false;
        }

    }

    public int getBalanceById(int id) {
        try {
            String strSelect = "SELECT `balance` FROM `user`"
                    + " WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setInt(1, id);

            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                int balance = rs.getInt(1);
                return balance;
            }
        } catch (Exception e) {
            System.out.println("getEmailUser" + e.getMessage());
        }
        return -1;

    }

    public int getUserIdByAccount(String account) {
        try {
            String strSelect = "SELECT `id` FROM `user`"
                    + " WHERE `account` = ?;";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, account);

            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(1);
                return userId;
            }
        } catch (Exception e) {
            System.out.println("getEmailUser" + e.getMessage());
        }
        return 0;
    }
// Phân trang

    public double countUser(String sql) {
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

    public List<User> getListUserPagination(String sql) {
        List<User> list = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phone = rs.getString(3);
                String email = rs.getString(4);
                int role = rs.getInt(5);
                String account = rs.getString(6);
                String password = rs.getString(7);
                int isDelete = rs.getInt(8);
                String createdBy = rs.getString(9);
                String deletedBy = rs.getString(10);
                String updatedBy = rs.getString(11);
                long createdAt = rs.getLong(12);
                long updatedAt = rs.getLong(13);
                long deletedAt = rs.getLong(14);
                int balance = rs.getInt(15);
                String activityLog = rs.getString(16);
                User user = new User(id, role, isDelete, name, phone, email, account, password, createdBy, deletedBy, updatedBy, createdAt, updatedAt, deletedAt, balance, activityLog);
                list.add(user);
            }
        } catch (Exception e) {
            System.out.println("Check user list UserDAO:" + e.getMessage());
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

    public static void main(String[] args) {
        UserDAO uSer = new UserDAO();

//       boolean falg = uSer.updateBalanceById(500, 2);
//        
//        System.out.println("Update tien:" + falg);
        List<User> list = uSer.getListUserPagination("SELECT * FROM `user` ORDER BY createdAt DESC LIMIT 5 OFFSET 0");

        User u = new User();

        u = uSer.getUserById(1);

        System.out.println(u.getActivityLog());

//        System.out.println(
//                u.getAccount() + " "
//                + u.getCreatedAt() + " "
//                + u.getEmail() + " "
//                + u.getPhone() + " "
//                + u.getBalance() + " "
//                + u.getIsDelete() + " "
//                + u.getRole() + " "
//                + u.getPassWord() + " "
//        );
//        System.out.println(list.get(0).getAccount() + "  "
//                + list.get(0).getId() + "  "
//                + list.get(0).getIsDelete() + "  "
//                + list.get(0).getPassword() + "  "
//                + list.get(0).getUpdatedBy() + "  "
//                + list.get(0).getName() + "  "
//                + list.get(0).getEmail() + "  "
//                + list.get(0).getPhone() + "  "
//                + list.get(0).getBalance() + "  "
//                + list.get(0).getRole() + "  "
//                + list.get(0).getCreatedBy() + "  "
//                + list.get(0).getDeletedBy() + "  "
//                + list.get(0).getCreatedAt() + "  "
//                + list.get(0).getDeletedAt() + "  "
//                + list.get(0).getActivityLog() + "  "
//                + list.get(0).getUpdatedAt());
//        u = uSer.checkAccountExist("user1", "111");
//        System.out.println(u.getId() + "   " + u.getRole());
    }

}
