package DatabaseConnection;

import java.sql.*;

import javax.swing.text.AbstractDocument.Content;

/**
 * Dit is een voorbeeld Java toepassing waarin je verbinding maakt met een SQLServer database.
 */
public class databaseConnection {

    public static void main(String[] args) {

        // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien deze voor jou anders is.
        String connectionUrl = "jdbc:sqlserver://localhost;databaseName=codecademy;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

        // Connection beheert informatie over de connectie met de database.
        Connection connection = null;

        // Statement zorgt dat we een SQL query kunnen uitvoeren.
        Statement statement = null;

        // ResultSet is de tabel die we van de database terugkrijgen.
        // We kunnen door de rows heen stappen en iedere kolom lezen.
        ResultSet resultSet = null;

        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Maak de verbinding met de database.
            connection = DriverManager.getConnection(connectionUrl);

            // Stel een SQL query samen.
            String SQL = "SELECT * FROM Content";
            statement = connection.createStatement();
            // Voer de query uit op de database.
            resultSet = statement.executeQuery(SQL);

            // System.out.print(String.format("| %7s | %-32s | \n", " ", " ", " ").replace(" ", "-"));

            // Als de resultset waarden bevat dan lopen we hier door deze waarden en printen ze.
            while (resultSet.next()) {
                // Vraag per row de kolommen in die row op.
                int ContentId = resultSet.getInt("ContentId");
                String ContentType = resultSet.getString("ContentType");

                // Print de kolomwaarden.
                // System.out.println(ContentId + " " + ContentType);

                // Met 'format' kun je de string die je print het juiste formaat geven, als je dat wilt.
                // %d = decimal, %s = string, %-32s = string, links uitgelijnd, 32 characters breed.
                System.out.format("| %7d | %-10s | \n", ContentId, ContentType);
            }
            System.out.println(String.format("| %7s | %-14s | \n", " ", " ", " ").replace(" ", "-"));

        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }
}