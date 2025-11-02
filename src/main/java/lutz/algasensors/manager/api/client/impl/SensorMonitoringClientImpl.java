package lutz.algasensors.manager.api.client.impl;

import io.hypersistence.tsid.TSID;
import lutz.algasensors.manager.api.client.SensorMonitoringClient;
import lutz.algasensors.manager.domain.exceptions.ClientBadGatewayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {
	private final RestClient restClient;

	private final long READ_TIMEOUT_SECONDS = 5;
	private final long CONNECT_TIMEOUT_SECONDS = 3;

	public SensorMonitoringClientImpl(
			RestClient.Builder rcb,
			@Value("${app.services.monitoring.port}") String port,
			@Value("${app.services.monitoring.url}") String url) {
		this.restClient = rcb.baseUrl(url + ":" + port)
		                     .requestFactory(generateClientHttpRequestFactory())
		                     .defaultStatusHandler(
				                     HttpStatusCode::isError,
				                     (request, response) -> {
					                     throw new ClientBadGatewayException();
				                     })
		                     .build();
	}

	private ClientHttpRequestFactory generateClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

		factory.setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS));
		factory.setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS));

		return factory;
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
