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

	
}
