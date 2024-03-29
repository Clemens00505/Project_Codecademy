package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Certificate;
import objects.Enrollment;

public class EnrollmentGUI extends Application {

    @Override
    public void start(Stage enrollmentStage) throws Exception {
        // create a GenericGUI
        GenericGUI<Enrollment> genericGUI = new GenericGUI<>();

        enrollmentStage.show();
        enrollmentStage.setTitle("Enrollments");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addEnrollmentButton = new Button("Enrollment toevoegen");
        Button editEnrollmentButton = new Button("Enrollment bewerken");
        Button deleteEnrollmentButton = new Button("Enrollment verwijderen");
        Button createCertificateButton = new Button("Certificaat maken");
        Button showModulesButton = new Button("Toon modules");
        Button backButton = new Button("Terug naar menu");

        // sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addEnrollmentButton.setMinWidth(equalWidth);
        editEnrollmentButton.setMinWidth(equalWidth);
        deleteEnrollmentButton.setMinWidth(equalWidth);
        createCertificateButton.setMinWidth(equalWidth);
        showModulesButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<Enrollment, String> studentMailCol = new TableColumn<>("Student E-mail");
        TableColumn<Enrollment, String> courseNameCol = new TableColumn<>("Cursusnaam");
        TableColumn<Enrollment, Integer> percentageCol = new TableColumn<>("Percentage");
        TableColumn<Enrollment, Date> enrollmentDateCol = new TableColumn<>("Inschrijfdatum");
        TableColumn<Enrollment, Integer> enrollmentIdCol = new TableColumn<>("Inschrijvings-ID");
        TableColumn<Enrollment, Integer> courseIdCol = new TableColumn<>("CursusId");
        TableColumn<Enrollment, Integer> certificateCreatedCol = new TableColumn<>("CertificateCreated");

        studentMailCol.setCellValueFactory(new PropertyValueFactory<>("studentMail"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        enrollmentDateCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        enrollmentIdCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        certificateCreatedCol.setCellValueFactory(new PropertyValueFactory<>("certificateCreated"));

        // add columns to a list
        List<TableColumn<Enrollment, ?>> columns = new ArrayList<>();
        columns.add(studentMailCol);
        columns.add(courseNameCol);
        columns.add(percentageCol);
        columns.add(enrollmentDateCol);

        // create the table
        TableView<Enrollment> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement(
                "SELECT Enrollment.CourseId, Enrollment.StudentMail, Enrollment.EnrollmentDate, Enrollment.Percentage, Enrollment.HasCertificate, Course.CourseName, Enrollment.EnrollmentId FROM Enrollment JOIN Course ON Enrollment.CourseId = Course.CourseId;");
        // puts the data from the resultset in an observablelist
        ObservableList<Enrollment> data = genericGUI.getData(resultSet, Enrollment.class);

        // displays data in the table
        table.setItems(data);

        // Gives the table and columns a good size on the screen
        table.setPrefWidth(950);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE);
        });

        // placing everything in the screen
        // put buttons in a vbox
        VBox buttons = new VBox(refreshButton, addEnrollmentButton, editEnrollmentButton, deleteEnrollmentButton, createCertificateButton, showModulesButton,
                backButton);

        buttons.setPrefWidth(200);
        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        enrollmentStage.setScene(scene);

        // eventhandler for adding enrollment
        addEnrollmentButton.setOnAction((addEnrollmentEvent) -> {
            try {
                AddEnrollmentGUI addEnrollmentGUI = new AddEnrollmentGUI();
                Stage addEnrollmentStage = new Stage();

                addEnrollmentStage.setTitle("Inschrijving toevoegen");
                genericGUI.openPopupScreen(addEnrollmentStage, addEnrollmentGUI);

            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        //eventhandler for opening EnrollmentModulesGUI
        showModulesButton.setOnAction((showModulesButtonEvent) -> {
            Enrollment enrollment = table.getSelectionModel().getSelectedItem();

            int enrollmentId = enrollment.getEnrollmentId();

            EnrollmentModulesGUI enrollmentModulesGUI = new EnrollmentModulesGUI(enrollmentId);
            Stage enrollmentModulesStage = new Stage();
            enrollmentModulesStage.setTitle("Modules bij inschrijving");

            genericGUI.switchScreen(enrollmentStage, enrollmentModulesStage, enrollmentModulesGUI);
        });

        // eventhandler for editing an enrollment
        editEnrollmentButton.setOnAction((editEnrollmentEvent) -> {
            Enrollment selectedEnrollment = table.getSelectionModel().getSelectedItem();

            if (selectedEnrollment != null) {
                String studentMail = selectedEnrollment.getStudentMail();
                String courseName = selectedEnrollment.getCourseName();
                int percentage = selectedEnrollment.getPercentage();
                Date enrollmentDate = selectedEnrollment.getEnrollmentDate();
                int enrollmentId = selectedEnrollment.getEnrollmentId();
                int courseId = selectedEnrollment.getCourseId();

                Enrollment enrollment = new Enrollment(studentMail, courseName, percentage, enrollmentDate,
                        enrollmentId, courseId);

                try {
                    EditEnrollmentGUI editEnrollmentGUI = new EditEnrollmentGUI(enrollment);
                    Stage editEnrollmentStage = new Stage();

                    editEnrollmentStage.setTitle("Inschrijving bewerken");
                    genericGUI.openPopupScreen(editEnrollmentStage, editEnrollmentGUI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        // eventhandler for deleting an enrollment
        deleteEnrollmentButton.setOnAction((deleteEnrollmentEvent) -> {
            Enrollment selectedEnrollment = table.getSelectionModel().getSelectedItem();

            try {
                selectedEnrollment.deleteEnrollment(selectedEnrollment, databaseConnection);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        createCertificateButton.setOnAction((createCertificateButtonEvent) -> {
            Enrollment enrollment = table.getSelectionModel().getSelectedItem();

            String studentMail = enrollment.getStudentMail();
            int enrollmentId = enrollment.getEnrollmentId();
            int hasCertificate = enrollment.getHasCertificate();
            int percentage = enrollment.getPercentage();

            //checks if certificate can be created
            if (percentage != 100) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Curusus niet voltooid");
                    errorAlert.setContentText("Haal 100% om een certificaat te geven");
                    errorAlert.showAndWait();
            } else if (hasCertificate != 0) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Certificaat al gemaakt");
                    errorAlert.setContentText("Er is al een certificaat voor deze inschrijving");
                    errorAlert.showAndWait();
            } else {
                //insert certificate into database
                Certificate certificate = new Certificate(studentMail, enrollmentId);
                certificate.insertIntoDatabase(certificate, databaseConnection);

                //sets hasCertificate to 1 so that we know that there is a certificate
                enrollment.setCertificateCreated(enrollment, databaseConnection);

                try {
                    refreshTable(data, table, genericGUI, databaseConnection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        // eventhandler for button to return to menu
        backButton.setOnAction((returnButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(enrollmentStage, codecademyStage, codecademyGUI);
        });
    }

    // method for refreshing the table
    private void refreshTable(ObservableList<Enrollment> data, TableView<Enrollment> table,
            GenericGUI<Enrollment> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement(
                "SELECT Enrollment.CourseId, Enrollment.StudentMail, Enrollment.EnrollmentDate, Enrollment.Percentage, Course.CourseName, Enrollment.EnrollmentId, Enrollment.HasCertificate\r\n"
                        + //
                        "FROM Enrollment\r\n" + //
                        "JOIN Course ON Enrollment.CourseId = Course.CourseId;");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, Enrollment.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }
}
