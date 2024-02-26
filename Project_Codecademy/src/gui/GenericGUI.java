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
import objects.Gender;
import objects.Status;

public class GenericGUI<T> {

    // public static <T> TableView<T> createTable(List<TableColumn<T, ?>> columns) {
    //     TableView<T> table = new TableView<>();
    //     table.getColumns().addAll(columns);
    //     return table;
    // }

    // public static <T> ObservableList<T> getData(ResultSet resultSet, Class<T> type) throws SQLException {
    //     ObservableList<T> data = FXCollections.observableArrayList();

    //     try {
    //         while (resultSet.next()) {
    //             T instance = type.getDeclaredConstructor().newInstance();
    //             setDataFromResultSet(instance, resultSet);
    //             data.add(instance);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     return data;
    // }

    // // Method to set data from ResultSet to object
    // private static <T> void setDataFromResultSet(T instance, ResultSet resultSet) {
    //     try {
    //         for (Field field : instance.getClass().getDeclaredFields()) {
    //             field.setAccessible(true);
    //             String fieldName = field.getName();
    //             Class<?> fieldType = field.getType();
    //             if (fieldType.isEnum()) {
    //                 Object value = fieldType.getMethod("fromString", String.class).invoke(null, resultSet.getString(fieldName));
    //                 field.set(instance, value);
    //             } else {
    //                 field.set(instance, resultSet.getObject(fieldName));
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

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

                    if (field.getType() == Gender.class) { 
                        // If field type is Gender enum, convert text to Gender enum
                        value = Gender.fromString((String) value);
                    }

                    if (field.getType() == Status.class) {
                        value = Status.fromString((String) value);
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

    // public ObservableList<T> getData(ResultSet resultSet, Class<T> usedClass) throws SQLException {
    //     ObservableList<T> data = FXCollections.observableArrayList();
    //     try {
    //         while (resultSet.next()) {
    //             T object;
    //             // Check if the class is not java.lang.Module before attempting to create an instance
    //             if (!usedClass.getName().equals("java.lang.Module")) {
    //                 object = usedClass.getDeclaredConstructor().newInstance();
    //                 for (Field field : usedClass.getDeclaredFields()) {
    //                     String fieldName = field.getName();
    //                     if (fieldName.equals("serialVersionUID")) {
    //                         continue;
    //                     }
    //                     field.setAccessible(true);
    //                     Object value = resultSet.getObject(fieldName);
    
    //                     if (field.getType() == Gender.class) { 
    //                         // If field type is Gender enum, convert text to Gender enum
    //                         value = Gender.fromString((String) value);
    //                     }
    
    //                     if (field.getType() == Status.class) {
    //                         value = Status.fromString((String) value);
    //                     }
    
    //                     field.set(object, value);
    //                 }
    //                 data.add(object);
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     System.out.println("Data verzameld: " + data.toString());
    //     return data;
    // }

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
