package com.agpf.workhub.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import redis.clients.jedis.Jedis;

import java.util.Properties;

@Configuration
public class BeansConfig {

    @Value("${workhub.uri.jedis}")
    private String uriJedis;

    @Value("${workhub.email.username}")
    private String email;

    @Value("${workhub.email.password}")
    private String emailPassword;

    @Bean
    public Jedis jedis() {
        return new Jedis(uriJedis);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(email);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


}
