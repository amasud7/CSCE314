import java.util.ArrayList;
import java.util.List;

// Ayad Masud 4/3/25

// Define the Planet interface
interface Planet {
    // --ToDo Complete this
    double getDistanceFromSun();  // in million km
    double getDiameter();         // in km
    double getRevolutionsPerEarthYear(); // 1 for Earth, different for others
    String getColor();            // Return the planet's main color
    void printInfo();              // Prints all details about the planet
}

// Implement the Earth class
class Earth implements Planet {
    // -- TODo  Implement this.
    @Override
    public double getDistanceFromSun() {
        return 149.6;
    }

    @Override
    public double getDiameter() {
        return 12742;
    }

    @Override
    public double getRevolutionsPerEarthYear() {
        return 1;
    }

    @Override
    public String getColor() {
        return "Blue and Green";
    }

    @Override
    public void printInfo() {
        System.out.println("Planet: Earth");
        System.out.println("Distance from Sun: " + getDistanceFromSun() + " million km");
        System.out.println("Diameter: " + getDiameter() + " km");
        System.out.println("Revolutions per Earth Year " + getRevolutionsPerEarthYear());
        System.out.println("Color: " + getColor());
    }
}

// Implement the Mars class
class Mars implements Planet {
//    -- ToDo  Implement this and other planets. 
    @Override
    public double getDistanceFromSun() {
        return 227.9;
    }

    @Override
    public double getDiameter() {
        return 6779;
    }

    @Override
    public double getRevolutionsPerEarthYear() {
        return 1.88;
    }

    @Override
    public String getColor() {
        return "Reddish";
    }

    @Override
    public void printInfo() {
        System.out.println("Planet: Mars");
        System.out.println("Distance from Sun: " + getDistanceFromSun() + " million km");
        System.out.println("Diameter: " + getDiameter() + " km");
        System.out.println("Revolutions per Earth Year " + getRevolutionsPerEarthYear());
        System.out.println("Color: " + getColor());
    }
}

class Saturn implements Planet {
    @Override
    public double getDistanceFromSun() {
        return 1433.5;
    }

    @Override
    public double getDiameter() {
        return 116460;
    }

    @Override
    public double getRevolutionsPerEarthYear() {
        return 29.46;
    }

    @Override
    public String getColor() {
        return "Yellowish";
    }

    @Override
    public void printInfo() {
        System.out.println("Planet: Saturn");
        System.out.println("Distance from Sun: " + getDistanceFromSun() + " million km");
        System.out.println("Diameter: " + getDiameter() + " km");
        System.out.println("Revolutions per Earth Year " + getRevolutionsPerEarthYear());
        System.out.println("Color: " + getColor());
    }
}


// SolarSystem class to manage planets
class SolarSystem {
    private List<Planet> planets = new ArrayList<>();
    
    public void addPlanet(Planet planet) {
        planets.add(planet);
    }
    
    public void printAllPlanets() {
        System.out.println("Welcome to the Solar System!\n");
        for (Planet planet : planets) {
            planet.printInfo();
            System.out.println("\n");
        }
    }
}

// Main class to run the program
public class SolarSystemSimulation {
    public static void main(String[] args) {
        SolarSystem solarSystem = new SolarSystem();
        
        // Add planets
        // --ToDo add your 3 or more planets
        solarSystem.addPlanet(new Earth());
        solarSystem.addPlanet(new Mars());
        solarSystem.addPlanet(new Saturn());

        // Print all planet details
        // --ToDo  Call a method to print all planets.
        solarSystem.printAllPlanets();
    }
}