package lutz.algasensors.manager.api.config.jpa;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Converter(autoApply = true)
public class TSIDToLongAttributeConverter implements AttributeConverter<TSID, Long> {
	@Override
	public Long convertToDatabaseColumn(TSID attribute) {
		return attribute.toLong();
	}

	@Override
	public TSID convertToEntityAttribute(Long dbData) {
		return TSID.from(dbData);
	}
}
