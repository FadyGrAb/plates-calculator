class InvalidInputException extends Exception {
  InvalidInputException(String msg) {
    super(msg);
  }
}

class TooManyInputParametersException extends InvalidInputException {
  TooManyInputParametersException(String msg) {
    super(msg);
  }
}

class TooLittleInputParametersException extends InvalidInputException {
  TooLittleInputParametersException(String msg) {
    super(msg);
  }
}

class MaxPlatesReachedException extends Exception {
  MaxPlatesReachedException(String msg) {
    super(msg);
  }
}
