package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import objects.Enrollment;
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

        DatabaseConnection databaseConnection = new DatabaseConnection();

        TableView<Enrollment> table = createTable(databaseConnection);

        table.setPrefWidth(700);

        //test
        Scene scene = new Scene(table);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

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

        ObservableList<Enrollment> data = getEnrollment(databaseConnection);
        
        TableView<Enrollment> table = new TableView();
        TableColumn<Enrollment, String> studentMailCol = new TableColumn("Student email");
        TableColumn<Enrollment, String> courseNameCol = new TableColumn("Cursusnaam");
        TableColumn<Enrollment, String> percentageCol = new TableColumn("Percentage");
        TableColumn<Enrollment, String> enrollmentDateCol = new TableColumn("Inschrijfdatum");
        TableColumn<Enrollment, String> enrollmentIdCol = new TableColumn("InschrijvingsID");

        studentMailCol.setCellValueFactory(new PropertyValueFactory<>("StudentMail"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("Percentage"));
        enrollmentDateCol.setCellValueFactory(new PropertyValueFactory<>("EnrollmentDate"));
        enrollmentIdCol.setCellValueFactory(new PropertyValueFactory<>("EnrollmentId"));

        table.getColumns().addAll(studentMailCol, courseNameCol, percentageCol, enrollmentDateCol, enrollmentIdCol);

        table.setItems(data);

        return table;
    }

    //method to get all student information
    public ObservableList<Enrollment> getEnrollment(DatabaseConnection databaseConnection) throws SQLException, ClassNotFoundException { 
        ObservableList<Enrollment> enrollments = FXCollections.observableArrayList();

        databaseConnection.openConnection();

        String selectStatement = "SELECT * FROM Enrollment WHERE StudentMail = '" + student.getEmail() + "'"; //Statement to get all student information

        try { //execute select statement 
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement(selectStatement);

            while (resultSet.next()) {
                Enrollment enrollment = new Enrollment(resultSet.getString("StudentMail"), resultSet.getString("CourseName"), resultSet.getInt("Percentage"), resultSet.getDouble("Grade"), resultSet.getDate("EnrollmentDate"), resultSet.getInt("EnrollmentId"));
                enrollments.add(enrollment);
            }
            
        } catch (SQLException e) {
            System.out.println("SQL select query was niet succesvol: " + e.getMessage());
            //Shows exception
            throw e;
        }

        databaseConnection.closeConnection();
        return enrollments;
    }
}
