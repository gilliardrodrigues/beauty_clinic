package com.ubi.beautyClinic.adapters.outbound;

import com.ubi.beautyClinic.application.ports.out.JavaMailSenderOutboundPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JavaMailSenderAdapter implements JavaMailSenderOutboundPort {

    private final JavaMailSender javaMailSender;

    public JavaMailSenderAdapter(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String receiver, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }
}

