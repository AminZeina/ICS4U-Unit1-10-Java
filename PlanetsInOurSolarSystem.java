import java.text.DecimalFormat;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This program uses enums to list the planets, then calculates distance from a planet chosen
 * by the user.
 * 
 * @author Amin Zeina
 * @version 1.0
 * @since 2020-03-11
 */
 
public class PlanetsInOurSolarSystem {
  
  /**
  * This class is a planets enum. 
  */
  
  enum Planets {
    MERCURY(0.387),
    VENUS(0.722),
    EARTH(1.0),
    MARS(1.52),
    JUPITER(5.20),
    SATURN(9.58),
    URANUS(19.2),
    NEPTUNE(30.1);
    
    double distanceFromSun;
    Planets(double distance) {
      this.distanceFromSun = distance;
    }
    
    double getDistance() {
      return distanceFromSun;
    }
  }
  
  /**
  * This class Capitalizes the first letter of a string.
  */
  
  public static String capitalize(String stringSent) {
    // https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
    return stringSent.substring(0, 1).toUpperCase() + stringSent.substring(1).toLowerCase();
  }
  
  /**
  * This class gets user's planet, then determines the mew order of planets. 
  */
  
  public static void main(String[] args) {
    
    // Declare variables
    String userPlanet;
    String travelAgain;
    boolean programEnded = false;
    Planets[] planetsArray = Planets.values();

    
    // Create scanner objects
    Scanner userInput = new Scanner(System.in);
    // Creating formatters to round output 
    // https://www.baeldung.com/java-round-decimal-number
    DecimalFormat rounder = new DecimalFormat("###.###");
    DecimalFormat scientificRounder = new DecimalFormat("#.##E0");
    
    System.out.println("Here are the planets in our solar system from closest to furthest from " 
        + "the sun:");
    // Lists all planets
    for (Planets planets: planetsArray) {
      // Prints out list of planets, with first letter capitalized and the rest lowercase
      System.out.println(capitalize(planets.name()));
    }
    
    // Loop for user to travel again if they want to 
    while (programEnded == false) {
      // Creating map in loop so if user travels again, the map is clear.
      // https://docs.oracle.com/javase/8/docs/api/java/util/NavigableMap.html
      NavigableMap<Double, String> calculatedDistanceMap = new TreeMap<Double, String>();
      
      System.out.print("Enter the planet you want to travel to: ");
      
      try {
        userPlanet = userInput.nextLine().toUpperCase();
        // Trying to assign a value to variable of type Planets to checks if user input is in enum
        Planets currentPlanet = Planets.valueOf(userPlanet);
        
        // Calculate new distanced with respect to the chosen planet
        for (Planets planets: planetsArray) {
          // Calcutate absolute value of every planet - user's chosen planet to get the distance.
          calculatedDistanceMap.put(Math.abs(planets.getDistance() 
              - currentPlanet.getDistance()), planets.name());
        }
        
        // Tell user what planet they are on, capitalized so it's not ALL UPPERCASE, \n for visuals
        System.out.println("\nNow that you are on " + capitalize(userPlanet) 
            + ", here are the planets from closest to furthest: ");
        
        for (Double newDistance: calculatedDistanceMap.keySet()) {
          // Print out the planets in order, using if to exclude the current planet
          if (newDistance != 0.0) {
            System.out.println(capitalize(calculatedDistanceMap.get(newDistance)) + " is " 
                + rounder.format(newDistance) + " AU (or " 
                + scientificRounder.format(newDistance * 1.496E8) + " km) away.");
          } 
        }
        
        // Asks if user wants to go somewhere else
        System.out.print("Do you want to travel somewhere else? (yes/no): ");
        // Convert to lower case so any capitalization will be valid 
        travelAgain = userInput.nextLine().toLowerCase();
      
        if (travelAgain.equals("yes")) {
          // User wants to keep playing
          programEnded = false;
        } else {
          // User wants to end the game
          programEnded = true;
        }
      
      } catch (IllegalArgumentException e) {
        System.err.println("That is not a planet.");
      } 
     
    } // End While
    
    System.out.println("Program End.");
  } // End Main
}