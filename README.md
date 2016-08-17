Google API Gmail - Send Message
===============================================================


### To initial Google API 
	GmailAPI gmailAPI = new GmailAPI(clientId, clientSecret, refreshToken);

### Setting Message
	List<String> recipientList = new ArrayList<String>();
	recipientList.add("to@gmail.com");
	String from = "from@gmail.com";
	String subject = "Google API Gmail - Subject";
	String bodyText = "Hello World!";

### To create Google Message of Mail
	MimeMessage mmsg = gmailAPI.createEmail(recipientList, from, subject, bodyText);

	com.google.api.services.gmail.model.Message message = gmailAPI.createMessageWithEmail(mmsg);

### Send
	gmailAPI.sendMessage(from, message);
