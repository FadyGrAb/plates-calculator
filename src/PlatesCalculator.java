import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class GymEquipment {
  private final Double weight;

  public GymEquipment(int weight) {
    this.weight = (double) weight;
  }

  public GymEquipment(double weight) {
    this.weight = weight;
  }

  public Double getWeight() {
    return weight;
  }
}

class Plate extends GymEquipment {
  public Plate(double weight) {
    super(weight);
  }

  public static <T extends Number> Plate createNew(T weight) {
    return new Plate((double) weight);
  }

  public String toString() {
    return "Plate -> " + getWeight() + " Kg";
  }

  public boolean equals(Object o) {
    Plate other = (Plate) o;
    return getWeight().doubleValue() == other.getWeight().doubleValue();
  }

  public int hashCode() {
    return Double.hashCode(getWeight());
  }
}

enum HandleType {
  BARBELL,
  DUMBBELL,
}

class Handle extends GymEquipment {
  private final Double maxPlatesCountOneSided;
  private List<Plate> plates = new ArrayList<>();
  private final HandleType type;

  Handle(HandleType type, double weight, double maxPlates) {
    super(weight);
    maxPlatesCountOneSided = maxPlates;
    this.type = type;
  }

  public static <T extends Number> Handle createNew(HandleType type, T weight, T maxPlates) {
    return new Handle(type, weight.doubleValue(), maxPlates.doubleValue());
  }

  public void addPlate(Plate plate) throws MaxPlatesReachedException {
    if (plates.size() + 1 > maxPlatesCountOneSided) {
      throw new MaxPlatesReachedException(
          "Can't add more plates. You have now " + plates.size() * 2 + " plates.");
    }

    plates.add(plate);
  }

  public double getTotalWeigth() {
    double platesWeights = 0;
    for (Plate plate : plates) {
      platesWeights += plate.getWeight();
    }

    return getWeight() + platesWeights * 2;
  }

  public void detailWeight() {
    System.out.println("Own weigth: " + getWeight() + " Kg");
    System.out.println("Max plates count: " + maxPlatesCountOneSided);
    for (Plate plate : plates) {
      System.out.println(plate + " x2");
    }
    System.out.println("Total weight: " + getTotalWeigth() + " Kg");
  }

  public String toString() {
    return "Handle -> "
        + type
        + " "
        + getWeight()
        + " Kg, Max one sided plates count: "
        + maxPlatesCountOneSided;
  }
}

public class PlatesCalculator {
  private Map<Plate, Integer> inventory = new HashMap<>();
  private Handle handle;

