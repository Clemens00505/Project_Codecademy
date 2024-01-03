package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;
import objects.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentGUI extends Application {

    @Override
    public void start(Stage studentGUI) throws Exception {

        studentGUI.show();
        studentGUI.setTitle("Studenten");

        //labels for the textfields
        Label emailLabel = new Label("Email: ");
        Label nameLabel = new Label("Naam: ");
        Label genderLabel = new Label("Gender: ");
        Label dateOfBirthLabel = new Label("Geboortedatum: ");
       
        //add textfields
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        TextField genderInput = new TextField();
        TextField dateOfBirthInput = new TextField();

        //add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        genderInput.setPromptText("man/vrouw/anders");
        dateOfBirthInput.setPromptText("yyyy-mm-dd");

        //alles wat te maken heeft met input in een VBox zetten
        VBox inputFields = new VBox(emailLabel, emailInput, nameLabel, nameInput, genderLabel, genderInput, dateOfBirthLabel, dateOfBirthInput);

        
        //Create buttons
        Button addStudentButton = new Button("Student toevoegen");
        Button editStudentButton = new Button("Student bewerken");
        Button deleteStudentButton = new Button("Student verwijderen");
        Button confirmButton = new Button("Aanpassing bevestigen");
        Button showEnrollmentsButton = new Button("Inschrijvingen");
        Button backButton = new Button("Terug naar hoofdmenu");

        int equalWidth = 175;
        addStudentButton.setMinWidth(equalWidth);
        editStudentButton.setMinWidth(equalWidth);
        deleteStudentButton.setMinWidth(equalWidth);
        confirmButton.setMinWidth(equalWidth);
        showEnrollmentsButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        //put buttons in a vbox
        VBox buttons = new VBox(addStudentButton, editStudentButton, deleteStudentButton, confirmButton, showEnrollmentsButton, backButton);


        DatabaseConnection databaseConnection = new DatabaseConnection();

        TableView<Student> table = createTable(databaseConnection);

        table.setPrefWidth(450);

        VBox rightSide = new VBox(inputFields, buttons);

        rightSide.setPrefWidth(250);

        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        studentGUI.setScene(scene);

        //eventhandler for adding student
        addStudentButton.setOnAction((event) -> {
            try {
                // System.out.println(databaseConnection.connectionIsOpen());

                String email = emailInput.getText();
                String name = nameInput.getText();
                String gender = genderInput.getText();
                String dateOfBirthString = dateOfBirthInput.getText();
                Date dateOfBirth = Date.valueOf(dateOfBirthString);

                Student student = new Student(email, name, gender, dateOfBirth);

                student.addStudent(student, databaseConnection);
                System.out.println(student);

                emailInput.setText(null);
                nameInput.setText(null);
                genderInput.setText(null);
                dateOfBirthInput.setText(null);
                
                refreshTable(databaseConnection, table);                
            } catch (Exception e) {
                System.out.println(e);
            }
        });


        //eventhandler for updating student
        editStudentButton.setOnAction((event) -> {
            Student selectedStudent = table.getSelectionModel().getSelectedItem();

            if (selectedStudent != null) {
                String email = selectedStudent.getEmail();
                String name = selectedStudent.getName();
                String gender = selectedStudent.getGender();
                Date dateOfBirth = selectedStudent.getDateOfBirth();
                String dateOfBirthString = String.valueOf(dateOfBirth);

                emailInput.setText(email);
                nameInput.setText(name);
                genderInput.setText(gender);
                dateOfBirthInput.setText(dateOfBirthString);

                confirmButton.setOnAction((eventConfirm) -> {
                    String emailUpdated = emailInput.getText();
                    String nameUpdated = nameInput.getText();
                    String genderUpdated = genderInput.getText();
                    String dateOfBirthStringUpdated = dateOfBirthInput.getText();
                    Date dateOfBirthUpdated = Date.valueOf(dateOfBirthStringUpdated);

                    Student studentUpdated = new Student(emailUpdated, nameUpdated, genderUpdated, dateOfBirthUpdated);

                    try {
                        studentUpdated.updateStudent(studentUpdated, databaseConnection);
                        refreshTable(databaseConnection, table);

                        if (selectedStudent != null) {
                            emailInput.setText(null);
                            nameInput.setText(null);
                            genderInput.setText(null);
                            dateOfBirthInput.setText(null);
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                });
            }            
        });

        //eventhandler for deleting student
        deleteStudentButton.setOnAction((event) -> {
            Student selectedStudent = table.getSelectionModel().getSelectedItem();

            if (selectedStudent != null) {
                try {
                    String email = selectedStudent.getEmail();
                    String name = selectedStudent.getName();
                    String gender = selectedStudent.getGender();
                    Date dateOfBirth = selectedStudent.getDateOfBirth();

                    Student student = new Student(email, name, gender, dateOfBirth);

                    student.deleteStudent(email, databaseConnection);
                    
                    refreshTable(databaseConnection, table);
                } catch (Exception e) {
                    System.out.println(e);
                }
                
            }       
        });

        //eventhandler for button to return to menu
        backButton.setOnAction((event) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            try {
                codecademyGUI.start(codecademyStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //close current stage
            studentGUI.close();
        });

        //button to show students enrollments
        showEnrollmentsButton.setOnAction((event) -> {
            

            
            Student selectedStudent = table.getSelectionModel().getSelectedItem();

            if (selectedStudent != null) {
                try {
                    String email = selectedStudent.getEmail();
                    String name = selectedStudent.getName();
                    String gender = selectedStudent.getGender();
                    Date dateOfBirth = selectedStudent.getDateOfBirth();

                    Student student = new Student(email, name, gender, dateOfBirth);

                    EnrollmentGUI enrollmentGUI = new EnrollmentGUI(student);
                    Stage enrollmentStage = new Stage();
                    enrollmentStage.setTitle("Inschrijvingen");

                    try {
                        enrollmentGUI.start(enrollmentStage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            studentGUI.close();
        });

    }


    //method for creating a table with all student data. Uses getAllStudents to get the data from the database
    public TableView createTable(DatabaseConnection databaseConnection) throws ClassNotFoundException, SQLException {

        ObservableList<Student> data = getAllStudents(databaseConnection);
        
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
    public ObservableList<Student> getAllStudents(DatabaseConnection databaseConnection) throws SQLException, ClassNotFoundException { 
        ObservableList<Student> students = FXCollections.observableArrayList();

        databaseConnection.openConnection();

        String selectStatement = "SELECT * FROM Student"; //Statement to get all student information

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

    public void refreshTable(DatabaseConnection databaseConnection, TableView<Student> table) {
        try {
            ObservableList<Student> data = getAllStudents(databaseConnection);
            table.setItems(data);
            table.refresh();
        } catch (Exception e) {
            System.out.println("Error refreshing table: " + e);
        }
    }
    

}
