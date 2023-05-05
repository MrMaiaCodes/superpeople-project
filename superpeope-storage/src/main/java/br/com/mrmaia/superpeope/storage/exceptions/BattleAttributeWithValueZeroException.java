package br.com.mrmaia.superpeope.storage.exceptions;

public class BattleAttributeWithValueZeroException extends Exception {

    private String code;

    private String message;

    public BattleAttributeWithValueZeroException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
