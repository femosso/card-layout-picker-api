package com.example.demo.common.service;

public interface MessageService {

    boolean sendForgotPasswordMessage(String email, String locale);

}
