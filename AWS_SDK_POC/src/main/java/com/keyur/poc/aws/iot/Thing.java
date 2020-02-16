package com.keyur.poc.aws.iot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.Action;
import com.amazonaws.services.iot.model.AttachThingPrincipalRequest;
import com.amazonaws.services.iot.model.AttachThingPrincipalResult;
import com.amazonaws.services.iot.model.AttributePayload;
import com.amazonaws.services.iot.model.CreateCertificateFromCsrRequest;
import com.amazonaws.services.iot.model.CreateThingRequest;
import com.amazonaws.services.iot.model.CreateThingResult;
import com.amazonaws.services.iot.model.CreateThingTypeRequest;
import com.amazonaws.services.iot.model.CreateThingTypeResult;
import com.amazonaws.services.iot.model.CreateTopicRuleRequest;
import com.amazonaws.services.iot.model.CreateTopicRuleResult;
import com.amazonaws.services.iot.model.DeleteThingRequest;
import com.amazonaws.services.iot.model.DeleteThingResult;
import com.amazonaws.services.iot.model.DeleteThingTypeRequest;
import com.amazonaws.services.iot.model.DeleteThingTypeResult;
import com.amazonaws.services.iot.model.DynamoDBAction;
import com.amazonaws.services.iot.model.DynamoKeyType;
import com.amazonaws.services.iot.model.ListThingPrincipalsRequest;
import com.amazonaws.services.iot.model.ListThingPrincipalsResult;
import com.amazonaws.services.iot.model.ListThingTypesRequest;
import com.amazonaws.services.iot.model.ListThingTypesResult;
import com.amazonaws.services.iot.model.ListThingsRequest;
import com.amazonaws.services.iot.model.ListThingsResult;
import com.amazonaws.services.iot.model.ThingTypeProperties;
import com.amazonaws.services.iot.model.TopicRulePayload;
import com.keyur.poc.aws.util.Constants;

/**
 * Hello world!
 *
 */
public class Thing 
{
	
	public static String THING_TO_CREATE = "DOOR_SENSOR:44:55:66:DD:FF";
	public static String THING_TO_DELETE = "";
	public static String CERTIFICATE_TO_ATTACH = "arn:aws:iot:us-east-2:687625303272:cert/d3b4adb60460c7a17c5518c8b7064b257a983fc3b697e75a0e781b4747811a2a";
	public static String THING_TYPE = "DOOR_SENSOR";
	public static String ROLE_WITH_DYNAMO_DB_PERMISSION = "arn:aws:iam::300083543872:role/service-role/common-rule-role";
	public static String TOPIC_NAME_TO_PERSIST_DATA = "iot/"+THING_TO_CREATE;
	public static String DYANMODB_TABLE_NAME = "DOOR_SENSOR";
	public static String DYANMODB_TABLE_HASHKEY_FIELD = "index";  // this is column name from dynamo db
	public static String DYANMODB_TABLE_HASHKEY_VALUE = "${timestamp}";   // this is key value to match from data model coming from device, this needs to match with dynamo db column
	
	
    public static void main( String[] args )
    {
       
        
        AWSIot awsIotClient = client();
        
       
 //       createThings(awsIotClient);
//        listThings(awsIotClient);
//        deleteThing(awsIotClient);
//        getAttachCertificateOfThing(awsIotClient);
        createRule(awsIotClient);
        
    }
    
    
    /**
     * List of devices 
     * 
     * @param awsIotClient
     */
    public static void listThings(AWSIot awsIotClient) {
    	ListThingsRequest ltr = new ListThingsRequest();
    	ListThingsResult listThingResult = awsIotClient.listThings(ltr);
    	System.out.println("listThingResult = "+listThingResult);
    }
    
