package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = "email_queue")
    public void sendEmail(EmailDTO emailDTO) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("fatima.sul@div.edu.az");
            mailMessage.setTo(emailDTO.getTo());
            mailMessage.setText(emailDTO.getBody());
            mailMessage.setSubject(emailDTO.getSubject());
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println("Email sending failed");
        }
    }
}

