package com.Student;
import com.DB_Connector;
import com.User;

import java.sql.*;

public class Student extends User {

    private String registryDate;
    private String currentGrade;
    private String danisman;
    private static Student instance;//singleton instance

    private Student(String id, String name, String email, String password, String registryDate, String currentGrade,
                    String picture, String faculty ,
                    String department, String danisman) {
        super(id, name, email, password, picture, faculty, department,"StudentMangWindow");//User constructor
        this.registryDate = registryDate;
        this.currentGrade = currentGrade;
        this.danisman = danisman;
    }

    public static void setInstance(Student instance) {
        Student.instance = instance;
    }
    public static Student getInstance(){
        if (instance == null) {
            System.out.println("stdent instance duzelt");
            return instance;
        }
        return instance;
    }
    public static Student getInstance(String id) throws SQLException {
        if (instance == null) {
            Connection con = DB_Connector.getInstance().getConnection();
            PreparedStatement stmt= con.prepareStatement("select * from university.getstudentdetails where id=? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            System.out.println("ddd");
            instance = new Student(rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
                    rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10));
        }
        return instance;
    }

    public String getRegistryDate() {
        return registryDate;
    }
    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }
    public String getCurrentGrade() {
        return currentGrade;
    }
    public void setCurrentGrade(String currentGrade) {
        this.currentGrade = currentGrade;
    }
    public String getDanisman() {
        return danisman;
    }
    public void setDanisman(String danisman) {
        this.danisman = danisman;
    }

}
