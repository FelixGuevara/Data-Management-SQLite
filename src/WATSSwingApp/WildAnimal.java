/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: November 10, 2025,
 * Class: WildAnimal.java
 *
 * This class represents an individual WildAnimal in the tracking system
 * It provides accessor and mutator methods for each field.
 * This class is used by the DatabaseManager to manage WildAnimal records.
 */
package WATSSwingApp;

/**
 * Represents an individual wild animal in the tracking system.
 * <p>
 * This class stores attributes of a wild animal such as species, name, age, gender,
 * weight, and health status. It provides accessor (getter) and mutator (setter) methods
 * for each field. The {@code WildAnimal} class is primarily used by {@code DatabaseManager}
 * to manage animal records.
 * </p>
 *
 * @author Felix Guevara
 * @version 1.0
 * @since 2025-11-10
 */
public class WildAnimal {

    /** Unique identifier for the animal. */
    private int id;

    /** Species of the animal (e.g., Lion, Tiger). */
    private String species;

    /** Name assigned to the animal. */
    private String name;

    /** Age of the animal in years. */
    private int age;

    /** Gender of the animal (e.g., Male, Female). */
    private String gender;

    /** Weight of the animal in kilograms. */
    private double weight;

    /** Current health status of the animal (e.g., Healthy, Injured). */
    private String healthStatus;

    /**
     * Constructs a new {@code WildAnimal} with the specified details.
     *
     * @param id          unique identifier for the animal
     * @param species     species of the animal
     * @param name        name assigned to the animal
     * @param age         age of the animal in years
     * @param gender      gender of the animal
     * @param weight      weight of the animal in kilograms
     * @param healthStatus current health status of the animal
     */
    public WildAnimal(int id, String species, String name, int age, String gender, double weight, String healthStatus) {
        this.id = id;
        this.species = species;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.healthStatus = healthStatus;
    }

    /** @return the unique identifier of the animal */
    public int getId() { return id; }

    /** @return the species of the animal */
    public String getSpecies() { return species; }

    /** @return the name of the animal */
    public String getName() { return name; }

    /** @return the age of the animal in years */
    public int getAge() { return age; }

    /** @return the gender of the animal */
    public String getGender() { return gender; }

    /** @return the weight of the animal in kilograms */
    public double getWeight() { return weight; }

    /** @return the current health status of the animal */
    public String getHealthStatus() { return healthStatus; }

    /**
     * Updates the species of the animal.
     *
     * @param species new species value
     */
    public void setSpecies(String species) { this.species = species; }

    /**
     * Updates the name of the animal.
     *
     * @param name new name value
     */
    public void setName(String name) { this.name = name; }

    /**
     * Updates the age of the animal.
     *
     * @param age new age value in years
     */
    public void setAge(int age) { this.age = age; }

    /**
     * Updates the gender of the animal.
     *
     * @param gender new gender value
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Updates the weight of the animal.
     *
     * @param weight new weight value in kilograms
     */
    public void setWeight(double weight) { this.weight = weight; }

    /**
     * Updates the health status of the animal.
     *
     * @param healthStatus new health status value
     */
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    /**
     * Returns a formatted string representation of the animal's details.
     *
     * @return a string containing the animal's ID, species, name, age, gender, weight, and health status
     */
    @Override
    public String toString() {
        return String.format("ID: %d | Species: %s | Name: %s | Age: %d | Gender: %s | Weight: %.2f | Health: %s",
                id, species, name, age, gender, weight, healthStatus);
    }
}
