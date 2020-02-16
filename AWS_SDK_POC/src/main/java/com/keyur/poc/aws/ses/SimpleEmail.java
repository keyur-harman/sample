package com.keyur.poc.aws.ses;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import java.io.IOException;

public class SimpleEmail {

//Replace sender@example.com with your "From" address.
  // This address must be verified with Amazon SES.
  static final String FROM = "shs.parallel6@gmail.com";

  // Replace recipient@example.com with a "To" address. If your account
  // is still in the sandbox, this address must be verified.
  static final String TO = "keyur21.gandhi@gmail.com";

  // The configuration set to use for this email. If you do not want to use a
  // configuration set, comment the following variable and the
  // .withConfigurationSetName(CONFIGSET); argument below.
  static final String CONFIGSET = "ConfigSet";

  // The subject line for the email.
  static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

  // The HTML body for the email.
  static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
      + "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
      + "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>"
      + "AWS SDK for Java</a>";

  // The email body for recipients with non-HTML email clients.
  static final String TEXTBODY = "This email was sent through Amazon SES "
      + "using the AWS SDK for Java.";

  public static AmazonSimpleEmailService client() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
          // @Override
          public String getAWSAccessKeyId() {
            return "ACCESS KEY";
          }

          // @Override
          public String getAWSSecretKey() {
            return "SECRET_ACCESS_KEY";
          }
        })).withRegion(Regions.US_EAST_2).build();
  }

  public static void main(String[] args) throws IOException {

    try {
//      final AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
//          // Replace US_WEST_2 with the AWS Region you're using for
//          // Amazon SES.
//          .withRegion(Regions.US_WEST_2).build();
      final AmazonSimpleEmailService client = client();
      final SendEmailRequest request = new SendEmailRequest()
          .withDestination(new Destination().withToAddresses(TO))
          .withMessage(new Message()
              .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(HTMLBODY))
                  .withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
              .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
          .withSource(FROM);
      // Comment or remove the next line if you are not using a
      // configuration set
//          .withConfigurationSetName(CONFIGSET);
      client.sendEmail(request);
      System.out.println("Email sent!");
    } catch (final Exception ex) {
      System.out.println("The email was not sent. Error message: " + ex.getMessage());
    }
  }

}
