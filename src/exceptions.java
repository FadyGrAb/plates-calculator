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

class TooFewInputParametersException extends InvalidInputException {
  TooFewInputParametersException(String msg) {
    super(msg);
  }
}

class MaxPlatesReachedException extends Exception {
  MaxPlatesReachedException(String msg) {
    super(msg);
  }
}
