package google.api.gmail.send;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws MessagingException {

		String clientId = "xxxxx";
		String clientSecret = "yyyyy";
		String refreshToken = "zzzzz";

		GmailAPI gmailAPI = new GmailAPI(clientId, clientSecret, refreshToken);

		List<String> recipientList = new ArrayList<String>();
		recipientList.add("to@gmail.com");
		String from = "from@gmail.com";
		
		String subject = "Google API Gmail - Subject";
		String bodyText = "Hello World!";

		MimeMessage mmsg = gmailAPI.createEmail(recipientList, from, subject, bodyText);

		com.google.api.services.gmail.model.Message message = gmailAPI.createMessageWithEmail(mmsg);

		gmailAPI.sendMessage(from, message);
	}
}
