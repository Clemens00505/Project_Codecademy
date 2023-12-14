package GUI;

import Database.databaseConnection;
import Objects.Student;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentGUI extends Application {

    @Override
    public void start(Stage studentGUI) throws Exception {

        // Menu menuItem = new Menu("Studenten");

        // MenuItem addStudentMenu = new MenuItem("Student toevoegen");
        // MenuItem editStudentMenu = new MenuItem("Student bewerken");
        // MenuItem deleteStudentMenu = new MenuItem("Student verwijderen");

        // menuItem.getItems().add(addStudentMenu);
        // menuItem.getItems().add(editStudentMenu);
        // menuItem.getItems().add(deleteStudentMenu);

        // MenuBar menuBar = new MenuBar();
        // menuBar.getMenus().add(menuItem);

        databaseConnection databaseConnection = new databaseConnection();
        databaseConnection.openConnection();

        final ObservableList<Student> data = databaseConnection.getAllStudents();
        databaseConnection.closeConnection();
        
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

        // VBox box = new VBox(menuBar, table);

        Scene scene = new Scene(table);

        studentGUI.setScene(scene);


    }

}
