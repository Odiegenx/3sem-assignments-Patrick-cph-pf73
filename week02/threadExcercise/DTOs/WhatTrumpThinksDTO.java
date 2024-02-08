package threadExcercise.DTOs;

public class WhatTrumpThinksDTO implements DTOInterface{
    String message;
    @Override
    public Object getResults() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
