package com.abolade.Learing.emumerations;

public enum Status {

    SERVER_UP("Server_UP"),
    SERVER_DOWN("SERVER_DOWN");

    Status(String status) {
        this.status = status;
    }

    private  final String status;

    public String getStatus() {
        return status;
    }



}
