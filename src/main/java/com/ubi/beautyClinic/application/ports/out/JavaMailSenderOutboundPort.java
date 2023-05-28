package com.ubi.beautyClinic.application.ports.out;

public interface JavaMailSenderOutboundPort {

    void sendEmail(String receiver, String subject, String content);
}
