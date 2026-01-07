package lutz.algasensors.manager.api.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import io.hypersistence.tsid.TSID;

public record TemperatureLogOutput(UUID id, TSID sensorId, OffsetDateTime registeredAt, Double value) {
}

