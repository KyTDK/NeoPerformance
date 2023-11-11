package com.neomechanical.neoperformance.utils.mail;

import com.neomechanical.neoperformance.NeoPerformance;
import com.neomechanical.neoperformance.performance.managers.DataManager;
import org.bukkit.Bukkit;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

public class EmailClient {
    private final String outgoingHost;
    private final String senderEmail;
    private final String[] recipients;
    private final String senderPassword;
    private final int outgoingPort;

    public EmailClient(DataManager dataManager) {
        outgoingHost = dataManager.getPerformanceConfig().getEmailNotifications().getMailServerHost();
        senderEmail = dataManager.getPerformanceConfig().getEmailNotifications().getMailServerUsername();
        recipients = dataManager.getPerformanceConfig().getEmailNotifications().getRecipients().toArray(new String[0]);
        senderPassword = dataManager.getPerformanceConfig().getEmailNotifications().getMailServerPassword();
        outgoingPort = dataManager.getPerformanceConfig().getEmailNotifications().getMailServerPort();
    }

    public void sendAsHtml(String title, String html) {
        Bukkit.getScheduler().runTaskAsynchronously(NeoPerformance.getInstance(), () -> {
            Session session = createSession();
            //create message using session
            Message message = new MimeMessage(session);
            for (String s : recipients) {
                try {
                    prepareEmailMessage(message, s, title, html);
                } catch (MessagingException | IOException e) {
                    throw new RuntimeException(e);
                }
                //sending message
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void prepareEmailMessage(Message message, String to, String title, String html)
            throws MessagingException, IOException {
        BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));
        message.setSubject(title);
        message.setFrom(new InternetAddress(senderEmail));
        message.setSentDate(new Date());
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        collect(in, message, html);
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.host", outgoingHost); //Outgoing server (SMTP) - change it to your SMTP server
        props.put("mail.smtp.port", outgoingPort);//Outgoing port
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }
    private void collect(BufferedReader in, Message msg, String body)
            throws MessagingException, IOException {
        String subject = msg.getSubject();
        String sb = "<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>\n" +
                subject + "\n" +
                "</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<H1>" + subject + "</H1>" + "\n" +
                "<P>\n" +
                body + "\n" +
                "</P>\n" +
                "</BODY>\n" +
                "</HTML>\n";
        msg.setDataHandler(new DataHandler(new ByteArrayDataSource(sb, "text/html")));
    }
}