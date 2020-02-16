package com.keyur.poc.aws.iot;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.CreateThingTypeRequest;
import com.amazonaws.services.iot.model.CreateThingTypeResult;
import com.amazonaws.services.iot.model.CreateTopicRuleRequest;
import com.amazonaws.services.iot.model.DeleteThingTypeRequest;
import com.amazonaws.services.iot.model.DeleteThingTypeResult;
import com.amazonaws.services.iot.model.ListThingTypesRequest;
import com.amazonaws.services.iot.model.ListThingTypesResult;
import com.amazonaws.services.iot.model.ThingTypeProperties;
import com.keyur.poc.aws.util.Constants;

public class ThingType {
	
	public static String THING_TYPE_TO_CREATE = "DOOR_SENSOR";
	public static String THING_TYPE_TO_DELETE = "DOOR_SENSOR";	
	
	  public static void main( String[] args )
	    {
	       
	        
	        AWSIot awsIotClient = client();
	
	        listThingTypes(awsIotClient);
//	        createThingType(awsIotClient);
//	        deleteThingtype(awsIotClient);


	        
	    }
	    
	    /**
	     * Thing type creation
	     * @param awsIotClient
	     */
	    public static void createThingType(AWSIot awsIotClient) {
	    	CreateThingTypeRequest ctt = new CreateThingTypeRequest();
	    	ctt.setThingTypeName(THING_TYPE_TO_CREATE);
	    	ThingTypeProperties thingTypeProperty = new ThingTypeProperties();
	    	thingTypeProperty.setThingTypeDescription("This is created to identify android phone.");
	    	ctt.setThingTypeProperties(thingTypeProperty);
	    	CreateThingTypeResult createThingTypeResult = awsIotClient.createThingType(ctt);
	    	System.out.println(" Create thing type result = "+createThingTypeResult);
	    }
	    
	    /**
	     * List thing types
	     * @param awsIotClient
	     */
	    public static void listThingTypes(AWSIot awsIotClient) {
	    	ListThingTypesRequest ltr = new ListThingTypesRequest();
	    	ListThingTypesResult listThingTypeResult = awsIotClient.listThingTypes(ltr);
	    	System.out.println("listThingTypeResult = "+listThingTypeResult);
	    }
	    
	    
	    /**
	     * Delete thing type
	     * 
	     * Can't directly delete the thing type, 
	     * first need to deprecated the thing type then wait for 5 minutes and then able to delete it
	     * 
	     * @param awsIotClient
	     */
	    public static void deleteThingtype(AWSIot awsIotClient) {
	    	DeleteThingTypeRequest deleteThingType = new DeleteThingTypeRequest();
	    	deleteThingType.setThingTypeName(THING_TYPE_TO_DELETE);
	    	DeleteThingTypeResult dttr =awsIotClient.deleteThingType(deleteThingType);
	    	System.out.println("delete thing type result = "+dttr);
	    }
	    
	 
	 
	    /**
	     * Connection object for AWS
	     * @return
	     */
	    public static AWSIot client() {
		    return AWSIotClientBuilder.standard()
		            .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
		                //@Override
		                public String getAWSAccessKeyId() {
		                    return Constants.ACCESS_KEY;
		                    
		                }

		                //@Override
		                public String getAWSSecretKey() {
		                    return Constants.SECRET_ACCESS_KEY;
		                }
		            }))		            
		            .withRegion(Regions.US_EAST_2).build();
		}

}
