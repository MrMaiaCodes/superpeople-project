package br.com.mrmaia.superpeope.storage.exceptions;

public class TotalBattleAttributesOverThirtyException extends Exception {

    private String code;

    private String message;

    public TotalBattleAttributesOverThirtyException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
