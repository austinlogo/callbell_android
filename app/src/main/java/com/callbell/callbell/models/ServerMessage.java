package com.callbell.callbell.models;

/**
 * Created by austin on 10/4/15.
 */
public class ServerMessage {

    private String from,
            to,
            message,
            hospital,
            operation;

    public ServerMessage(String hos, String op, String fr, String t, String msg) {
        hospital = hos;
        operation = op;
        from = fr;
        to = t;
        message = msg;
    }

    public String getHospital() {
        return hospital;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getOperation() {
        return "/" + operation;
    }

}
