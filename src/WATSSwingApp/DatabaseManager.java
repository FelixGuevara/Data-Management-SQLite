/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
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

/**
 * A utility class responsible for managing SQLite database operations for the Wildlife Animal Tracking System (WATS).
 * <p>
 * This class encapsulates the database file path and provides methods to establish a connection and perform
 * CRUD operations on the {@code WildAnimals} table. It promotes modularity, reusability, and simplifies
 * database access throughout the application.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class DatabaseManager {

    /** The file path of the SQLite database. */
    private final String dbPath;

    /**
     * Constructs a new {@code DatabaseManager} instance.
     *
     * @param dbPath the file path of the SQLite database
     */
    public DatabaseManager(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return a {@link Connection} object for interacting with the database
     * @throws SQLException if a database access error occurs
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    /**
     * Retrieves all wild animal records from the {@code WildAnimals} table.
     *
     * @return a {@link List} of {@link WildAnimal} objects representing all records in the database;
     *         returns an empty list if no records exist
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return animals;
    }

    /**
     * Retrieves a wild animal record by its Tag ID.
     *
     * @param id the Tag ID of the animal
     * @return a {@link WildAnimal} instance if found; {@code null} otherwise
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return animal;
    }

    /**
     * Inserts a new wild animal record into the database.
     *
     * @param animal the {@link WildAnimal} object to insert
     * @return {@code true} if the insertion was successful; {@code false} otherwise
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
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Updates an existing wild animal record in the database.
     *
     * @param animal the {@link WildAnimal} object containing updated details
     * @return {@code true} if the update was successful; {@code false} otherwise
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
            return affectedRows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Deletes a wild animal record from the database by its Tag ID.
     *
     * @param id the Tag ID of the animal to delete
     * @return {@code true} if the deletion was successful; {@code false} otherwise
     */
    public boolean deleteWildAnimal(int id) {
        String sql = "DELETE FROM WildAnimals WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
