package com.example.demo.common.service.impl;

import com.example.demo.common.service.MessageService;
import com.example.demo.common.web.payload.Mail;
import com.example.demo.user.persistence.entity.PasswordResetToken;
import com.example.demo.user.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private Configuration freemarkerConfig;

    @Value("${demo.password.updateUrl}")
    private String passwordUpdateUrl;

    @Override
    public boolean sendForgotPasswordMessage(String email, String locale) {
        PasswordResetToken pwdReset = userService.createPasswordResetToken(email);
        if (pwdReset == null) return false;

        Map<String, Object> model = new HashMap<>();
        model.put("firstName", pwdReset.getUser().getFirstName());
        model.put("lastName", pwdReset.getUser().getLastName());
        model.put("passwordUpdateUrl", passwordUpdateUrl + pwdReset.getToken());

        Mail mail = new Mail();
        mail.setMailFrom("");
        mail.setMailTo(pwdReset.getUser().getEmail());
        mail.setModel(model);

        try {
            sendEmail(mail, "auth/forgot-password", getLocaleFromString(locale));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendEmail(Mail mail, String directory, Locale locale) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(sender.createMimeMessage());

        // Using a subfolder such as /templates here
        //freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate(directory + "/body.ftl", locale);
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());
        template = freemarkerConfig.getTemplate(directory + "/subject.ftl");
        String subject = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

        helper.setTo(mail.getMailTo());
        helper.setText(body, true);
        helper.setSubject(subject);

        //sender.send(helper.getMimeMessage());
        logger.info("Sending E-mail: " + body);
    }

    private Locale getLocaleFromString(String locale) {
        if (StringUtils.hasText(locale)) {
            switch (locale) {
                case "en":
                    return Locale.ENGLISH;
                case "pt":
                    return new Locale("pt","BR");
            }
        }
        return Locale.ENGLISH;
    }
}