  public static void main(String[] args) {
    // Adding Shutdown Hook
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("\nAborting...");
      System.out.flush();
    }));

    PlatesCalculator calc = new PlatesCalculator();
    boolean isAlive = true;
    String[] mainMenuItems = {
      "Build Plates Inventory", "Add Handle", "View Gear", "Calculate", "Exit",
    };

    while (isAlive) {
      Utils.clearScreen();
      System.out.println(
          """
            _______  _        _______ _________ _______  _______    _______  _______  _        _______           _        _______ _________ _______  _______
           (  ____ )( \\      (  ___  )\\__   __/(  ____ \\(  ____ \\  (  ____ \\(  ___  )( \\      (  ____ \\|\\     /|( \\      (  ___  )\\__   __/(  ___  )(  ____ )
           | (    )|| (      | (   ) |   ) (   | (    \\/| (    \\/  | (    \\/| (   ) || (      | (    \\/| )   ( || (      | (   ) |   ) (   | (   ) || (    )|
           | (____)|| |      | (___) |   | |   | (__    | (_____   | |      | (___) || |      | |      | |   | || |      | (___) |   | |   | |   | || (____)|
           |  _____)| |      |  ___  |   | |   |  __)   (_____  )  | |      |  ___  || |      | |      | |   | || |      |  ___  |   | |   | |   | ||     __)
           | (      | |      | (   ) |   | |   | (            ) |  | |      | (   ) || |      | |      | |   | || |      | (   ) |   | |   | |   | || (\\ (
           | )      | (____/\\| )   ( |   | |   | (____/\\/\\____) |  | (____/\\| )   ( || (____/\\| (____/\\| (___) || (____/\\| )   ( |   | |   | (___) || ) \\ \\__
           |/       (_______/|/     \\|   )_(   (_______/\\_______)  (_______/|/     \\|(_______/(_______/(_______)(_______/|/     \\|   )_(   (_______)|/   \\__/
          """);

      int mainMenuOption =
          Utils.showMenu("Plates Calculator", mainMenuItems, "Enter a choice number");

      switch (mainMenuOption) {
        // Build Inventory
        case 0 -> calc.buildInventory();
        // Add Handle
        case 1 -> calc.addHandle();
        // View Gear
        case 2 -> calc.viewGear();
        // TODO Calculate (Dynamic Programing)
        case 3 -> System.out.println(">>>>>>>" + mainMenuItems[2]);
        // Exit
        case 4 -> {
          System.out.println("Exiting...");
          isAlive = false;
        }
      }
    }
  }

  public void setHandle(HandleType type, double ownWeight, double maxPlatesCount) {
    handle = new Handle(type, ownWeight, maxPlatesCount);
  }

  public Handle getHandle() {
    return handle;
  }

  private void addHandle() {
    boolean isAlive = true;

    Utils.clearScreen();
    System.out.println(
        """
            _____   _   _                       _____         _       _ _               ____            _   _       _ _
           |  _  |_| |_| |   _ _ ___ _ _ ___   | __  |___ ___| |_ ___| | |   ___ ___   |    \\ _ _ _____| |_| |_ ___| | |
           |     | . | . |  | | | . | | |  _|  | __ -| .'|  _| . | -_| | |  | . |  _|  |  |  | | |     | . | . | -_| | |
           |__|__|___|___|  |_  |___|___|_|    |_____|__,|_| |___|___|_|_|  |___|_|    |____/|___|_|_|_|___|___|___|_|_|
                            |___|
        """);

    HandleType handleType = HandleType.BARBELL;
    String[] handleTypeMenu = {"Barbell", "Dumbbell"};
    int userInput =
        Utils.showMenu(
            "What's your handle type?\nA Dumbbell will take twice as the Barbell plates.",
            handleTypeMenu,
            "Choose a type");

    if (userInput == 1) {
      handleType = HandleType.DUMBBELL;
    }

    double handleWeight = -1.0;
    while (isAlive) {
      System.out.print("\nSpecify how much your handle weighs in Kg: ");
      try {
        String weight = Utils.getStdin();
        handleWeight = Double.parseDouble(weight.strip());
        if (handleWeight <= 0) {
          System.out.println("[ERROR] Enter a value greater than or equal 1");
          continue;
        }

        isAlive = false;
      } catch (NumberFormatException e) {
        System.out.println("[ERROR] This isn't a valid number.");
      }
    }

    double handleMaxPlates = -1.0;
    isAlive = true;
    while (isAlive) {
      System.out.print("\nSpecify the max plates number you can fit one side of your handle: ");
      try {
        String maxPlates = Utils.getStdin();
        handleMaxPlates = Double.parseDouble(maxPlates.strip());
        if (handleMaxPlates <= 0) {
          System.out.println("[ERROR] Enter a value greater than or equal 1");
          continue;
        }
        isAlive = false;
      } catch (NumberFormatException e) {
        System.out.println("[ERROR] This isn't a valid number.");
      }
    }

    handle = new Handle(handleType, handleWeight, handleMaxPlates);

    System.out.println("Your handle is added: " + handle);
    Utils.pauseScreen("Press ENTER to continue...");
  }

  private void viewGear() {
    Utils.clearScreen();
    System.out.println(
        """
          __ __                _____
         |  |  |___ _ _ ___   |   __|___ ___ ___
         |_   _| . | | |  _|  |  |  | -_| .'|  _|
           |_| |___|___|_|    |_____|___|__,|_|

        """);
    printInventory(false);
    System.out.println("\nHandle:");
    if (handle == null) {
      System.out.println("Handle isn't set yet");
    } else {
      System.out.println(handle);
    }
    Utils.pauseScreen("Press ENTER to continue...");
  }

  private void buildInventory() {
    boolean isAlive = true;
    String[] inventoryMenu = {
      "Add/Modify", "Remove", "View", "Done",
    };

    while (isAlive) {
      Utils.clearScreen();
      System.out.println(
          """
              _____     _ _   _    _____ _     _              _____                 _
             | __  |_ _|_| |_| |  |  _  | |___| |_ ___ ___   |     |___ _ _ ___ ___| |_ ___ ___ _ _
             | __ -| | | | | . |  |   __| | .'|  _| -_|_ -|  |-   -|   | | | -_|   |  _| . |  _| | |
             |_____|___|_|_|___|  |__|  |_|__,|_| |___|___|  |_____|_|_|\\_/|___|_|_|_| |___|_| |_  |
                                                                                               |___|
          """);
      int userInput =
          Utils.showMenu("Building plates inventory", inventoryMenu, "Enter a choice number");
      switch (userInput) {
        // Add/Modify plates
        case 0 -> addPlatesToInventory();
        // Remove
        case 1 -> removePlatesFromInventory();
        // View
        case 2 -> printInventory(true);
        // Done
        case 3 -> {
          System.out.print("\nBuilding Plates inventory is completed.");
          printInventory(true);
          isAlive = false;
        }
      }
    }
  }

  private void addPlatesToInventory() {
    boolean isAlive = true;
    System.out.println("\nAdd or modify plates. Type 'done' to finish.");
    
    while (isAlive) {
      System.out.print("\tNew/modify plate (weight Kg,count): ");
      String platesInput = Utils.getStdin();

      if (!platesInput.toLowerCase().equals("done")) {
        try {
          double[] plateEntry = Utils.parsePlatesEntry(platesInput);
          Plate newPlate = new Plate(plateEntry[0]);
          Integer plateCount = (int) plateEntry[1];
          inventory.put(newPlate, plateCount);
          System.out.println("\tAdded: " + newPlate + " " + "x" + plateCount + "\n");
        } catch (InvalidInputException e) {
          System.out.println("\t[ERROR] " + e.getMessage());
          System.out.println(
              "\tInput should be comma seperated pair of PlateWeight(Kg),Count.\n"
                  + "\tExample: 5,2 // Two 5 Kg plates\n");
        }
      } else {
        isAlive = false;
      }
    }
  }

  private void removePlatesFromInventory() {
    boolean isAlive = true;
    System.out.println("\nType the plate weight in Kg to remove. Type 'done' to finish.");

    while (isAlive) {
      System.out.print("\tWeight in Kg: ");
      String userInput = Utils.getStdin();

      if (!userInput.toLowerCase().equals("done")) {
        try {
          Plate plateToRemove = new Plate(Double.parseDouble(userInput.strip()));
          if (inventory.remove(plateToRemove) == null) {
            System.out.println("\t[WARNING] You don't have this plate in your inventory!\n");
          } else {
            System.out.println("\t" + plateToRemove + " is removed\n");
          }
        } catch (NumberFormatException e) {
          System.out.println("\t[ERROR] This ins't a valid number\n");
        }
      } else {
        isAlive = false;
      }
    }
  }

  public Map<Plate, Integer> getInventory() {
    return inventory;
  }

  public void printInventory(boolean pause) {
    if (inventory.size() == 0) {
      System.out.println("Plates inventory is empty");
      return;
    }

    System.out.println("Current plates inventory:");
    for (Plate key : inventory.keySet()) {
      System.out.println(key + " x" + inventory.get(key));
    }
    if (pause) {
      Utils.pauseScreen("Press ENTER to conitnue...");
    }
  }
}
