package org.hxy.shop.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    public static void sendMail(String to, String code) {
        /**
         * 1.获得一个Session对象
         * 2.创建一个代表邮件的对象Message
         * 3.发送邮件Transport
         */
        Properties props = new Properties();
        props.setProperty("mail.host","localhost");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("service@shop.com","111");
            }
        });

        Message message = new MimeMessage(session);
        try {
            //设置发件人
            message.setFrom(new InternetAddress("service@shop.com"));
            //设置收件人
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            //设置标题
            message.setSubject("来自购物天堂首领宇神的官方激活邮件");
            //设置正文
            message.setContent("<h1>购物天堂首领宇神的官方激活邮件！" +
                    "点下面的链接完成激活操作！" +
                    "</h1><h3><a href='http://169.254.42.239:8888/" +
                    "user_active.action?code="+code+"'>" +
                    "http://169.254.42.239:8888/user_active.action?code="+code+"</a></h3>",
                    "text/html;charset=UTF-8");
            //发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMail("aaa@shop.com","111111111111111111111");
    }
}
