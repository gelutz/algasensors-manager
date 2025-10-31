package lutz.algasensors.manager.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
	@Id
	@AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
	private SensorId id;
	private String name;
	private String ip;
	private String location;
	private String protocol;
	private String model;
	private boolean enabled;
}
