package lutz.algasensors.manager.api.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import io.hypersistence.tsid.TSID;
import lutz.algasensors.manager.api.model.SensorMonitoringOutput;

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {
	@GetExchange
	SensorMonitoringOutput getMonitoringDetails(@PathVariable TSID sensorId);

	@PutExchange("/enable")
	void enableMonitoring(@PathVariable TSID sensorId);

	@DeleteExchange("/enable")
	void disableMonitoring(@PathVariable TSID sensorId);
}
