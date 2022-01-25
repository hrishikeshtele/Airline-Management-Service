package com.ht.airline.service.impl;

import com.ht.airline.dto.common.PasswordDeliveryDTO;
import com.ht.airline.dto.response.ErrResponse;
import com.ht.airline.dto.response.GenResponse;
import com.ht.airline.security.RandomStringGenerator;
import com.ht.airline.service.EmailService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  @Autowired private JavaMailSender javaMailSender;
  @Autowired private RandomStringGenerator randomStringGenerator;
  @Autowired private SpringTemplateEngine templateEngine;

  @Value("${app.settings.smtp.userName}")
  private String userName;

  @Value("${app.settings.email-template.verification}")
  private String verificationEmailTemplate;

  @Value("${app.settings.email-template.notify}")
  private String notifyEmailTemplate;

  private static final String PASSWORD_KEY = "password";
  private static final String USERNAME_KEY = "username";
  private static final String BOOKING_STATUS_KEY = "booking_status";
  private static final String BOOKING_DETAILS_KEY = "booking_details";
  private static final String IMG_CONTENT_TYPE = "image/png";
  private static final String EMAIL_VERIFICATION_SUBJECT = "email_verification_subject";

  private static final String GIT_IMG_PATH = "/images/github-image.png";
  private static final String MAIL_IMG_PATH = "/images/mail-image.png";
  private static final String LINKEDIN_IMG_PATH = "/images/linkedin-image.png";

  private static final String GIT_SYMBOL = "github_img";
  private static final String MAIL_SYMBOL = "mail_img";
  private static final String LINKEDIN_SYMBOL = "linkedin_img";

  @Override
  public boolean sendCode(PasswordDeliveryDTO passwordDeliveryDTO, GenResponse genResponse) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(
              message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      Context context = new Context();
      Map<String, Object> model = new HashMap<>();
      model.put(PASSWORD_KEY, passwordDeliveryDTO.getPassword());
      model.put(USERNAME_KEY, passwordDeliveryDTO.getName());
      context.setVariables(model);

      // Git image
      URL gitUrl = getClass().getResource(GIT_IMG_PATH);
      if (gitUrl == null) throw new FileNotFoundException("File cannot be found.");
      File gitFile = new File(gitUrl.getPath());
      Resource gitResource = new ClassPathResource(GIT_IMG_PATH);
      InputStream gitInputStream = gitResource.getInputStream();
      byte[] imageBytes = FileCopyUtils.copyToByteArray(gitInputStream);
      InputStreamSource gitImageSource = new ByteArrayResource(imageBytes);

      // Mail Image
      URL mailUrl = getClass().getResource(MAIL_IMG_PATH);
      if (mailUrl == null) throw new FileNotFoundException("File cannot be found.");
      File mailFile = new File(mailUrl.getPath());
      Resource mailResource = new ClassPathResource(MAIL_IMG_PATH);
      InputStream mailInputStream = mailResource.getInputStream();
      byte[] mailImageBytes = FileCopyUtils.copyToByteArray(mailInputStream);
      InputStreamSource mailImageSource = new ByteArrayResource(mailImageBytes);

      // Linkedin Image
      URL linkedinUrl = getClass().getResource(LINKEDIN_IMG_PATH);
      if (linkedinUrl == null) throw new FileNotFoundException("File cannot be found.");
      File linkedinFile = new File(linkedinUrl.getPath());
      Resource linkedinResource = new ClassPathResource(LINKEDIN_IMG_PATH);
      InputStream linkedinInputStream = linkedinResource.getInputStream();
      byte[] linkedinImageBytes = FileCopyUtils.copyToByteArray(linkedinInputStream);
      InputStreamSource linkedinImageSource = new ByteArrayResource(linkedinImageBytes);

      context.setVariable(GIT_SYMBOL, gitFile.getName());
      context.setVariable(MAIL_SYMBOL, mailFile.getName());
      context.setVariable(LINKEDIN_SYMBOL, linkedinFile.getName());

      String html = templateEngine.process(verificationEmailTemplate, context);
      InternetAddress internetAddress = new InternetAddress(passwordDeliveryDTO.getToEmail());
      internetAddress.validate();
      helper.setTo(passwordDeliveryDTO.getToEmail());
      helper.setText(html, true);
      ResourceBundle bundle = ResourceBundle.getBundle("messages");
      helper.setSubject(bundle.getString(EMAIL_VERIFICATION_SUBJECT));
      helper.setFrom(userName);

      helper.addInline(gitFile.getName(), gitImageSource, IMG_CONTENT_TYPE);
      helper.addInline(mailFile.getName(), mailImageSource, IMG_CONTENT_TYPE);
      helper.addInline(linkedinFile.getName(), linkedinImageSource, IMG_CONTENT_TYPE);

      javaMailSender.send(message);
      return true;
    } catch (AddressException e) {
      log.error(passwordDeliveryDTO.getToEmail() + " is not a valid email address");
    } catch (Exception e) {
      log.error("Email delivery failed: " + e.getMessage());
    }
    genResponse.setStatusCode(HttpStatus.BAD_GATEWAY.value());
    genResponse.setError(
        ErrResponse.builder()
            .errorCode(HttpStatus.BAD_GATEWAY.value())
            .description("EMAIL_DELIVERY_FAILED")
            .build());
    return false;
  }

  @Async
  @Override
  public boolean sendConfirmationMail(String email, String mess, String messStatus, String name) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(
              message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      Context context = new Context();
      Map<String, Object> model = new HashMap<>();
      model.put(BOOKING_STATUS_KEY, messStatus);
      model.put(BOOKING_DETAILS_KEY, mess);
      model.put(USERNAME_KEY, name);
      context.setVariables(model);

      // Git image
      URL gitUrl = getClass().getResource(GIT_IMG_PATH);
      if (gitUrl == null) throw new FileNotFoundException("File cannot be found.");
      File gitFile = new File(gitUrl.getPath());
      Resource gitResource = new ClassPathResource(GIT_IMG_PATH);
      InputStream gitInputStream = gitResource.getInputStream();
      byte[] imageBytes = FileCopyUtils.copyToByteArray(gitInputStream);
      InputStreamSource gitImageSource = new ByteArrayResource(imageBytes);

      // Mail Image
      URL mailUrl = getClass().getResource(MAIL_IMG_PATH);
      if (mailUrl == null) throw new FileNotFoundException("File cannot be found.");
      File mailFile = new File(mailUrl.getPath());
      Resource mailResource = new ClassPathResource(MAIL_IMG_PATH);
      InputStream mailInputStream = mailResource.getInputStream();
      byte[] mailImageBytes = FileCopyUtils.copyToByteArray(mailInputStream);
      InputStreamSource mailImageSource = new ByteArrayResource(mailImageBytes);

      // Linkedin Image
      URL linkedinUrl = getClass().getResource(LINKEDIN_IMG_PATH);
      if (linkedinUrl == null) throw new FileNotFoundException("File cannot be found.");
      File linkedinFile = new File(linkedinUrl.getPath());
      Resource linkedinResource = new ClassPathResource(LINKEDIN_IMG_PATH);
      InputStream linkedinInputStream = linkedinResource.getInputStream();
      byte[] linkedinImageBytes = FileCopyUtils.copyToByteArray(linkedinInputStream);
      InputStreamSource linkedinImageSource = new ByteArrayResource(linkedinImageBytes);

      context.setVariable(GIT_SYMBOL, gitFile.getName());
      context.setVariable(MAIL_SYMBOL, mailFile.getName());
      context.setVariable(LINKEDIN_SYMBOL, linkedinFile.getName());

      String html = templateEngine.process(notifyEmailTemplate, context);
      InternetAddress internetAddress = new InternetAddress(email);
      internetAddress.validate();
      helper.setTo(email);
      helper.setText(html, true);
      ResourceBundle bundle = ResourceBundle.getBundle("messages");
      helper.setSubject("Booking Details");
      helper.setFrom(userName);

      helper.addInline(gitFile.getName(), gitImageSource, IMG_CONTENT_TYPE);
      helper.addInline(mailFile.getName(), mailImageSource, IMG_CONTENT_TYPE);
      helper.addInline(linkedinFile.getName(), linkedinImageSource, IMG_CONTENT_TYPE);

      javaMailSender.send(message);
      return true;
    } catch (AddressException e) {
      log.error(email + " is not a valid email address");
    } catch (Exception e) {
      log.error("Email delivery failed: " + e.getMessage());
    }
    log.error("EMAIL_DELIVERY_FAILED");
    return false;
  }
}
