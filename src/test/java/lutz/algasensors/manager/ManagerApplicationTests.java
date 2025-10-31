package lutz.algasensors.manager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lutz.algasensors.manager.common.IdUtils;

@SpringBootTest
class ManagerApplicationTests {

	@Test
	void shouldGenerateTSID() {
		var tsid = IdUtils.tsid();

		Assertions.assertThat(tsid.getInstant())
				.isCloseTo(Instant.now(),
						Assertions.within(1, ChronoUnit.MINUTES));
	}

}
