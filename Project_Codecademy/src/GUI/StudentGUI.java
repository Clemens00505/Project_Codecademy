package GUI;

import java.sql.Date;
import java.sql.SQLException;

import Database.databaseConnection;
import Objects.Student;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

        //labels voor inputvelden
        Label emailLabel = new Label("Email: ");
        Label nameLabel = new Label("Naam: ");
        Label genderLabel = new Label("Gender: ");
        Label dateOfBirthLabel = new Label("Geboortedatum: ");
       
        //textfields voor het invoeren van een nieuwe student
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        TextField genderInput = new TextField();
        TextField dateOfBirthInput = new TextField();

        //prompttext maken voor de inputvelden als voorbeeld van een goede input
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        genderInput.setPromptText("man/vrouw/anders");
        dateOfBirthInput.setPromptText("yyyy-mm-dd");

        //alles wat te maken heeft met input in een VBox zetten
        VBox inputFields = new VBox(emailLabel, emailInput, nameLabel, nameInput, genderLabel, genderInput, dateOfBirthLabel, dateOfBirthInput);

        
        //buttons maken voor verschillende acties
        Button addStudentButton = new Button("Student toevoegen");
        Button editStudentButton = new Button("Student bewerken");
        Button deleteStudentButton = new Button("Student verwijderen");

        //buttons in een VBox zetten
        VBox buttons = new VBox(addStudentButton, editStudentButton, deleteStudentButton);

        databaseConnection databaseConnection = new databaseConnection();
        databaseConnection.openConnection();

        ObservableList<Student> data = databaseConnection.getAllStudents();
        
        studentGUI.show();
        studentGUI.setTitle("Studenten");
        
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

        VBox rightSide = new VBox(inputFields, buttons);

        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        studentGUI.setScene(scene);

        addStudentButton.setOnAction((event) -> {
            try {
                databaseConnection.openConnection();
                System.out.println(databaseConnection.connectionIsOpen());

                String email = emailInput.getText();
                String name = nameInput.getText();
                String gender = genderInput.getText();
                String dateOfBirthString = dateOfBirthInput.getText();
                Date dateOfBirth = Date.valueOf(dateOfBirthString);

                Student student = new Student(email, name, gender, dateOfBirth);

                databaseConnection.addStudent(student);
                System.out.println(student);

                table.refresh();
                studentGUI.hide();

                studentGUI.show();
                

                
                // databaseConnection.closeConnection();


            } catch (Exception e) {
                System.out.println(e);
            }
        });


    }


}
