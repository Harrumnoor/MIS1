package Activiti;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;


public class EmailSender implements JavaDelegate
{
	Expression to, from, subject, bcc, text, cc;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception
	{
		System.out.println();
		System.out.println("Sending Email...");
		System.out.println();
		
		
		String To=(String) to.getValue(execution);
		//String From=(String) from.getValue(execution);
		String Subject=(String) subject.getValue(execution);
		//String Bcc=(String) bcc.getValue(execution);
		String Text=(String) text.getValue(execution);
		//String Cc=(String) cc.getValue(execution);
		
		System.out.println("To: "+To);
		//System.out.println("From: "+From);
		System.out.println("Subject: "+Subject);
		System.out.println("Text: "+Text);
		
	   	final String username = "smartappfyp@gmail.com";
		final String password = "smartapp";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.required", "true");
		
		
		
		
		/*props.put("mail.smtp.user",username); 
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.port", "25"); 
		props.put("mail.debug", "true"); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.EnableSSL.enable","true");

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.setProperty("mail.smtp.socketFactory.fallbac k", "false"); 
		props.setProperty("mail.smtp.port", "587"); 
		props.setProperty("mail.smtp.socketFactory.port", "587"); */

		Session session = Session.getInstance(props,
		new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try 
		{
			System.out.println("DOING.....");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sana.shah94.ss@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
			//message.addRecipient(Message.RecipientType.BCC, new InternetAddress(Bcc));
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(Cc));
			message.setSubject(Subject);
			message.setText(Text);
			System.out.println("DOING");
			Transport.send(message);
			System.out.println();
			System.out.println("Email Sent");
			System.out.println();
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
		
		
	}

}
