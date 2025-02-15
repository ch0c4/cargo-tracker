package org.johan.tracker.pathfinder.api;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestBean;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.johan.tracker.pathfinder.internal.GraphDao;

@Controller("/graph-traversal")
public class GraphTraversalController {

  private static final long ONE_MIN_MS = 1000 * 60;
  private static final long ONE_DAY_MS = ONE_MIN_MS * 60 * 24;

  private final Random random = new Random();

  private final GraphDao dao;

  public GraphTraversalController(GraphDao dao) {
    this.dao = dao;
  }

  @Get("/shortest-path")
  public List<TransitPath> findShortestPath(@RequestBean @Valid ShortestPathQuery query) {
    List<String> allVertices = dao.listLocations();
    allVertices.remove(query.originUnLocode());
    allVertices.remove(query.destinationUnLocode());

    int candidateCount = getRandomNumberOfCandidates();
    List<TransitPath> candidates = new ArrayList<>(candidateCount);

    for (int i = 0; i < candidateCount; i++) {
      allVertices = getRandomChunkOfLocations(allVertices);
      List<TransitEdge> transitEdges = new ArrayList<>(allVertices.size() - 1);
      String fromUnLocode = query.originUnLocode();
      LocalDateTime date = LocalDateTime.now();

      for (int j = i + 1; j <= allVertices.size(); j++) {
        LocalDateTime fromDate = nextDate(date);
        LocalDateTime toDate = nextDate(fromDate);
        String toUnLocode =
            j >= allVertices.size() ? query.destinationUnLocode() : allVertices.get(j);
        transitEdges.add(
            new TransitEdge(
                dao.getVoyageNumber(fromUnLocode, toUnLocode),
                fromUnLocode,
                toUnLocode,
                fromDate,
                toDate));
        fromUnLocode = toUnLocode;
        date = nextDate(toDate);
      }
      candidates.add(new TransitPath(transitEdges));
    }
    return candidates;
  }

  private LocalDateTime nextDate(LocalDateTime date) {
    return date.plus(ONE_DAY_MS + (random.nextInt(1000) - 500) * ONE_MIN_MS, ChronoUnit.MILLIS);
  }

  private int getRandomNumberOfCandidates() {
    return 3 + random.nextInt(3);
  }

  private List<String> getRandomChunkOfLocations(List<String> allLocations) {
    Collections.shuffle(allLocations);
    int total = allLocations.size();
    int chunk = total > 4 ? 1 + random.nextInt(5) : total;
    return allLocations.subList(0, chunk);
  }
}
