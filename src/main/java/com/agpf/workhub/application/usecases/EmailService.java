package com.agpf.workhub.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void enviarEmailComCodigoParaRegistro(String destino, String code) {
        try {
            jakarta.mail.internet.MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(destino);
            helper.setSubject("Código de validação de email");

            ClassPathResource resource = new ClassPathResource("templates/email-validation.html");
            String htmlTemplate = StreamUtils.copyToString(resource.getInputStream(), java.nio.charset.StandardCharsets.UTF_8);
            String htmlContent = htmlTemplate.replace("{{code}}", code);

            helper.setText(htmlContent, true);

            emailSender.send(message);

            log.info(String.format("Email com código de validação de conta foi enviado para %s", destino));
        } catch (jakarta.mail.MessagingException | java.io.IOException e) {
            log.error("Erro ao enviar email: ", e);
            throw new RuntimeException("Falha ao enviar email", e);
        }
    }

}
