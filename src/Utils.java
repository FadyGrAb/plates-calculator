import java.util.Scanner;
import java.util.Arrays;

public class Utils {
	public static String getStdin() { 
		Scanner s = new Scanner(System.in);
		return s.nextLine();
	}

	public static double[] parsePlatesEntry() throws InvalidInputException {
		String stdinLine = Utils.getStdin();
		String[] tokens = stdinLine
					.replaceAll(" ", "")
					.split(",");

		if (tokens.length < 2) {
			throw new TooLittleInputParametersException("Input paramters are low: " + Arrays.toString(tokens));
		}

		if (tokens.length > 2) {
			throw new TooManyInputParametersException("Input paramters are too many. " + Arrays.toString(tokens));
		}

		
		double[] result = new double[2];

		for (int i = 0; i < 2; i++) {
			try {
				result[i] = Double.parseDouble(tokens[i]);
			} catch ( NumberFormatException e) {
				throw new InvalidInputException("Invalid input: " + tokens[i]);
			}
		}

		return result;
	}

	public static void printWelcomeBanner() {
		String banner = """
		    ____  __      __               ______      __           __      __
		   / __ \\/ /___ _/ /____  _____   / ____/___ _/ /______  __/ /___ _/ /_____  _____
		  / /_/ / / __ `/ __/ _ \\/ ___/  / /   / __ `/ / ___/ / / / / __ `/ __/ __ \\/ ___/
		 / ____/ / /_/ / /_/  __(__  )  / /___/ /_/ / / /__/ /_/ / / /_/ / /_/ /_/ / /
		/_/   /_/\\__,_/\\__/\\___/____/   \\____/\\__,_/_/\\___/\\__,_/_/\\__,_/\\__/\\____/_/
		""";

		System.out.println(banner);
	}
		

	
}
