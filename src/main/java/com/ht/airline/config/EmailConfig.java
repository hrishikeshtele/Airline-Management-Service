package com.ht.airline.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmailConfig {

  private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
  private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
  private static final String MAIL_SMTP_EHLO = "mail.smtp.ehlo";
  private static final String MAIL_SMTP_START_TLS_ENABLE = "mail.smtp.starttls.enable";
  private static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
  private static final String MAIL_DEBUG = "mail.debug";

  @Value("${app.settings.smtp.host}")
  private String host;

  @Value("${app.settings.smtp.port}")
  private Integer port;

  @Value("${app.settings.smtp.userName}")
  private String userName;

  @Value("${app.settings.smtp.password}")
  private String password;

  @Value("${app.settings.smtp.protocol}")
  private String protocol;

  @Value("${app.settings.smtp.auth}")
  private String auth;

  @Value("${app.settings.smtp.ehlo}")
  private String ehlo;

  @Value("${app.settings.smtp.starttls.enable}")
  private String startTlsEnable;

  @Value("${app.settings.smtp.ssl.enable}")
  private String sslEnable;

  @Value("${app.settings.smtp.debug}")
  private String debug;

  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(userName);
    mailSender.setPassword(password);
    Properties props = mailSender.getJavaMailProperties();
    props.put(MAIL_TRANSPORT_PROTOCOL, protocol);
    props.put(MAIL_SMTP_AUTH, auth);
    props.put(MAIL_SMTP_EHLO, ehlo);
    props.put(MAIL_SMTP_START_TLS_ENABLE, startTlsEnable);
    props.put(MAIL_SMTP_SSL_ENABLE, sslEnable);
    props.put(MAIL_DEBUG, debug);
    return mailSender;
  }

  @Bean
  public SpringTemplateEngine springTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(htmlTemplateResolver());
    return templateEngine;
  }

  @Bean
  public SpringResourceTemplateResolver htmlTemplateResolver() {
    SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
    emailTemplateResolver.setPrefix("classpath:/templates/");
    emailTemplateResolver.setSuffix(".html");
    emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
    emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    return emailTemplateResolver;
  }
}
