package gui;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import objects.Difficulty;
import objects.Gender;
import objects.Status;

public class GenericGUI<T> {

    public TableView<T> createTable(List<TableColumn<T, ?>> columns) {
        TableView<T> table = new TableView<>();
        table.getColumns().addAll(columns);
        return table;
    }

    public ObservableList<T> getData(ResultSet resultSet, Class<T> usedClass) throws SQLException {
        ObservableList<T> data = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                T object = usedClass.getDeclaredConstructor().newInstance();
                for (Field field : usedClass.getDeclaredFields()) {
                    String fieldName = field.getName();
                    if (fieldName.equals("serialVersionUID")) {
                        continue;
                    }
                    field.setAccessible(true);
                    Object value = resultSet.getObject(fieldName);

                    // If statements to check for enum types
                    if (field.getType() == Gender.class) {
                        value = Gender.fromString((String) value);
                    }

                    if (field.getType() == Status.class) {
                        value = Status.fromString((String) value);
                    }

                    if (field.getType() == Difficulty.class) {
                        value = Difficulty.fromString((String) value);
                    }

                    field.set(object, value);
                }
                data.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Data verzameld: " + data.toString());
        return data;
    }

    // method for refreshing the tables
    public void refreshTable(ObservableList<T> data, TableView<T> table) {
        table.setItems(data);
    }

    // method for swithing screens
    public void switchScreen(Stage currentStage, Stage newStage, Application newScreen) {
        try {
            newScreen.start(newStage);
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method for opening a popup window
    public void openPopupScreen(Stage popupStage, Application popupScreen) {
        try {
            popupScreen.start(popupStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
