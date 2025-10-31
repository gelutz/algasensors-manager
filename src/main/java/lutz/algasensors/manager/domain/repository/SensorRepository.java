package lutz.algasensors.manager.domain.repository;

import lutz.algasensors.manager.domain.model.Sensor;
import lutz.algasensors.manager.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}
