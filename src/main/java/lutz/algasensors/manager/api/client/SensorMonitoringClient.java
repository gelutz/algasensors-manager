package lutz.algasensors.manager.api.client;

import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {
	void enableMonitoring(TSID sensorId);

	void disableMonitoring(TSID sensorId);
}
