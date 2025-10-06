import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;


abstract class GymEquipment {
	private final Double WEIGHT;
	
	public GymEquipment(int weight) {
		WEIGHT = (double) weight;
	}
	
	public GymEquipment(double weight) {
		WEIGHT = weight;
	}

	public Double getWeight() {
		return WEIGHT;
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
		return "Plate: " + getWeight() + " Kg";
	}

	public boolean equals(Object o) {
		Plate other = (Plate) o;
		return getWeight().doubleValue() == other.getWeight().doubleValue();
	}

	public int hashCode(){
		return Double.hashCode(getWeight());
	}
}

class Handle extends GymEquipment {
	private final Double MAX_PlATES_COUNT_ONE_SIDE;
	private List<Plate> plates = new ArrayList<>();

	Handle(double weight, double maxPlates) {
		super(weight);
		MAX_PlATES_COUNT_ONE_SIDE = maxPlates;
	}

	public static <T extends Number> Handle createNew(T weight, T maxPlates) {
		return new Handle(weight.doubleValue(), maxPlates.doubleValue());
	}

	public void addPlate(Plate plate) throws MaxPlatesReachedException {
		if (plates.size() + 1 > MAX_PlATES_COUNT_ONE_SIDE) {
			throw new MaxPlatesReachedException("Can't add more plates. You have now " + plates.size() * 2 + " plates.");
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
		System.out.println("Max plates count: " + MAX_PlATES_COUNT_ONE_SIDE);
		for (Plate plate : plates) {
			System.out.println(plate + " x2");
		}
		System.out.println("Total weight: " + getTotalWeigth() + " Kg");
	}

}
	
public class PlatesCalculator {
	// showBuildInventory:
	// 	[1] Add/Modify
	// 	[2] Remove
	// 	[3] Done
	private Map<Plate, Integer> inventory = new HashMap<>();
	private Handle handle;

	public static void main(String[] args){
		PlatesCalculator calc = new PlatesCalculator();
		boolean isAlive = true;
		String[] mainMenuItems = {
			"Build Inventory",
			"Add Handle",
			"Calculate",
			"Exit",
		};

		Utils.printWelcomeBanner();
		while (isAlive) {
			int mainMenuOption = Utils.showMenu("Choose an option to begin", mainMenuItems, "Choose an option");

			switch (mainMenuOption) {
				case 0:
					calc.buildInventory();
					break;
				case 1:
					System.out.println(">>>>>>>" + mainMenuItems[1]);
					break;
				case 2:
					System.out.println(">>>>>>>" + mainMenuItems[2]);
					break;
				case 3:
					System.out.println("Exiting...");
					isAlive = false;
					break;
			}			
		}
	}

	public void setHandle(double ownWeight, double maxPlatesCount) {
		handle = new Handle(ownWeight, maxPlatesCount);
	}

	public Handle getHandle() {
		return handle;
	}

	private void buildInventory() {
		boolean isAlive = true;
		String[] inventoryMenu = {
			"Add/Modify",
			"Remove",
			"Done",
		};

		while(isAlive){
			int userInput = Utils.showMenu("Building plates inventory", inventoryMenu, "Choose an option to continue");
			System.out.print("New plate (weight Kg, count): ");
			try {
				String platesInput = Utils.getStdin();
				double[] plateEntry = Utils.parsePlatesEntry(platesInput);
				Plate newPlate = new Plate(plateEntry[0]);
				Integer plateCount = (int) plateEntry[1];
				addPlateToInventory(newPlate, plateCount); 

				System.out.println("\nCurrent Inventory:");
				printInventory();
				System.out.println();

			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
				System.out.println("Input should be comma seperated pair of PlateWeight(Kg),Count.\nExample: 5,2 // Two 5 Kg plates");
			}
		}
	}

	private void addPlateToInventory(Plate plate, int count) {
		inventory.put(plate, count);
	}

	public Map<Plate, Integer> getInventory() {
		return inventory;
	}

	public void printInventory() {
		for (Plate key: inventory.keySet()) {
			System.out.println(key + " x" + inventory.get(key));
		}
	}

}
