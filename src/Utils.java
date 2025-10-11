import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum Answer {
  YES,
  NO,
  ANY,
}

public class Utils {
  public static void clearScreen() {
    try {
      if (System.getProperty("os.name").startsWith("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void pauseScreen(String prompt) {
    System.out.print("\n" + prompt);
    getStdin();
  }

  public static String getStdin() {
    Scanner s = new Scanner(System.in);
    if (s.hasNextLine()) {
      return s.nextLine();
    } else {
      System.exit(0);
      return null;
    }
  }

  /**
   * Asks the user a question and return the answer
   *
   * @param prompt The yes/no question to be asked.
   * @param defaultAnswer The default answer if the user presses Enter.
   * @return Answer YES/NO answer enum.
   */
  public static Answer ask(String prompt, Answer defaultAnswer) {
    String expectedAnswers = " (yes/no): ";
    List yesList = List.of("y", "yes");
    List noList = List.of("n", "no");

    if (defaultAnswer == Answer.YES) {
      expectedAnswers = " ([Yes]/no): ";
    }

    if (defaultAnswer == Answer.NO) {
      expectedAnswers = " (yes/[No]): ";
    }

    while (true) {
      System.out.print(prompt + expectedAnswers);
      String userInput = Utils.getStdin().strip();

      if (userInput == "" && defaultAnswer != Answer.ANY) {
        return defaultAnswer;
      }

      if (yesList.contains(userInput.toLowerCase())) {
        return Answer.YES;
      }

      if (noList.contains(userInput.toLowerCase())) {
        return Answer.NO;
      }

      System.out.println("[ERROR] Not a valid answer. Type yes/y or no/n.");
    }
  }

  public static double[] parsePlatesEntry(String userInput) throws InvalidInputException {
    // TODO fix spaces bug 3434 3443, 3434 3434
    String[] tokens = userInput.replaceAll(" ", "").split(",");

    if (tokens.length < 2) {
      throw new TooFewInputParametersException(
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
