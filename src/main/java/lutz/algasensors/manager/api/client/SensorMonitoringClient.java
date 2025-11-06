package lutz.algasensors.manager.api.client;

import io.hypersistence.tsid.TSID;
import lutz.algasensors.manager.api.model.SensorMonitoringOutput;

public interface SensorMonitoringClient {
	SensorMonitoringOutput getMonitoringDetails(TSID sensorId);

	void enableMonitoring(TSID sensorId);

	void disableMonitoring(TSID sensorId);
}
