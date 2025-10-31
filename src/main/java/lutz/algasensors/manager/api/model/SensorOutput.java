package lutz.algasensors.manager.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lutz.algasensors.manager.domain.model.Sensor;

public record SensorOutput(TSID id, String name, String ip, String location, String protocol, String model, Boolean enabled) {
	public static SensorOutput fromModel(Sensor sensor) {
		return new SensorOutput(sensor.getId().getValue(), sensor.getName(), sensor.getIp(), sensor.getLocation(), sensor.getProtocol(), sensor.getModel(), sensor.isEnabled());
	}
}
