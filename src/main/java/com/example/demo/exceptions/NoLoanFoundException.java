package com.example.demo.exceptions;

public class NoLoanFoundException extends RuntimeException{
    public NoLoanFoundException(String message) { super(message);}
}
