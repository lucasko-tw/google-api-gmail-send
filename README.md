Google API Gmail - Send Message
===============================================================
### Authorization Scopes
	https://www.googleapis.com/auth/gmail.compose

### To Initial Google API 
```JAVA
	GmailAPI gmailAPI = new GmailAPI(clientId, clientSecret, refreshToken);
```
### Setting Message
```JAVA
	List<String> recipientList = new ArrayList<String>();
	recipientList.add("to@gmail.com");
	String from = "from@gmail.com";
	String subject = "Google API Gmail - Subject";
	String bodyText = "Hello World!";
```
### To Create Google Message of Mail
```JAVA
	MimeMessage mmsg = gmailAPI.createEmail(recipientList, from, subject, bodyText);

	com.google.api.services.gmail.model.Message message = gmailAPI.createMessageWithEmail(mmsg);
```
### Send
```JAVA
	gmailAPI.sendMessage(from, message);
```
