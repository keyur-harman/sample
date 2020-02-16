package com.keyur.poc.aws.iam;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.ListServerCertificatesRequest;
import com.amazonaws.services.identitymanagement.model.ListServerCertificatesResult;
import com.amazonaws.services.identitymanagement.model.ServerCertificateMetadata;

/**
 * Hello world!
 *
 */
public class User
{
	 public static void main(String[] args) {
		 

	        
	        BasicAWSCredentials creds = new BasicAWSCredentials("AKIAJPSJBD42UUIQWRPA", "vJUagJqm9XGfwBPxvlb7eM4rzuIYoU1R/NQmIPY2");

	        AmazonIdentityManagement iamConnection = client();
	        
	        // createUserOnAws(iamConnection);
	        
	        listAllServerCertificates(iamConnection);
	       
	    }
	 
	 
	 /**
	  * This will create user on AWS IAM
	  * @param iamConnection
	  */
	 public static void createUserOnAws(AmazonIdentityManagement iamConnection) {
		 String username = "keyurgandhi1";
		 CreateUserRequest request = new CreateUserRequest().withUserName(username);

	        CreateUserResult response = iamConnection.createUser(request);

	        System.out.println("Successfully created user: " +
	                response.getUser().getUserName());
	 }
	 
	 public static void listAllServerCertificates(AmazonIdentityManagement iamConnection) {
		 System.out.println("inside list all certi");
		  boolean done = false;
	        ListServerCertificatesRequest request =
	                new ListServerCertificatesRequest();

	        while(!done) {

	            ListServerCertificatesResult response =
	            		iamConnection.listServerCertificates(request);
	            System.out.println(" response = "+response);

	            for(ServerCertificateMetadata metadata :
	                    response.getServerCertificateMetadataList()) {
	                System.out.printf("Retrieved server certificate %s",
	                        metadata.getServerCertificateName());
	            }

	            request.setMarker(response.getMarker());

	            if(!response.getIsTruncated()) {
	                done = true;
	            }
	        }
	 }
	 
	 public static AmazonIdentityManagement client() {
		    return AmazonIdentityManagementClientBuilder.standard()
		            .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
		                //@Override
		                public String getAWSAccessKeyId() {
		                    return "AKIAJPSJBD42UUIQWRPA";
		                }

		                //@Override
		                public String getAWSSecretKey() {
		                    return "vJUagJqm9XGfwBPxvlb7eM4rzuIYoU1R/NQmIPY2";
		                }
		            }))		            
		            .withRegion(Regions.DEFAULT_REGION).build();
		}
}
