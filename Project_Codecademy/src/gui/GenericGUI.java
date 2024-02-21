package gui;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GenericGUI<T> {
    
    public TableView<T> createTable(List<TableColumn<T, ?>> columns) {
        TableView<T> table = new TableView<>();
        table.getColumns().addAll(columns);
        return table;
    }


    //method to get data from 
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
}
