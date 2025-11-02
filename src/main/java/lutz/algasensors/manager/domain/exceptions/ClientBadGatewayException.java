package lutz.algasensors.manager.domain.exceptions;

public class ClientBadGatewayException extends RuntimeException {
	public ClientBadGatewayException() {
		super("Houve um erro na conexão com o serviço.");
	}
}
