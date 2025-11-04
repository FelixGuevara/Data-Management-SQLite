/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 31, 2025,
 * Class: DatabaseManager.java
 *
 * This is a utility class responsible for managing connections to the SQLite database
 * used in the Wildlife Animal Tracking System. It encapsulates the database file path and provides
 * a method to establish a connection, enabling other components of the application to perform
 * SQL operations without directly handling connection logic.
 * This promotes modularity, reusability, and simplifies database access throughout the application.
 */
package WATSSwingApp;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final String dbPath;

    public DatabaseManager(String dbPath) {
        this.dbPath = dbPath;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    /**
     * Method: getAllWildAnimalRecords
     * Purpose: Retrieves all wild animal records from the WildAnimals table in the database.
     * Arguments: None
     * Return: A List of WildAnimal objects representing all records in the database.
     *         If no records exist, returns an empty list.
     */
    public List<WildAnimal> getAllWildAnimalRecords() {
        String sql = "SELECT * FROM WildAnimals";
        List<WildAnimal> animals = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                WildAnimal animal = new WildAnimal(
                        rs.getInt("id"),
                        rs.getString("species"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getDouble("weight"),
                        rs.getString("healthStatus")
                );
                animals.add(animal);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        return animals;
    }

    /**
     * Method: getWildAnimalById
     * Purpose: Gets an animal record by Tag ID.
     * Arguments: int id - the tag id for the selected record
     * Return: WildAnimal instance if success, null object otherwise
     */
    public WildAnimal getWildAnimalById(int id) {
        String sql = "SELECT * FROM WildAnimals WHERE id = ?";
        WildAnimal animal = null;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                animal = new WildAnimal(
                        rs.getInt("id"),
                        rs.getString("species"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getDouble("weight"),
                        rs.getString("healthStatus")
                );
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        // null if not found
        return animal;
    }

    /**
     * Method: insertWildAnimal
     * Purpose: Inserts a new animal record.
     * Arguments: WildAnimal anima - the animal record to be added
     * Return: boolean - true if success, false otherwise
     */
    public boolean insertWildAnimal(WildAnimal animal) {
        String sql = "INSERT INTO WildAnimals (id, species, name, age, gender, weight, healthStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getId());
            stmt.setString(2, animal.getSpecies());
            stmt.setString(3, animal.getName());
            stmt.setInt(4, animal.getAge());
            stmt.setString(5, animal.getGender());
            stmt.setDouble(6, animal.getWeight());
            stmt.setString(7, animal.getHealthStatus());

            stmt.executeUpdate();
            return true; // success
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false; // failure
        }
    }

    /**
     * Method: updateWildAnimal
     * Purpose: Updates an existing animal's details by Tag ID.
     * Arguments: WildAnimal animal - animal record to be updated
     * Return: boolean - true if success, false otherwise
     */
    public boolean updateWildAnimal(WildAnimal animal) {
        String sql = "UPDATE WildAnimals SET species = ?, name = ?, age = ?, gender = ?, weight = ?, healthStatus = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getSpecies());
            stmt.setString(2, animal.getName());
            stmt.setInt(3, animal.getAge());
            stmt.setString(4, animal.getGender());
            stmt.setDouble(5, animal.getWeight());
            stmt.setString(6, animal.getHealthStatus());
            stmt.setInt(7, animal.getId());

            int affectedRows = stmt.executeUpdate();

            // true if update succeeded
            return affectedRows > 0;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false; // failure
        }
    }

    /**
     * Method: deleteWildAnimal
     * Purpose: Deletes an animal by Tag ID.
     * Arguments: int id - the tag id for the record to be deleted
     * Return: boolean - true if success, false otherwise
     */
    public boolean deleteWildAnimal(int id) {
        String sql = "DELETE FROM WildAnimals WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            // true if record deleted
            return affectedRows > 0;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false; // failure
        }
    }
}