    /**
     * Create device and attach default certificate to device
     * @param awsIotClient
     */
    public static void createThings(AWSIot awsIotClient) {
    	
    	CreateThingRequest ctr = new CreateThingRequest();
    	ctr.setThingName(THING_TO_CREATE);
    	ctr.setThingTypeName(THING_TYPE);
    	HashMap<String, String> thingAttributeMap = new HashMap<String, String>();
    	thingAttributeMap.put("manufacturer", "M1");
    	thingAttributeMap.put("model", "M01");
    	thingAttributeMap.put("serialno", "44:55:66:DD:FF");
    	AttributePayload attributePayload = new AttributePayload();
    	attributePayload.setAttributes(thingAttributeMap);
    	ctr.setAttributePayload(attributePayload);
    	CreateThingResult cResult = awsIotClient.createThing(ctr);
    	System.out.println(" Create thing result = "+cResult);
    	
    	attachCertificateToThing(THING_TO_CREATE, awsIotClient);
    	
    	
    	
    }
    
    /**
     * Attach certificate to thing
     * 
     * @param thingName
     * @param awsIotClient
     */
    public static void attachCertificateToThing(String thingName,AWSIot awsIotClient) {
    	AttachThingPrincipalRequest attachCertiReq = new AttachThingPrincipalRequest();
    	attachCertiReq.setThingName(thingName);
    	attachCertiReq.setPrincipal(CERTIFICATE_TO_ATTACH);
    	
    	AttachThingPrincipalResult attachCertResult = awsIotClient.attachThingPrincipal(attachCertiReq);
    	
    }
    
    /**
     * Retrieve certificate information of thing
     * @param awsIotClient
     */
    public static void getAttachCertificateOfThing(AWSIot awsIotClient) {
    	ListThingPrincipalsRequest thingPrincipalReq = new ListThingPrincipalsRequest();
    	thingPrincipalReq.setThingName("testThing4");
    	ListThingPrincipalsResult lptr = awsIotClient.listThingPrincipals(thingPrincipalReq);
    	System.out.println(" Thing certificate = "+lptr);
    	
    }
    
 
    /**
     * Delete thing
     * 
     * @param awsIotClient
     */
    public static void deleteThing(AWSIot awsIotClient) {
    	DeleteThingRequest deleteThingType = new DeleteThingRequest();
    	deleteThingType.setThingName("testThing2");
    	DeleteThingResult dttr =awsIotClient.deleteThing(deleteThingType);
    	System.out.println("delete thing result = "+dttr);
    }
    public static void updateThing(AWSIot awsIotClient) {
 	
    }
    
    /**
     * Rule is created here, to persist data in dynamo db.
     * @param awsIotClient
     */
    public static void createRule(AWSIot awsIotClient) {
    	CreateTopicRuleRequest ctrr = new CreateTopicRuleRequest();
    	String ruleName = "RULE_"+THING_TO_CREATE;
    	String rName = ruleName.replace(":", "");
    	ctrr.setRuleName(rName);
    	TopicRulePayload rulePayload = new TopicRulePayload();
    	rulePayload.setSql("SELECT * FROM '"+TOPIC_NAME_TO_PERSIST_DATA+"' ");    	

    	/**
    	 *  Setting action for dynamo db
    	 */
    	Action ruleAction = new Action();
    	DynamoDBAction dynamoDbaction = new DynamoDBAction();
    	dynamoDbaction.setTableName(DYANMODB_TABLE_NAME);
    	dynamoDbaction.setHashKeyField(DYANMODB_TABLE_HASHKEY_FIELD);
    	dynamoDbaction.setHashKeyValue(DYANMODB_TABLE_HASHKEY_VALUE);
    	dynamoDbaction.setHashKeyType(DynamoKeyType.STRING);
    	dynamoDbaction.setRoleArn(ROLE_WITH_DYNAMO_DB_PERMISSION);
    	ruleAction.setDynamoDB(dynamoDbaction);    	    
    	
    	List<Action> actionList = new ArrayList<Action>();
    	actionList.add(ruleAction);
    	
    	rulePayload.setActions(actionList);
    	
    	ctrr.setTopicRulePayload(rulePayload);
    	
    	CreateTopicRuleResult ruleResult = awsIotClient.createTopicRule(ctrr);
    	System.out.println(" Created Rule repsonse = "+ruleResult);
    }
    
    /**
     * AWS connection object
     * 
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
