package lutz.algasensors.manager.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException() {
		super("Recurso não encontrado");
	}
}
