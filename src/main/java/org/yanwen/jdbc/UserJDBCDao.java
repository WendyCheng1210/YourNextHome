package org.yanwen.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJDBCDao {
    static final String DBURL = "jdbc:postgresql://localhost:5555/homeGoods";
    static final String USER = "admin";
    static final String PASS = "password";
    private Logger logger = LoggerFactory.getLogger(getClass()); //crate a logger instance
    //CRUD

    public List<UserJDBC> getUsers(){
        List<UserJDBC> userJDBCS = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //STEP 2: Open a connection
            logger.debug("open connection..");
            conn = DriverManager.getConnection(DBURL, USER, PASS);

            //STEP 3: Execute a query
            logger.info("create statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM users";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()) {
                //Retrieve by column name
                Long id  = rs.getLong("id");
                String user_name = rs.getString("user_name");
                String password = rs.getString("password");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                Date birthday = rs.getDate("birthday");
                String address = rs.getString("address");

                //Fill the object
                UserJDBC userJDBC = new UserJDBC();
                userJDBC.setId(id);
                userJDBC.setUser_name(user_name);
                userJDBC.setPassword(password);
                userJDBC.setFirst_name(first_name);
                userJDBC.setLast_name(last_name);
                userJDBC.setPhone(phone);
                userJDBC.setEmail(email);
                userJDBC.setGender(gender);
                userJDBC.setBirthday(birthday);
                userJDBC.setAddress(address);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            //STEP 6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return userJDBCS;
    }

    public static void main(String[] args){
        UserJDBCDao userJDBCDao = new UserJDBCDao();
        System.out.println(userJDBCDao.getUsers());
    }
}
