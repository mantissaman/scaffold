package com.bnp.arch.api.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.UUID;

import com.bnp.arch.api.model.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilFunctions {

    public static String uniqueId() {
        String _uid = "";
        try {
            UUID uuid = UUID.randomUUID();
            _uid = Long.toString(uuid.getMostSignificantBits(), 36) + '-'
                    + Long.toString(uuid.getLeastSignificantBits(), 36);

        } catch (Throwable e) {
            log.error("ERROR {}", e.toString());
        }
        return _uid;
    }

    public static String getRandomPassword(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static String getInviteEmailBody(User user, String password, String appUrl) {
        String emailBody = "<html><body style='font-size:15px; font-family: calibri, verdana, arial, sans-serif;'>";
        emailBody = emailBody + "Welcome " + user.getFirstName() + " " + user.getLastName() + ",<br><br>";
        emailBody = emailBody + "<div>Your account has been created. Please use below credentials to login.<br>";
        emailBody = emailBody + "<b>Emaiil Address </b>: " + user.getEmail() + "<br>" + "<b>Password </b>: " + password
                + "<br>";
        emailBody = emailBody + "Click <a href='" + appUrl + "'>here</a> to login.";
        emailBody = emailBody + "<div style='color:grey'><br><br>Kind Regards,<br>Claroty Team</div>";
        emailBody = emailBody + "</body></html>";
        return emailBody;
    }

    public static String getResetPasswordEmailBody(User user, String password, String appUrl) {
        String emailBody = "<html><body style='font-size:15px; font-family: calibri, verdana, arial, sans-serif;'>";
        emailBody = emailBody + "Dear " + user.getFirstName() + " " + user.getLastName() + ",<br><br>";
        emailBody = emailBody + "<div>Here are the new credentials to login.<br>";
        emailBody = emailBody + "<b>Emaiil Address </b>: " + user.getEmail() + "<br>" + "<b>Password </b>: " + password
                + "<br>";
        emailBody = emailBody + "Click <a href='" + appUrl + "'>here</a> to login.";
        emailBody = emailBody + "<div style='color:grey'><br><br>Kind Regards,<br>Claroty Team</div>";
        emailBody = emailBody + "</body></html>";
        return emailBody;
    }

    public static String getApprovalEmailBody(String quoteNumber, String appUrl) {
        String emailBody = "<html><body style='font-size:15px; font-family: calibri, verdana, arial, sans-serif;'>";
        emailBody = emailBody + "Dear Deal Desk,<br><br>";
        emailBody = emailBody + "<div>ELA Quote Number "+quoteNumber+" has been submitted for Approval.<br>";
        emailBody = emailBody + "Click <a href='" + appUrl + "'>here</a> to login.";
        emailBody = emailBody + "<div style='color:grey'><br><br>Kind Regards,<br>Claroty Team</div>";
        emailBody = emailBody + "</body></html>";
        return emailBody;
    }

    public static void deleteFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("ERROR {}", e.toString());
        }
    }
}
