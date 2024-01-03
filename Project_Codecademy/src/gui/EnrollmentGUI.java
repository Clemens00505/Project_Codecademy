package gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Student;

public class EnrollmentGUI extends Application {
    private Student student;
    
    public EnrollmentGUI(Student student) {
        this.student = student;
    }

    @Override
    public void start(Stage enrollmentGUI) throws Exception {
        enrollmentGUI.setTitle("Inschrijvingen");

        Button backButton = new Button("Terug");
        Label testLabel = new Label(student.toString());

        

        // Scene scene = new Scene();

        // enrollmentGUI.setScene(scene);
        // enrollmentGUI.show();



        //eventhandler for button to return to main menu
        backButton.setOnAction((event) -> {
            StudentGUI studentGUI = new StudentGUI();

            Stage studentStage = new Stage();
            studentStage.setTitle("Menu");

            try {
                studentGUI.start(studentStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //close current stage
            enrollmentGUI.close();
        });
    
    }

    //method for creating a table with all student data. Uses getAllStudents to get the data from the database
    public TableView createTable(DatabaseConnection databaseConnection) throws ClassNotFoundException, SQLException {

        ObservableList<Student> data = getEnrollment(databaseConnection);
        
        TableView<Student> table = new TableView();
        TableColumn<Student, String> emailCol = new TableColumn("Email");
        TableColumn<Student, String> nameCol = new TableColumn("Naam");
        TableColumn<Student, String> genderCol = new TableColumn("Gender");
        TableColumn<Student, String> dateOfBirthCol = new TableColumn("Geboortedatum");

        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));

        table.getColumns().addAll(emailCol, nameCol, genderCol, dateOfBirthCol);

        table.setItems(data);

        return table;
    }

    //method to get all student information
    public ObservableList<Student> getEnrollment(DatabaseConnection databaseConnection) throws SQLException, ClassNotFoundException { 
        ObservableList<Student> students = FXCollections.observableArrayList();

        databaseConnection.openConnection();

        String selectStatement = "SELECT * FROM Enrollment WHERE StudentMail = '" + student.getEmail() + "'"; //Statement to get all student information

        try { //execute select statement 
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement(selectStatement);

            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("Email"), resultSet.getString("Name"), resultSet.getString("Gender"), resultSet.getDate("DateOfBirth"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.out.println("SQL select query was niet succesvol: " + e);
            //Shows exception
            throw e;
        }

        databaseConnection.closeConnection();
        return students;
    }
}
