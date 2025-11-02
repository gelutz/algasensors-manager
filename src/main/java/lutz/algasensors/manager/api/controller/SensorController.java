package lutz.algasensors.manager.api.controller;


import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lutz.algasensors.manager.api.client.SensorMonitoringClient;
import lutz.algasensors.manager.api.model.SensorInput;
import lutz.algasensors.manager.api.model.SensorOutput;
import lutz.algasensors.manager.api.service.SensorService;
import lutz.algasensors.manager.domain.model.SensorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {
	private final SensorService sensorService;
	private final SensorMonitoringClient sensorMonitoringClient;

	@GetMapping
	public Page<SensorOutput> search(@PageableDefault Pageable pageable) {
		return sensorService.list(pageable);
	}

	@GetMapping("{sensorId}")
	public SensorOutput getOne(@PathVariable TSID sensorId) {
		return sensorService.find(new SensorId(sensorId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SensorOutput create(@RequestBody SensorInput input) {
		return sensorService.create(input);
	}

	@PutMapping("{sensorId}")
	@ResponseStatus(HttpStatus.OK)
	public SensorOutput update(@PathVariable TSID sensorId, @RequestBody SensorInput data) {
		return sensorService.update(new SensorId(sensorId), data);
	}

	@DeleteMapping("{sensorId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable TSID sensorId) {
		sensorService.delete(new SensorId(sensorId));
		sensorMonitoringClient.disableMonitoring(sensorId);
	}

	@PutMapping("{sensorId}/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enable(@PathVariable TSID sensorId) {
		sensorService.enable(new SensorId(sensorId));
		
		sensorMonitoringClient.enableMonitoring(sensorId);
	}

	@DeleteMapping("{sensorId}/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disable(@PathVariable TSID sensorId) {
		sensorService.disable(new SensorId(sensorId));
		sensorMonitoringClient.disableMonitoring(sensorId);
	}
}
