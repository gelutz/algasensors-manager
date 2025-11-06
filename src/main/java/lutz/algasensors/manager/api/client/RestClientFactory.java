package lutz.algasensors.manager.api.client;

import lombok.RequiredArgsConstructor;
import lutz.algasensors.manager.domain.exceptions.ClientBadGatewayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {
	private final RestClient.Builder builder;

	@Value("${app.services.monitoring.port}")
	private String port;
	@Value("${app.services.monitoring.url}")
	private String url;

	public RestClient monitorClient() {
		return builder.baseUrl(url + ":" + port)
		              .requestFactory(generateClientHttpRequestFactory())
		              .defaultStatusHandler(
				              HttpStatusCode::isError,
				              (request, response) -> {
					              throw new ClientBadGatewayException();
				              })
		              .build();
	}

	private ClientHttpRequestFactory generateClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

		factory.setReadTimeout(Duration.ofSeconds(5));
		factory.setConnectTimeout(Duration.ofSeconds(3));

		return factory;
	}
}
