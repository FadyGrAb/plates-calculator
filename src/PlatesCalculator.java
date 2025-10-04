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
	
class HandleTestDrive {
	public static void main(String[] args) {
		Handle barbell = Handle.createNew(6.5, 4);
		try {
			System.out.print("Adding 10 Kg plate ... ");
			barbell.addPlate(new Plate(10));
			System.out.println("OK");
			
			System.out.print("Adding 5 Kg plate ... ");
			barbell.addPlate(new Plate(5));
			System.out.println("OK");
			
			System.out.print("Adding 1.25 Kg plate ... ");
			barbell.addPlate(new Plate(1.25));
			System.out.println("OK");
			
			System.out.print("Adding 1.5 Kg plate ... ");
			barbell.addPlate(new Plate(1.5));
			System.out.println("OK");
			
			assert barbell.getTotalWeigth() == 42.0 : "Total weight should be 42.0";

			System.out.println("Total weight is : " + barbell.getTotalWeigth());
		} catch (MaxPlatesReachedException e) {
			// won't happen
			System.out.println(e.toString());
		}

		try {
			System.out.println("Trying to add one more 5 Kg plate...");
			barbell.addPlate(new Plate(5));
		} catch (MaxPlatesReachedException e) {
			System.out.println(e.toString());
			barbell.detailWeight();
		}
	}
}

public class PlatesCalculator {
	public static void main(String[] args){
		
		System.out.println("Write any thing:");
		try {
			double[] inputs = Utils.parsePlatesEntry();
			System.out.println("Your input: " + Arrays.toString(inputs));
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println("Input should be comma seperated pair of PlateWeight(Kg),Count.\nExample: 5,2 // Two 5 Kg plates");
		}


	}
}
