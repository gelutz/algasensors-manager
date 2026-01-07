package lutz.algasensors.manager.api.service;

import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lutz.algasensors.manager.api.client.SensorMonitoringClient;
import lutz.algasensors.manager.api.model.DailyMedianTemperatureOutput;
import lutz.algasensors.manager.api.model.DetailedSensorOutput;
import lutz.algasensors.manager.api.model.SensorInput;
import lutz.algasensors.manager.api.model.SensorOutput;
import lutz.algasensors.manager.api.model.TemperatureLogOutput;
import lutz.algasensors.manager.common.IdUtils;
import lutz.algasensors.manager.common.StringUtils;
import lutz.algasensors.manager.domain.exceptions.ResourceNotFoundException;
import lutz.algasensors.manager.domain.model.Sensor;
import lutz.algasensors.manager.domain.model.SensorId;
import lutz.algasensors.manager.domain.repository.SensorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorService {
	private final SensorRepository sensorRepository;
	private final SensorMonitoringClient sensorMonitoringClient;

	public Page<SensorOutput> list(@NonNull Pageable pageable) {
		return sensorRepository.findAll(pageable).map(SensorOutput::fromModel);
	}

	private @NonNull Sensor findModel(@NonNull SensorId id) {
		return sensorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	public @NonNull SensorOutput find(@NonNull SensorId id) {
		var sensor = findModel(id);
		return SensorOutput.fromModel(sensor);
	}

	public @NonNull DetailedSensorOutput findDetails(@NonNull TSID id) {
		var sensor = findModel(new SensorId(id));
		var details = sensorMonitoringClient.getMonitoringDetails(id);

		return new DetailedSensorOutput(
				SensorOutput.fromModel(sensor),
				details
		);
	}

	public SensorOutput create(SensorInput input) {
		var sensor = Sensor.builder()
		                   .id(new SensorId(IdUtils.tsid()))
		                   .name(input.name())
		                   .ip(input.ip())
		                   .location(input.location())
		                   .protocol(input.protocol())
		                   .model(input.model())
		                   .enabled(false)
		                   .build();

		return SensorOutput.fromModel(sensorRepository.save(sensor));
	}

	public SensorOutput update(@NonNull SensorId sensorId, SensorInput data) {
		Sensor target = findModel(sensorId);

		try {
			for (RecordComponent recordComponent : SensorInput.class.getRecordComponents()) {
				var attribute = recordComponent.getAccessor().invoke(data);
				if (attribute != null) {
					var methodName = "set" + StringUtils.capitalize(recordComponent.getName());
					// aqui é feito o set{campo} do objeto target com o valor attribute
					target.getClass().getDeclaredMethod(methodName, attribute.getClass()).invoke(target, attribute);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		return SensorOutput.fromModel(sensorRepository.save(target));
	}

	public void delete(@NonNull SensorId sensorId) {
		var sensor = findModel(sensorId);
		sensorRepository.delete(sensor);
	}

	public void enable(@NonNull SensorId sensorId) {
		Sensor sensor = findModel(sensorId);
		sensor.setEnabled(true);

		sensorMonitoringClient.enableMonitoring(sensorId.getValue());

		sensorRepository.save(sensor);
	}

	public void disable(@NonNull SensorId sensorId) {
		Sensor sensor = findModel(sensorId);
		sensor.setEnabled(false);

		sensorMonitoringClient.disableMonitoring(sensorId.getValue());

		sensorRepository.save(sensor);
	}

	public List<DailyMedianTemperatureOutput> getDailyMedianTemperatures(@NonNull TSID sensorId) {
		return sensorMonitoringClient.getDailyMedianTemperatures(sensorId);
	}

	public List<TemperatureLogOutput> getTemperatureHistory(@NonNull TSID sensorId, int days) {
		return sensorMonitoringClient.getTemperatureHistory(sensorId, days);
	}
}
