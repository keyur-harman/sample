package com.harman.poc.aws.iot;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Lists;
//import com.prahs.tie.plugin.configmanagement.exceptions.MessageValidationException;
//import com.prahs.tie.plugin.util.models.APIMap;
//import com.prahs.tie.plugin.util.models.Api;
//import com.prahs.tie.plugin.util.models.CustomAmqp;

public class StringMatcherMain {
	
//	public static String inputString = "/studies/123456/subjects";
	public static String inputString = "/studies/123456/subjects/prajay learning/datasets/regular/9876543";
//	public static String inputString = "/studies/123456/subjects/prajay/datasets/regular/9876543/9876543/9876543";
	public static ArrayList<String> apiList = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		apiList.add("/studies/#{study_oid}/subjects/#{subject_name}/datasets/regular/#{form_oid}");
		apiList.add("/studies/#{study_oid}/subjects/#{subject_name}/dataset/regular/#{form_oid}");
		apiList.add("/studies/#{study_oid}/Subjects");
		apiList.add("/studies/#{study_oid}/subjects");
		
		String matchedAPI = "";
		for (String api : apiList) {
			
			if(isRequestMatchWithApiDefinition_Optimized(inputString, api)) {
				matchedAPI = api;
				break;
			}
		}
		
		System.out.println("Matched API definition = "+matchedAPI);
	}
	
	/**
	 * Here first will split the request API and API definition with charachter '/'
	 * Then will match the size of the array.
	 *  if size does not match, then the method will return false.
	 * if size matches for both array, then further will go element by element.
	 * The element which start with '#' will be ignored as it is the actual value expected from the requestor.
	 * If all the elements matches then the method will return true.
	 * 
	 * @param requestAPI
	 * @param apiDefinition
	 * @return
	 */
	public static boolean isRequestMatchWithApiDefinition_Optimized(String requestAPI, String apiDefinition) {
		requestAPI = requestAPI.substring(1);
		apiDefinition = apiDefinition.substring(1);
		 String[] requestApiInArray = requestAPI.split("/");
		 String[] apiDefinitionInArray = apiDefinition.split("/");
		 ArrayList<String> requestApiList = Lists.newArrayList(requestApiInArray);
		 ArrayList<String> apiDefinitionList = Lists.newArrayList(apiDefinitionInArray);
		 System.out.println("requestApiInArray = "+requestApiList+" size = "+requestApiList.size());
		 System.out.println("apiDefinitionInArray = "+apiDefinitionList+" size = "+apiDefinitionList.size());
		 
		 if(requestApiInArray.length == apiDefinitionInArray.length) {
			 for(int i=0;i<requestApiInArray.length;i++) {
				 System.out.println("From request = "+requestApiList.get(i)+" From api def = "+apiDefinitionList.get(i));
				 String apiDefinitionFirstCharachter = String.valueOf(apiDefinitionList.get(i).charAt(0));
				 System.out.println("apiDefinitionFirstCharachter = "+apiDefinitionFirstCharachter);
				 
				 if(!apiDefinitionFirstCharachter.equals("#")) {
					 if(!requestApiList.get(i).equals(apiDefinitionList.get(i))) {
						 System.out.println("not matching");
						 return false;
					 }
				 }				 
			 }
			 return true;
		 }else {
			 return false;
		 }
	}
	
	public static boolean isRequestMatchWithApiDefinition(String requestAPI, String apiDefinition) {
		requestAPI = requestAPI.substring(1);
		apiDefinition = apiDefinition.substring(1);
		 String[] requestApiInArray = requestAPI.split("/");
		 String[] apiDefinitionInArray = apiDefinition.split("/");
		 ArrayList<String> requestApiList = Lists.newArrayList(requestApiInArray);
		 ArrayList<String> apiDefinitionList = Lists.newArrayList(apiDefinitionInArray);
		 System.out.println("requestApiInArray = "+requestApiList+" size = "+requestApiList.size());
		 System.out.println("apiDefinitionInArray = "+apiDefinitionList+" size = "+apiDefinitionList.size());
		 
		 if(requestApiInArray.length == apiDefinitionInArray.length) {
			 for(int i=0;i<requestApiInArray.length;i++) {
				 System.out.println("From request = "+requestApiList.get(i)+" From api def = "+apiDefinitionList.get(i));
				 String apiDefinitionFirstCharachter = String.valueOf(apiDefinitionList.get(i).charAt(0));
				 System.out.println("apiDefinitionFirstCharachter = "+apiDefinitionFirstCharachter);
				 
				 if(!apiDefinitionFirstCharachter.equals("#")) {
					 if(requestApiList.get(i).equals(apiDefinitionList.get(i))) {
						 System.out.println("matching");
					 }else {
						 System.out.println("not matching");
						 return false;
					 }
				 }else {
					 System.out.println("has # = "+apiDefinitionList.get(i));
				 }
				 
			 }
			 return true;
		 }else {
			 return false;
		 }
		 
	}
	
//	  public void validateRequestPathAndMethod(Api api, CustomAmqp customAmqp)
//		      throws MessageValidationException, URISyntaxException, UnsupportedEncodingException,
//		      MalformedURLException {
//		    String requestPath = customAmqp.getSourceBean().getRequestPath();
//		    requestPath = "/" + requestPath.split("/")[2];
//		    String requestMethod = customAmqp.getSourceBean().getRequestMethod();
//		    boolean isRequestValid = false;
//		    for (Map.Entry<String, APIMap> entry : api.getApiUniqueIdentifier().entrySet()) {
////		      if (entry.getValue().getPath().equals(requestPath.split("\\?")[0])
////		          && entry.getValue().getMethod().equals(requestMethod)) {
////		        isRequestValid = true;
////		        validateAdditionalParametersInRequestPath(entry.getValue(), requestPath);
////		      }
//		    if(entry.getValue().getMethod().equals(requestMethod)) {    // Matching API method name first
//		    	String apiPathWithPathVariable =  requestPath.split("\\?")[0];      // spliting request path with ?, So we can seperate out query params from URL
//		    	String apiPathDefinitionFromConfig = entry.getValue().getPath();    // API path name from config where we may have path variable in it
//		    	
//		    	if(isRequestMatchWithApiDefinition_Optimized(apiPathWithPathVariable, apiPathDefinitionFromConfig)) {  // Here if request path is matching with API path definition from config
//		    		isRequestValid = true;
//		    		validateAdditionalParametersInRequestPath(entry.getValue(), requestPath);
//		    	}
//		    }
//		    	
//		    }
//
//		    if (!isRequestValid) {
//		      throw new MessageValidationException("TIE-001-012", "Validation error",
//		          "Configuration based message validation failed for requestMethod and requestPath.");
//		    }
//		  }

}
