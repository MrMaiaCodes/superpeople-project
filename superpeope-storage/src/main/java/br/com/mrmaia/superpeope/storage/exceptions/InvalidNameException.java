package br.com.mrmaia.superpeope.storage.exceptions;

public class InvalidNameException extends Exception {

    private String code;

    private String message;

    public InvalidNameException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
