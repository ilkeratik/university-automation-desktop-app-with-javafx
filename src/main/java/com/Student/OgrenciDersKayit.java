package com.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

public class OgrenciDersKayit implements Initializable {
    @FXML
    private VBox tablesVbox;
    private Connection con;
    private static final Student student = Student.getInstance();
    @FXML
    private TableView<OgrenciDersKayitModel> kayitTable;
    @FXML
    private TableView<OgrenciDersKayitModel>  tableSelectedLessons;
    private ObservableList<OgrenciDersKayitModel> lessonsData;
    private ObservableList<OgrenciDersKayitModel> selectedLessons = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DB_Connector.getInstance().getConnection();
            setKayitTableColumns(kayitTable);
            fetchLessons();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void setKayitTableColumns(TableView table) {
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("id"));
        TableColumn nameCol = new TableColumn("Adi");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("name"));
        TableColumn deptCol = new TableColumn("Bolum");
        deptCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("dept"));
        TableColumn teacherCol = new TableColumn("O.Gorevlisi");
        teacherCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("teacher"));
        TableColumn sinifCol = new TableColumn("Sinif");
        sinifCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("sinif"));
        TableColumn donemCol = new TableColumn("Donem");
        donemCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("donem"));
        TableColumn ZSCol = new TableColumn("Z/S");
        ZSCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("zs"));
        TableColumn creditCol = new TableColumn("Kredi");
        creditCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("credit"));
        TableColumn ectsCol = new TableColumn("AKTS");
        ectsCol.setCellValueFactory(new PropertyValueFactory<StudentLessonListModel, String>("akts"));

        table.getColumns().addAll(idCol,nameCol,deptCol,teacherCol,sinifCol,donemCol,ZSCol,creditCol,ectsCol);

    }

    public void fetchLessons() throws SQLException{
        lessonsData = FXCollections.observableArrayList();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select id, name, department, teacher, sinif, donem, ZS, ects, credit from university.lessons");
        while (rs.next()) {
            lessonsData.add(
                    new OgrenciDersKayitModel(rs.getString(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6),
                            rs.getString(7),rs.getString(8),rs.getString(9)));//adding data as rows
        }
        kayitTable.setItems(lessonsData);
    }

    public void addLesson(){
        if (tableSelectedLessons.getColumns().isEmpty()){
            setKayitTableColumns(tableSelectedLessons);
        }
        if(kayitTable.getSelectionModel().getSelectedItem() != null){
            OgrenciDersKayitModel selectedItem = kayitTable.getSelectionModel().getSelectedItem();
            if(!selectedLessons.contains(selectedItem)){
                selectedLessons.add(selectedItem);
                tableSelectedLessons.setItems(selectedLessons);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uyari");
                alert.setHeaderText("Bu dersi zaten sectiniz!");
                alert.show();
            }
        }
    }
    public void removeLesson(){
        if(tableSelectedLessons.getSelectionModel().getSelectedItem() != null){
            OgrenciDersKayitModel selectedItem = tableSelectedLessons.getSelectionModel().getSelectedItem();
            selectedLessons.remove(selectedItem);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uyari");
            alert.setHeaderText("Listeden Cikarmak icin ders secin!");
            alert.show();
        }

    }
    public void approveDersKaydi(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ders Kayit Onay");
        alert.setHeaderText("Ders listesini onayliyor musunuz?");
        Student student = Student.getInstance();
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String term;
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if(month > 8){
            term = "Guz";
        }
        else{
            term = "Bahar";
        }
        JSONObject istek = new JSONObject();
        JSONArray lessonList = new JSONArray();

        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                selectedLessons.forEach(lesson ->{
                    lessonList.put(lesson.getId());
                });
            }
            istek.put(year+" "+term, lessonList);
            System.out.println(istek.toString());
            try {
                Connection con = DB_Connector.getInstance().getConnection();
                PreparedStatement stmt = con.prepareStatement("insert into university.enrollrequests(id, student, danisman, lessonlist, status)" +
                        "values(?,?,?,?,0)");
                stmt.setString(1,year+term+student.getId());
                stmt.setString(2,student.getId());
                stmt.setString(3,student.getDanisman());
                stmt.setString(4, istek.toString());
                stmt.executeUpdate();
            } catch (SQLException throwables) {
                alert.setTitle("Uyari");
                alert.setHeaderText("Ders kaydi istegi sistemde zaten var ya da bir hata olustu..");
                alert.show();
            }
        });
    }
}
