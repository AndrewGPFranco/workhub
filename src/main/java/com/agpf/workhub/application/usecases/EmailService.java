package com.agpf.workhub.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void enviarEmailComCodigoParaRegistro(String destino, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destino);
        message.setSubject("Código de validação de email");

        message.setText(
                String.format("""
                    O código de validação é: %s
                """, code)
        );

        emailSender.send(message);

        log.info(String.format("Email com código de validação de conta foi enviado para %s", destino));
    }

}
