package lutz.algasensors.manager.api.model;

import java.time.LocalDate;

public record DailyMedianTemperatureOutput(LocalDate date, Double medianTemperature) {
}

