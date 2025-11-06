package lutz.algasensors.manager.api.client.impl;

import io.hypersistence.tsid.TSID;
import lutz.algasensors.manager.api.client.RestClientFactory;
import lutz.algasensors.manager.api.client.SensorMonitoringClient;
import lutz.algasensors.manager.api.model.SensorMonitoringOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {
	private final RestClient restClient;

	public SensorMonitoringClientImpl(RestClientFactory factory) {
		this.restClient = factory.monitorClient();
	}


	@Override
	public SensorMonitoringOutput getMonitoringDetails(TSID sensorId) {
		return restClient.get()
		                 .uri("/api/sensors/{sensorId}/monitoring")
		                 .retrieve()
		                 .body(SensorMonitoringOutput.class);
	}

	@Override
	public void enableMonitoring(TSID sensorId) {
		restClient.put()
		          .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
		          .retrieve()
		          .toBodilessEntity();
	}

	@Override
	public void disableMonitoring(TSID sensorId) {
		restClient.delete()
		          .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
		          .retrieve()
		          .toBodilessEntity();
	}
}
