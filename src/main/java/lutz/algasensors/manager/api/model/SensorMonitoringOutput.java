package lutz.algasensors.manager.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record SensorMonitoringOutput(TSID id, Double lastTemperature, OffsetDateTime updatedAt, Boolean enabled) {
}
