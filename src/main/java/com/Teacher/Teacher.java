package com.Teacher;

import com.DB_Connector;
import com.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher extends User {
    private static Teacher instance;//singleton instance

    private Teacher(String id, String name, String email, String password,
                    String picture, String faculty,
                    String department) {
        super(id,name,email,password,picture,faculty,department,"TeacherMangWindow");
    }

    public static Teacher getInstance(String id) throws SQLException {
        if (instance == null) {
            Connection con = DB_Connector.getInstance().getConnection();
            PreparedStatement stmt= con.prepareStatement("select * from university.getteacherdetails where id=? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            instance = new Teacher(rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
                    rs.getString(7));
        }
        return instance;
    }
    public static Teacher getInstance(){
        if (instance == null) {
            System.out.println("Teacher get instance duzelt");// change later
            return instance;
        }
        return instance;
    }

}
