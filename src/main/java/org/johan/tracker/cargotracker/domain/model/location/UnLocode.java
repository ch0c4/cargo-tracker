package org.johan.tracker.cargotracker.domain.model.location;

public record UnLocode(String code) {

  private static final java.util.regex.Pattern VALID_PATTERN =
      java.util.regex.Pattern.compile("[a-zA-Z]{2}[a-zA-Z2-9]{3}");

  public UnLocode {
    if (!VALID_PATTERN.matcher(code).matches()) {
      throw new IllegalArgumentException("Illegal code: " + code);
    }
  }
}
