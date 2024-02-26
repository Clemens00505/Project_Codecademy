package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import objects.Gender;
import objects.Student;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentGUI extends Application {

    @Override
    public void start(Stage studentGUI) throws Exception {
        // Create a GenericGUI
        GenericGUI<Student> genericGUI = new GenericGUI<>();

        studentGUI.show();
        studentGUI.setTitle("Studenten");

        // Create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addStudentButton = new Button("Student toevoegen");
        Button editStudentButton = new Button("Student bewerken");
        Button deleteStudentButton = new Button("Student verwijderen");
        Button confirmButton = new Button("Aanpassing bevestigen");
        Button backButton = new Button("Terug naar hoofdmenu");

        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addStudentButton.setMinWidth(equalWidth);
        editStudentButton.setMinWidth(equalWidth);
        deleteStudentButton.setMinWidth(equalWidth);
        confirmButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create columns for the table
        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        TableColumn<Student, String> nameCol = new TableColumn<>("Naam");
        TableColumn<Student, Gender> genderCol = new TableColumn<>("Gender");
        TableColumn<Student, Date> dateOfBirthCol = new TableColumn<>("Geboortedatum");
        TableColumn<Student, String> postalCodeCol = new TableColumn<>("Postcode");
        TableColumn<Student, Integer> houseNumberCol = new TableColumn<>("Huisnummer");
        TableColumn<Student, String> cityCol = new TableColumn<>("Woonplaats");
        TableColumn<Student, String> countryCol = new TableColumn<>("Land");

        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        houseNumberCol.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        // add columns to a list
        List<TableColumn<Student, ?>> columns = new ArrayList<>();
        columns.add(emailCol);
        columns.add(nameCol);
        columns.add(genderCol);
        columns.add(dateOfBirthCol);
        columns.add(postalCodeCol);
        columns.add(houseNumberCol);
        columns.add(cityCol);
        columns.add(countryCol);

        // create the table
        TableView<Student> table = genericGUI.createTable(columns);


        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Student");

        // puts the data from the resultset in an observablelist
        ObservableList<Student> data = genericGUI.getData(resultSet, Student.class);

        // displays data in the table
        table.setItems(data);


        //Gives the table and columns a good size on the screen
        table.setPrefWidth(950);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE); 
        });

        // placing everything in the screen
        // put buttons in a vbox
        VBox buttons = new VBox(refreshButton, addStudentButton, editStudentButton, deleteStudentButton, confirmButton, backButton);

        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        studentGUI.setScene(scene);

        // eventhandler for adding student
        addStudentButton.setOnAction((addStudentEvent) -> {
            try {
                AddStudentGUI addStudentGUI = new AddStudentGUI();
                Stage addStudentStage = new Stage();

                addStudentStage.setTitle("Student toevoegen");
                genericGUI.openPopupScreen(addStudentStage, addStudentGUI);

            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        // eventhandler for updating student
        editStudentButton.setOnAction((editStudentEvent) -> {
            Student selectedStudent = table.getSelectionModel().getSelectedItem();

            if (selectedStudent != null) {
                String email = selectedStudent.getEmail();
                String name = selectedStudent.getName();
                Gender gender = selectedStudent.getGender();
                Date dateOfBirth = selectedStudent.getDateOfBirth();
                String postalCode = selectedStudent.getPostalCode();
                Integer houseNumber = selectedStudent.getHouseNumber();
                String city = selectedStudent.getCity();
                String country = selectedStudent.getCountry();

                Student student = new Student(email, name, gender, dateOfBirth, postalCode, houseNumber, city, country);

                try {
                    EditStudentGUI editStudentGUI = new EditStudentGUI(student);
                    Stage editStudentStage = new Stage();

                    editStudentStage.setTitle("Student bewerken");
                    genericGUI.openPopupScreen(editStudentStage, editStudentGUI);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                
            }
        });

        // eventhandler for deleting student
        deleteStudentButton.setOnAction((deleteStudentEvent) -> {
            Student selectedStudent = table.getSelectionModel().getSelectedItem();

            if (selectedStudent != null) {
                try {
                    String email = selectedStudent.getEmail();
                    String name = selectedStudent.getName();
                    Gender gender = selectedStudent.getGender();
                    Date dateOfBirth = selectedStudent.getDateOfBirth();
                    String postalCode = selectedStudent.getPostalCode();
                    Integer houseNumber = selectedStudent.getHouseNumber();
                    String city = selectedStudent.getCity();
                    String country = selectedStudent.getCountry();

                    Student student = new Student(email, name, gender, dateOfBirth, postalCode, houseNumber, city, country);

                    student.deleteStudent(email, databaseConnection);
                    refreshTable(data, table, genericGUI, databaseConnection);

                    refreshTable(data, table, genericGUI, databaseConnection);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // eventhandler for button to return to menu
        backButton.setOnAction((returnButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(studentGUI, codecademyStage, codecademyGUI);
        });


        //eventhandler for refreshbutton
        refreshButton.setOnAction((refreshTableEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
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
