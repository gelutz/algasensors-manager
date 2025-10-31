package lutz.algasensors.manager.api.controller;


import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lutz.algasensors.manager.api.model.SensorInput;
import lutz.algasensors.manager.api.model.SensorOutput;
import lutz.algasensors.manager.common.IdUtils;
import lutz.algasensors.manager.domain.model.Sensor;
import lutz.algasensors.manager.domain.model.SensorId;
import lutz.algasensors.manager.domain.repository.SensorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {
	private final SensorRepository sensorRepository;


	@GetMapping("{sensorId}")
	public SensorOutput getOne(@PathVariable TSID sensorId) {
		var sensor = sensorRepository.findById(new SensorId(sensorId))
		                             .orElseThrow(() -> new RuntimeException("nenhum sensor encontrado."));
		return SensorOutput.fromModel(sensor);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SensorOutput create(@RequestBody SensorInput input) {
		var sensor = Sensor.builder()
				.id(new SensorId(IdUtils.tsid()))
				.name(input.name())
				.ip(input.ip())
				.location(input.location())
				.protocol(input.protocol())
				.model(input.model())
				.enabled(false)
				.build();

		sensor = sensorRepository.save(sensor);

		return SensorOutput.fromModel(sensor);
	}
}
