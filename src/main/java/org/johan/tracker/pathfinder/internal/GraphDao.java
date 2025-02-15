package org.johan.tracker.pathfinder.internal;

import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Singleton
public class GraphDao {

  private final Random random = new Random();

  public List<String> listLocations() {
    return new ArrayList<>(
        Arrays.asList(
            "CNHKG", "AUMEL", "SESTO", "FIHEL", "USCHI", "JNTKO", "DEHAM", "CNSHA", "NLRTM",
            "SEGOT", "CNHGH", "USNYC", "USDAL"));
  }

  public String getVoyageNumber(String from, String to) {
    int i = random.nextInt(5);

    return switch (i) {
      case 0 -> "0100S";
      case 1 -> "0200T";
      case 2 -> "0300A";
      case 3 -> "0301S";
      default -> "0400S";
    };
  }
}
