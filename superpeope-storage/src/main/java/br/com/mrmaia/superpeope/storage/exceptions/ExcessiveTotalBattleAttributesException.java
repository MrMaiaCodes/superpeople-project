package br.com.mrmaia.superpeope.storage.exceptions;

public class ExcessiveTotalBattleAttributesException extends Exception {

    private String code;

    private String message;

    public ExcessiveTotalBattleAttributesException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
