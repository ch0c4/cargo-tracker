package org.johan.tracker.cargotracker.domain.model.voyage;

import java.util.List;

public interface VoyageRepository {

  Voyage find(VoyageNumber voyageNumber);

  List<Voyage> findAll();
}
