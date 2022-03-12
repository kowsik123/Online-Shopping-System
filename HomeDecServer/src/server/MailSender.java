package server;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {
	public static void sendMail(String htmlCode,String to) {
		
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
        Authenticator a =new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailAddress.EMAIL, EmailAddress.PASSWORD );
            }
        };
        
        Session s=Session.getInstance(props, a);
        
        Message msg=new MimeMessage(s);
        
        try {
			msg.setFrom(new InternetAddress(EmailAddress.EMAIL));
			
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to) );
			
			msg.setSubject("Reset your password");
			
			msg.setText("");
			
			MimeMultipart multipart = new MimeMultipart("related");
			
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText =htmlCode;
			messageBodyPart.setContent(htmlText, "text/html");
			multipart.addBodyPart(messageBodyPart);
			
			msg.setContent(multipart);
			
			Transport.send(msg);
			
			System.out.println("message sent");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        
	}
}