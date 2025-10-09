import java.util.Arrays;
import java.util.Scanner;

public class Utils {
  public static String getStdin() {
    Scanner s = new Scanner(System.in);
    return s.nextLine();
  }

  public static double[] parsePlatesEntry(String userInput) throws InvalidInputException {
    // TODO fix spaces bug 3434 3443, 3434 3434
    String[] tokens = userInput.replaceAll(" ", "").split(",");

    if (tokens.length < 2) {
      throw new TooLittleInputParametersException(
          "Input paramters are low: " + Arrays.toString(tokens));
    }

    if (tokens.length > 2) {
      throw new TooManyInputParametersException(
          "Input paramters are too many. " + Arrays.toString(tokens));
    }

    double[] result = new double[2];

    for (int i = 0; i < 2; i++) {
      try {
        result[i] = Double.parseDouble(tokens[i]);
      } catch (NumberFormatException e) {
        throw new InvalidInputException("Invalid input: " + tokens[i]);
      }
    }

    return result;
  }

  public static void printWelcomeBanner() {
    String banner =
        """
            ____  __      __               ______      __           __      __
           / __ \\/ /___ _/ /____  _____   / ____/___ _/ /______  __/ /___ _/ /_____  _____
          / /_/ / / __ `/ __/ _ \\/ ___/  / /   / __ `/ / ___/ / / / / __ `/ __/ __ \\/ ___/
         / ____/ / /_/ / /_/  __(__  )  / /___/ /_/ / / /__/ /_/ / / /_/ / /_/ /_/ / /
        /_/   /_/\\__,_/\\__/\\___/____/   \\____/\\__,_/_/\\___/\\__,_/_/\\__,_/\\__/\\____/_/
        """;

    System.out.println(banner);
  }

  public static int showMenu(String menuTitle, String[] menuItems, String menuPrompt) {
    System.out.println(menuTitle + ":");

    for (int i = 0; i < menuItems.length; i++) {
      System.out.println(String.format("\t[%d] %s", i + 1, menuItems[i]));
    }

    boolean haveValidresponse = false;
    int response = 0;

    while (!haveValidresponse) {
      System.out.print(menuPrompt + ": ");
      String input = Utils.getStdin();
      try {
        response = Integer.parseInt(input.strip());
        if (response >= 1 && response <= menuItems.length) {
          break;
        }

        System.out.println(
            "[ERROR] Your input is invalid. Please choose a number between 1 and "
                + menuItems.length
                + ".");
      } catch (NumberFormatException e) {
        System.out.println("[ERROR] This isn't a valid number.");
      }
    }

    return response - 1;
  }
}
