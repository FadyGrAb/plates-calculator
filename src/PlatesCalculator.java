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
}

public class PlatesCalculator {
  private Map<Plate, Integer> inventory = new HashMap<>();
  private Handle handle;

  public static void main(String[] args) {
    PlatesCalculator calc = new PlatesCalculator();
    boolean isAlive = true;
    String[] mainMenuItems = {
      "Build Inventory", "Add Handle", "Calculate", "Exit",
    };

    Utils.printWelcomeBanner();
    while (isAlive) {
      int mainMenuOption = Utils.showMenu("Plates Calculator", mainMenuItems, "Enter a choice number");

      switch (mainMenuOption) {
        // Build Inventory
        case 0 -> calc.buildInventory();
        // TODO Add Handle
        case 1 -> System.out.println(">>>>>>>" + mainMenuItems[1]);
        // TODO Calculate (Dynamic Programing)
        case 2 -> System.out.println(">>>>>>>" + mainMenuItems[2]);
        // Exit
        // TODO handle ctrl+c
        case 3 -> {
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
    // TODO complete add handle logic
    boolean isAlive = true;
    System.out.println("\n");
  }

  private void buildInventory() {
    boolean isAlive = true;
    String[] inventoryMenu = {
      "Add/Modify", "Remove", "View", "Done",
    };

    while (isAlive) {
      int userInput =
          Utils.showMenu(
              "Building plates inventory", inventoryMenu, "Enter a choice number");
      switch (userInput) {
        case 0:
          addPlatesToInventory();
          break;
        case 1:
          removePlatesFromInventory();
          break;
        case 2:
          printInventory();
          break;
        case 3:
          System.out.println("\nBuilding Plates inventory is completed");
          printInventory();
          isAlive = false;
          break;
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

  public void printInventory() {
    if (inventory.size() == 0) {
      System.out.println("Plates inventory is empty");
      return;
    }

    System.out.println();
    System.out.println("Current plates inventory:");
    for (Plate key : inventory.keySet()) {
      System.out.println(key + " x" + inventory.get(key));
    }
    System.out.println();
  }
}
