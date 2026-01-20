package br.com.detran.SpringVeiculoProprietario.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
