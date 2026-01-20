package br.com.detran.SpringVeiculoProprietario.exception;

public class DuplicateCpfCnpjException extends BusinessException {
    public DuplicateCpfCnpjException(String cpfCnpj) {
        super(/*"CPF/CNPJ já cadastrado: " +*/ cpfCnpj);
    }
}
