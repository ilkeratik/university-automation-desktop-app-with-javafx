package com.Teacher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;
import com.DB_Connector;

import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class TeacherDersKayit implements Initializable {

    private Connection con;
    private Teacher teacher = Teacher.getInstance();
    @FXML
    private VBox dersListeBox;
    @FXML
    private TableView<IstenenDersListesiModel> lessonsDetailTable;
    private ObservableList<IstenenDersListesiModel> dersListesi=  FXCollections.observableArrayList();
    @FXML
    private TableView<DersKayitIstekleriModel> dersKayitIstekTable;
    private ObservableList dersKayitIstekleri = FXCollections.observableArrayList();
    private JSONObject lessonJson;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            TableColumn idCol = new TableColumn("id");
            idCol.setCellValueFactory(new PropertyValueFactory<DersKayitIstekleriModel, String>("id"));
            TableColumn studentCol = new TableColumn("Ogrenci No");
            studentCol.setCellValueFactory(
                    new PropertyValueFactory<DersKayitIstekleriModel, String>("student"));
            TableColumn stateCol = new TableColumn("Durumu");
            stateCol.setCellValueFactory(
                    new PropertyValueFactory<DersKayitIstekleriModel, String>("state"));

            con = DB_Connector.getInstance().getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select id, student, status from university.enrollrequests");
            while (rs.next()) {
                //Iterate Row
                dersKayitIstekleri.add(
                        new DersKayitIstekleriModel(rs.getString(1), rs.getString(2),rs.getInt(3)));
            }
            dersKayitIstekTable.setItems(dersKayitIstekleri);
            dersKayitIstekTable.getColumns().addAll(idCol, studentCol, stateCol);

        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    @FXML
    void approveDersKaydi(ActionEvent event) throws SQLException {
        String student = dersKayitIstekTable.getSelectionModel().getSelectedItem().getStudent();
        String requestId = dersKayitIstekTable.getSelectionModel().getSelectedItem().getId();
        JSONObject jsonObject = new JSONObject();
        String key = lessonJson.keys().next();
        JSONArray lessonArray = lessonJson.getJSONArray(key);
        jsonObject.put(key, lessonArray);
        System.out.println(lessonJson.toString());
        System.out.println(student);
        PreparedStatement stmt = con.prepareStatement("UPDATE university.student SET lessons = JSON_MERGE_PATCH(IFNULL(lessons,'{}' ), ?) where id= ?");
        stmt.setString(1, lessonJson.toString());
        stmt.setString(2,student);
        stmt.executeUpdate();

        PreparedStatement updateEnroll = con.prepareStatement("UPDATE university.enrollrequests SET status = 1 where id= ? ");
        updateEnroll.setString(1, requestId);
        updateEnroll.executeUpdate();

        lessonArray.forEach(lesson -> {
            try {
                String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                Statement addStudentToLessonTable = con.createStatement();
                String query = "CREATE TABLE IF NOT EXISTS "+lesson.toString()+"_"+year+" as select student,vize,final,average,grade,state from university.createlessontable";
                //create the lesson table if not exists
                addStudentToLessonTable.execute(query);
                query = "select vize from university."+lesson.toString()+"_"+year+ " where student="+student;
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.next()){
                    Statement stmts = con.createStatement();//add student to the lesson table
                    stmt.setString(1, lesson.toString()+"_"+year);
                    query = "INSERT INTO university." + (lesson.toString()+"_"+year) + " (student) values ('"+student+"')";
                    stmts.executeUpdate(query);
                }else{
                    System.out.println("Kayit zaten var");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @FXML
    void showLessonsRequest(ActionEvent event) throws SQLException {
        dersListeBox.setVisible(true);
        if(dersKayitIstekTable.getSelectionModel().getSelectedItem() != null) {
            dersListesi.clear();
            String selectedId = dersKayitIstekTable.getSelectionModel().getSelectedItem().getId();
            PreparedStatement stmt= con.prepareStatement("select lessonlist from university.enrollrequests where id=?");
            stmt.setString(1,selectedId );
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String lessons = rs.getString(1);
            lessonJson = new JSONObject(lessons);
            String key = lessonJson.keys().next();
            JSONArray lessonArray = lessonJson.getJSONArray(key);
            stmt = con.prepareStatement("select name,sinif,ects,credit from university.lessons where id=?");
            PreparedStatement finalStmt = stmt;
            lessonArray.forEach(lesson -> {
                try {
                    finalStmt.setString(1, (String) lesson);
                    ResultSet lrs = finalStmt.executeQuery();
                    lrs.next();
                    dersListesi.add(
                            new IstenenDersListesiModel((String) lesson, lrs.getString(1), lrs.getString(2),
                                    lrs.getString(3), lrs.getString(4)));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            if (lessonsDetailTable.getColumns().isEmpty()) {//adding column names if not added before
                TableColumn idCol = new TableColumn("Ders Kodu");
                idCol.setCellValueFactory(new PropertyValueFactory<DersKayitIstekleriModel, String>("id"));

                TableColumn nameCol = new TableColumn("Ders Adi");
                nameCol.setPrefWidth(200);
                nameCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("name"));

                TableColumn sinifCol = new TableColumn("Dersin sinifi");
                sinifCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("sinif"));

                TableColumn aktsCol = new TableColumn("AKTS");
                aktsCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("akts"));

                TableColumn creditCol = new TableColumn("Kredi");
                creditCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("credit"));

                lessonsDetailTable.getColumns().addAll(idCol, nameCol, sinifCol, aktsCol, creditCol);
            }

            lessonsDetailTable.setItems(dersListesi);

        }
    }
}
