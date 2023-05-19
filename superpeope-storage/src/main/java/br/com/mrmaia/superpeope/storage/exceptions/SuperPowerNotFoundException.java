package br.com.mrmaia.superpeope.storage.exceptions;

public class SuperPowerNotFoundException extends Exception{

    private String code;

    private String message;

    public SuperPowerNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {return code;}
}
