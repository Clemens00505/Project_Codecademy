package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import objects.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

        // labels for the textfields
        Label emailLabel = new Label("Email: ");
        Label nameLabel = new Label("Naam: ");
        Label genderLabel = new Label("Gender: ");
        Label dateOfBirthLabel = new Label("Geboortedatum: ");

        // add textfields
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        TextField genderInput = new TextField();
        // ObservableList<String> gender = new ObservableList(
        // "man",
        // "vrouw",
        // "anders"
        // );
        // final ComboBox dropdownGender = new ComboBox(gender);
        TextField dateOfBirthInput = new TextField();

        // add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        genderInput.setPromptText("man/vrouw/anders");
        dateOfBirthInput.setPromptText("yyyy-mm-dd");

        // alles wat te maken heeft met input in een VBox zetten
        VBox inputFields = new VBox(emailLabel, emailInput, nameLabel, nameInput, genderLabel, genderInput,
                dateOfBirthLabel, dateOfBirthInput);

        // Create buttons
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

        // put buttons in a vbox
        VBox buttons = new VBox(addStudentButton, editStudentButton, deleteStudentButton, confirmButton,
                showEnrollmentsButton, backButton);

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create columns for the table
        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        TableColumn<Student, String> nameCol = new TableColumn<>("Naam");
        TableColumn<Student, String> genderCol = new TableColumn<>("Gender");
        TableColumn<Student, Date> dateOfBirthCol = new TableColumn<>("Geboortedatum");

        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        // add columns to a list
        List<TableColumn<Student, ?>> columns = new ArrayList<>();
        columns.add(emailCol);
        columns.add(nameCol);
        columns.add(genderCol);
        columns.add(dateOfBirthCol);

        // Create a GenericGUI
        GenericGUI<Student> genericGUI = new GenericGUI<>();

        // create the table
        TableView<Student> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Student");
        databaseConnection.connectionIsOpen();

        // puts the data from the resultset in an observablelist
        ObservableList<Student> data = genericGUI.getData(resultSet, Student.class);

        // displays data in the table
        table.setItems(data);

        table.setPrefWidth(450);

        VBox rightSide = new VBox(inputFields, buttons);

        rightSide.setPrefWidth(250);

        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        studentGUI.setScene(scene);

        // eventhandler for adding student
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

                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        // eventhandler for updating student
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
                        // refreshTable(databaseConnection, table);

                        if (selectedStudent != null) {
                            emailInput.setText(null);
                            nameInput.setText(null);
                            genderInput.setText(null);
                            dateOfBirthInput.setText(null);

                            refreshTable(data, table, genericGUI, databaseConnection);
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                });
            }
        });

        // eventhandler for deleting student
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
                    refreshTable(data, table, genericGUI, databaseConnection);

                    // refreshTable(databaseConnection, table);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });

        // eventhandler for button to return to menu
        backButton.setOnAction((event) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            try {
                codecademyGUI.start(codecademyStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // close current stage
            studentGUI.close();
        });

        // button to show students enrollments
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

                        studentGUI.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {

                // Error message that shows when user presses button for enrollments without
                // selecting a student
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Geen student geselecteerd");
                errorAlert.setContentText("Selecteer een student om deze optie te gebruiken");
                errorAlert.showAndWait();
            }

        });

    }

    // method for refreshing the table
    private void refreshTable(ObservableList<Student> data, TableView<Student> table, GenericGUI<Student> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Student");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, Student.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }
}
