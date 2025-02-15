package org.johan.tracker.cargotracker.interfaces.booking.sse;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.CargoRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@Controller("/cargo")
public class RealtimeCargoTrackingController {

  private final CargoRepository cargoRepository;
  private final Sinks.Many<RealtimeCargoTrackingViewAdapter> sink;

  public RealtimeCargoTrackingController(CargoRepository cargoRepository) {
    this.cargoRepository = cargoRepository;
    this.sink = Sinks.many().multicast().onBackpressureBuffer();
    log.debug("SSE sink created");
  }

  @Get(produces = MediaType.TEXT_EVENT_STREAM)
  public Publisher<RealtimeCargoTrackingViewAdapter> tracking() {
    Flux<RealtimeCargoTrackingViewAdapter> initial =
        Flux.fromIterable(
            cargoRepository.findAll().stream()
                .map(RealtimeCargoTrackingViewAdapter::from)
                .toList());

    return Flux.concat(
        initial, sink.asFlux().doOnSubscribe(sub -> log.debug("SSE subscriber connected")));
  }

  public void onCargoUpdated(Cargo cargo) {
    log.debug("Broadcasting cargo update: {}", cargo);
    sink.tryEmitNext(RealtimeCargoTrackingViewAdapter.from(cargo));
  }
}
