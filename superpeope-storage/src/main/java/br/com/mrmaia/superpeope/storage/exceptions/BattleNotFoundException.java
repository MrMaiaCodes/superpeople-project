package br.com.mrmaia.superpeope.storage.exceptions;

public class BattleNotFoundException extends Exception {

    private String code;

    private String message;

    public BattleNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {return code;}
}
