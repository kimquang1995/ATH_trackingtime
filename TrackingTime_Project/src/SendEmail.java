import java.util.Properties;


public class SendEmail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		private static String SMPT_HOSTNAME = "";
	    private static String USERNAME = "";
	    private static String PASSWORD = "";

	    public static void main(String[] args) {

	        // Recipient's email ID needs to be mentioned.
	        String to = "abcd@gmail.com";

	        // Sender's email ID needs to be mentioned
	        String from = "web@gmail.com";

	        // Assuming you are sending email from localhost
	        // String host = "localhost";

	        // Get system properties
	        Properties properties = System.getProperties();

	        // Setup mail server
	        properties.setProperty("mail.smtp.host", SMPT_HOSTNAME);

	        // Get the default Session object.
	        // Session session = Session.getDefaultInstance(properties);

	        // create a session with an Authenticator
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(USERNAME, PASSWORD);
	            }
	        });

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
	                    to));

	            // Set Subject: header field
	            message.setSubject("This is the Subject Line!");

	            // Now set the actual message
	            message.setText("This is actual message");

	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	    }
	}

}
