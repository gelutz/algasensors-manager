package lutz.algasensors.manager.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lutz.algasensors.manager.api.model.DailyMedianTemperatureOutput;
import lutz.algasensors.manager.api.model.DetailedSensorOutput;
import lutz.algasensors.manager.api.model.SensorInput;
import lutz.algasensors.manager.api.model.SensorOutput;
import lutz.algasensors.manager.api.service.SensorService;
import lutz.algasensors.manager.domain.model.SensorId;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {
	private final SensorService sensorService;

	@GetMapping
	public Page<SensorOutput> search(@PageableDefault Pageable pageable) {
		return sensorService.list(pageable);
	}

	@GetMapping("{sensorId}")
	public SensorOutput getOne(@PathVariable TSID sensorId) {
		return sensorService.find(new SensorId(sensorId));
	}

	@GetMapping("{sensorId}/details")
	public DetailedSensorOutput getDetailed(@PathVariable TSID sensorId) {
		return sensorService.findDetails(sensorId);
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
	}

	@PutMapping("{sensorId}/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enable(@PathVariable TSID sensorId) {
		sensorService.enable(new SensorId(sensorId));
	}

	@DeleteMapping("{sensorId}/enable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disable(@PathVariable TSID sensorId) {
		sensorService.disable(new SensorId(sensorId));
	}

	@GetMapping("{sensorId}/temperatures/daily-median")
	public List<DailyMedianTemperatureOutput> getDailyMedianTemperatures(@PathVariable TSID sensorId) {
		return sensorService.getDailyMedianTemperatures(sensorId);
	}
}
