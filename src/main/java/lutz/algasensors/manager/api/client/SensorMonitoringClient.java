package lutz.algasensors.manager.api.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import io.hypersistence.tsid.TSID;
import lutz.algasensors.manager.api.model.DailyMedianTemperatureOutput;
import lutz.algasensors.manager.api.model.SensorMonitoringOutput;

import java.util.List;

@HttpExchange("/api/sensors/{sensorId}")
public interface SensorMonitoringClient {
	@GetExchange("/monitoring")
	SensorMonitoringOutput getMonitoringDetails(@PathVariable TSID sensorId);

	@PutExchange("/monitoring/enable")
	void enableMonitoring(@PathVariable TSID sensorId);

	@DeleteExchange("/monitoring/enable")
	void disableMonitoring(@PathVariable TSID sensorId);

	@GetExchange("/temperatures/daily-median")
	List<DailyMedianTemperatureOutput> getDailyMedianTemperatures(@PathVariable TSID sensorId);
}
