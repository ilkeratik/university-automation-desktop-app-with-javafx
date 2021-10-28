package com.Student;

import com.DB_Connector;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.json.JSONObject;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OgrenciDers implements Initializable {
    @FXML
    private TabPane dersTab;
    @FXML
    private TableView kayitTable;
    @FXML
    private TableView tableSelectedLessons;
    private ObservableList lessonsAvailable;
    @FXML
    private ComboBox termBox;
    private Connection con;
    @FXML
    private TableView<StudentLessonListModel> termLessonsTable;
    private  ObservableList<StudentLessonListModel> termLessonsList=  FXCollections.observableArrayList();
    private static final Student student = Student.getInstance();

    private JSONObject lessonsjson;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DB_Connector.getInstance().getConnection();
            PreparedStatement PStatement = con.prepareStatement("select lessons from university.student where id =?");
            PStatement.setString(1, student.getId());
            ResultSet rs = PStatement.executeQuery();
            rs.next();
            lessonsjson =  new JSONObject(rs.getString(1));
            lessonsjson.keySet().forEach(key ->
            {
                System.out.println("key: "+ key);
                termBox.getItems().add(key);
            });


            lessonsAvailable = FXCollections.observableArrayList();
            Statement statement = con.createStatement();
            rs = statement.executeQuery("select * from university.lessons");
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                kayitTable.getColumns().addAll(col);//columns added
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                lessonsAvailable.add(row);//adding rows
            }

            kayitTable.setItems(lessonsAvailable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    }