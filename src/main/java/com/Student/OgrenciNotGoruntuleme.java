package com.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import com.DB_Connector;

import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class OgrenciNotGoruntuleme implements Initializable {

    private Connection con;
    private static final Student student = Student.getInstance();
    @FXML
    private ComboBox termBox;
    @FXML
    private TableView<StudentLessonListModel> termLessonsTable;
    private  ObservableList<StudentLessonListModel> termLessonsList=  FXCollections.observableArrayList();
    private JSONObject lessonsjson;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
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
            setColumnsTermLessonList();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public void setColumnsTermLessonList(){
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("id"));

        TableColumn nameCol = new TableColumn("Adi");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<StudentLessonListModel, String>("name"));

        TableColumn vizeCol = new TableColumn("Vize");
        vizeCol.setCellValueFactory(
                new PropertyValueFactory<StudentLessonListModel, String>("vize"));
        TableColumn finalCol = new TableColumn("Final");
        finalCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("finalN"));

        TableColumn ortalamaCol = new TableColumn("Ortalama");
        ortalamaCol.setCellValueFactory(
                new PropertyValueFactory<StudentLessonListModel, String>("average"));

        TableColumn harfCol = new TableColumn("Harf");
        harfCol.setCellValueFactory(
                new PropertyValueFactory<StudentLessonListModel, String>("grade"));

        TableColumn durumCol = new TableColumn("Durum");
        durumCol.setCellValueFactory(
                new PropertyValueFactory<StudentLessonListModel, String>("state"));
        termLessonsTable.getColumns().addAll(idCol, nameCol, vizeCol, finalCol, ortalamaCol,harfCol,durumCol);
    }
    public void getTermLessons() throws SQLException {
        String term = termBox.getSelectionModel().getSelectedItem().toString();
        JSONArray lessons = lessonsjson.getJSONArray(term);
        JSONArrayToModel(lessons);
    }

    public void JSONArrayToModel(JSONArray lessons) {
        termLessonsList.clear();
        lessons.forEach(lesson -> {
            try {
                Statement stmt = con.createStatement();
                String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                ResultSet nameResult = con.createStatement().executeQuery("select name from university.lessons where id='" + lesson + "'");//universite adi getirme
                nameResult.next();
                String lessonName = nameResult.getString(1);
                String query = "select vize,final, average,grade,state from university." + (lesson +"_"+year)+
                        " where student=" + student.getId();
                System.out.println(query);
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                System.out.println(""+rs.getString(1)+" "+ student.getId() );
                termLessonsList.add(
                        new StudentLessonListModel(lesson.toString(), lessonName, rs.getString(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5)));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        termLessonsTable.setItems(termLessonsList);
    }
}
