/**
 * Author: Felix Guevara
 * Course: [CEN-3024C-13950]
 * Date: October 26, 2025,
 * Class: WildAnimal.java
 *
 * This class represents an individual WildAnimal in the tracking system
 * It provides accessor and mutator methods for each field.
 * This class is used by the AnimalManager to manage WildAnimal records.
 */
package WATSSwingApp;

public class WildAnimal {
    /**
     * Fields to store animal attributes
     */
    private int id;
    private String species;
    private String name;
    private int age;
    private String gender;
    private double weight;
    private String healthStatus;

    /**
     * Method: WildAnimal (Constructor)
     * Purpose: Initializes a new WildAnimal object with provided details.
     * Arguments: int id, String species, String name, int age, String gender, double weight
     * Return: None
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

    /**
     * Getter methods
     */
    public int getId() { return id; }

    public String getSpecies() { return species; }

    public String getName() { return name; }

    public int getAge() { return age; }

    public String getGender() { return gender; }

    public double getWeight() { return weight; }

    public String getHealthStatus() { return healthStatus; }

    /**
     * Setter methods
     */
    public void setSpecies(String species) { this.species = species; }

    public void setName(String name) { this.name = name; }

    public void setAge(int age) { this.age = age; }

    public void setGender(String gender) { this.gender = gender; }

    public void setWeight(double weight) { this.weight = weight; }

    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    /** Method: toString
     * Purpose: Returns a formatted string representation of the animal
     * Arguments: None
     * Return: String
     */
    @Override
    public String toString() {
        return String.format("ID: %d | Species: %s | Name: %s | Age: %d | Gender: %s | Weight: %.2f | Health: %s",
                id, species, name, age, gender, weight, healthStatus);
    }
}
