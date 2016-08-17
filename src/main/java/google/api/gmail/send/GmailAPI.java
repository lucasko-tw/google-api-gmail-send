package google.api.gmail.send;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

public class GmailAPI {
	
	private static HttpTransport HTTP_TRANSPORT;
	private Credential CREDENTIAL;
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private String APPLICATION_NAME = "GOOGLE-API-GMAIL";
	private Gmail GMAIL;

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	public GmailAPI(String clientId, String clientSecret, String refreshToken) {

		CREDENTIAL = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
				.setClientSecrets(clientId, clientSecret).build();
		CREDENTIAL.setRefreshToken(refreshToken);
	 
		GMAIL = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, CREDENTIAL).setApplicationName(APPLICATION_NAME)
				.build();
	}
	
	public boolean sendMessage(String userKey, Message message) {
		try {
			Message msg = GMAIL.users().messages().send(userKey, message).execute();
			return true;
		} catch (IOException e) {
			System.out.println("send message Error:" + e.getMessage());

			e.printStackTrace();
			return false;
		}

	}
	
	
	
	

	public Message createMessageWithEmail(MimeMessage emailContent) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			emailContent.writeTo(buffer);
		} catch (Exception e) {
			System.out.println("createMessageWithEmail error: " + e.getMessage());
			e.printStackTrace();
		}
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

	public MimeMessage createEmail(List<String> recipientList, String from, String subject, String bodyText)
			throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		InternetAddress[] recipientAddress = new InternetAddress[recipientList.size()];
		int counter = 0;
		for (String recipient : recipientList) {
			recipientAddress[counter] = new InternetAddress(recipient.trim());
			counter++;
		}
		InternetAddress fromEmail = null;
		try {
			fromEmail = new InternetAddress(from, "TEST Gmail Example");
		} catch (UnsupportedEncodingException e) {
			fromEmail = new InternetAddress(from);
			e.printStackTrace();
		}
		email.setFrom(fromEmail);
		email.addRecipients(javax.mail.Message.RecipientType.TO, recipientAddress);
		email.setSubject(subject);
		email.setText(bodyText);
		return email;
	}

	
}
