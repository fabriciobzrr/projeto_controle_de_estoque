package exception;

public class EstoqueException extends RuntimeException {
    public EstoqueException(String mensagemErro) {
        super(mensagemErro);
    }
}
