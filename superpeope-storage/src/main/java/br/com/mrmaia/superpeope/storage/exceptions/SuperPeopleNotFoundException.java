package br.com.mrmaia.superpeope.storage.exceptions;

public class SuperPeopleNotFoundException extends Exception {

    private String code;

    private String message;

    public SuperPeopleNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
