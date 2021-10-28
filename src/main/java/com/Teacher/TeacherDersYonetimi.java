package com.Teacher;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import com.DB_Connector;
import com.Student.StudentLessonListModel;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TeacherDersYonetimi  implements Initializable {
    private Connection con;
    private Teacher teacher = Teacher.getInstance();

    @FXML
    private TableView<TeacherLessonListModel> lessonsTable;
    private ObservableList<TeacherLessonListModel> lessonsData = FXCollections.observableArrayList();
    @FXML
    private TableView<TeacherNotGirmeModel> chosenDetailsTable;
    private ObservableList<TeacherNotGirmeModel> studentData = FXCollections.observableArrayList();
    @FXML
    private JFXButton approveBtn;
    @FXML
    private TableView<TeacherStudentLessonListModel> studentListTable;
    private ObservableList<TeacherStudentLessonListModel> enrolledData = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DB_Connector.getInstance().getConnection();

            TableColumn deptLesson = new TableColumn("Bolum");
            deptLesson.setCellValueFactory(
                    new PropertyValueFactory<DersKayitIstekleriModel, String>("department"));
            TableColumn idLesson = new TableColumn("id");
            idLesson.setCellValueFactory(new PropertyValueFactory<DersKayitIstekleriModel, String>("id"));
            TableColumn nameLesson = new TableColumn("Adi");
            nameLesson.setCellValueFactory(
                    new PropertyValueFactory<DersKayitIstekleriModel, String>("name"));
            TableColumn yearLesson = new TableColumn("Yili");
            yearLesson.setCellValueFactory(
                    new PropertyValueFactory<DersKayitIstekleriModel, String>("year"));

            PreparedStatement stmt = con.prepareStatement("select l.id, d.name, l.name, l.year from university.lessons as l " +
                    "join university.department d on d.id = l.department where l.teacher=?");
            stmt.setString(1, teacher.getId());
            ResultSet lessonRes = stmt.executeQuery();
            while(lessonRes.next()){
                lessonsData.add(
                        new TeacherLessonListModel(lessonRes.getString(1),lessonRes.getString(2),
                                lessonRes.getString(3),lessonRes.getString(4)));
            }
            lessonsTable.setItems(lessonsData);
            lessonsTable.getColumns().addAll(deptLesson, idLesson, nameLesson, yearLesson);
            lessonsTable.refresh();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void vizeGiris(ActionEvent event) throws SQLException{
        addNotColumns("vize");

    }
    @FXML
    void finalGiris(ActionEvent event) throws SQLException {
        addNotColumns("final");
    }
    @FXML
    void showStudents(ActionEvent event){
        chosenDetailsTable.setVisible(false);
        approveBtn.setVisible(false);
        String lesson = lessonsTable.getSelectionModel().getSelectedItem().getId();
        String year = lessonsTable.getSelectionModel().getSelectedItem().getYear();


        try {
            Statement statement = con.createStatement();
            String query = "select l.student, s.name, d.name, s.currentGrade from "+lesson+"_"+year+
                    " as l join university.student as s on l.student = s.id "+
                    "join university.department as d on s.department = d.id";
            ResultSet rs = statement.executeQuery(query);
            enrolledData.clear();
            while (rs.next()){
                System.out.println(rs.getString(1));
                enrolledData.add(
                        new TeacherStudentLessonListModel(rs.getString(1), rs.getString(2),
                                rs.getString(3),rs.getString(4)));
            }
            studentListTable.setVisible(true);
            studentListTable.setItems(enrolledData);
            if(studentListTable.getColumns().isEmpty()){
                TableColumn idCol = new TableColumn("No");
                idCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("id"));
                TableColumn nameCol = new TableColumn("Adi");
                nameCol.setCellValueFactory(new PropertyValueFactory<DersKayitIstekleriModel, String>("name"));
                TableColumn deptCol = new TableColumn("Bolumu");
                deptCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("dept"));
                TableColumn classCol = new TableColumn("Sinifi");
                classCol.setCellValueFactory(
                        new PropertyValueFactory<DersKayitIstekleriModel, String>("classN"));
                studentListTable.getColumns().addAll(idCol,nameCol,deptCol, classCol);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    void approveNotGirisi(ActionEvent event) {
        String mode = chosenDetailsTable.getColumns().get(1).getText();
        String id = lessonsTable.getSelectionModel().getSelectedItem().getId();
        String year= lessonsTable.getSelectionModel().getSelectedItem().getYear();
        String changeColumn;
        System.out.println("denemee "+mode);
        if(mode.equals("final")){
            changeColumn = "final";
        }
        else{
            changeColumn = "vize";
        }
        chosenDetailsTable.getItems().forEach(entry -> {
            String query = "update  university."+id+"_"+year+" set "+changeColumn+
                    "="+ entry.getNot()+" where student="+entry.getId();
            System.out.println(query);
            try{
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    @FXML
    void harfNotuHesapla(ActionEvent event) {

    }

    public void addNotColumns(String baslik) throws SQLException {
        studentListTable.setVisible(false);
        chosenDetailsTable.setVisible(true);
        approveBtn.setVisible(true);
        TableColumn idCol = new TableColumn("Ogrenci No");
        idCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("id"));

        TableColumn notCol = new TableColumn(baslik);
        notCol.setCellValueFactory(
                new PropertyValueFactory<StudentLessonListModel, String>("not"));
        notCol.setCellFactory(TextFieldTableCell.forTableColumn());

        chosenDetailsTable.getColumns().clear();
        chosenDetailsTable.getItems().clear();
        String id =lessonsTable.getSelectionModel().getSelectedItem().getId();
        String year=lessonsTable.getSelectionModel().getSelectedItem().getYear();

        Statement stm = con.createStatement();
        String query = "select student, "+baslik+" from university."+id+"_"+year;
        ResultSet studentList = stm.executeQuery(query);
        while(studentList.next()){
            studentData.add(
                    new TeacherNotGirmeModel(studentList.getString(1),studentList.getString(2)));
        }
        chosenDetailsTable.setItems(studentData);
        chosenDetailsTable.getColumns().addAll(idCol, notCol);
        notCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<TeacherNotGirmeModel, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<TeacherNotGirmeModel, String> t) {
                        ((TeacherNotGirmeModel) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setNot(t.getNewValue());
                    }
                }
        );
    }

}
