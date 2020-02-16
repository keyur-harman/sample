package com.kg.poc.crnk.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.crnk.core.engine.document.Document;
import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.document.Relationship;
import io.crnk.core.engine.document.Resource;
import io.crnk.core.engine.document.ResourceIdentifier;
import io.crnk.core.utils.Nullable;

/**
 * This class is an utility class to perform JSON object conversion with the
 * help of crnk client library.
 * 
 * @author inkgandhi
 *
 */
@Component
public class CrnkUtil {

	/**
	 * key for the link object type self
	 */
	public static final String SELF_LINK = "self";

	/**
	 * key for the link object type related
	 */
	public static final String RELATED_LINK = "related";

	/**
	 * This method convert value from string format to JsonNode format
	 * 
	 * @param value
	 * @return JsonNode
	 */
	public JsonNode convertStringToJsonNode(String value) {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.valueToTree(value);
	}

	/**
	 * This method creates reference of the JSON object based on input as an ID of
	 * the object and class name as an object type
	 * 
	 * @param id
	 * @param objectType
	 * @return Relationship
	 */
	public Relationship getRelationshipObject(String id, String objectType) {
		Relationship relationshipObject = new Relationship();
		relationshipObject.setData(Nullable.of(new ResourceIdentifier(id, objectType)));
		return relationshipObject;

	}

	/**
	 * This method return ObjectNode for the self link based on input link
	 * 
	 * @param selfLink
	 * @return ObjectNode
	 */
	public ObjectNode getLinkObject(String selfLink) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNodeLink = mapper.createObjectNode();
		objectNodeLink.put(SELF_LINK, selfLink);
		return objectNodeLink;

	}

	/**
	 * This method return ObjectNode for the self link and related link based on
	 * input link
	 * 
	 * @param selfLink
	 * @param relatedLink
	 * @return ObjectNode
	 */
	public ObjectNode getLinkObject(String selfLink, String relatedLink) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNodeLink = mapper.createObjectNode();
		objectNodeLink.put(SELF_LINK, selfLink);
		objectNodeLink.put(RELATED_LINK, relatedLink);
		return objectNodeLink;

	}

	/**
	 * This method form the resource object based on required input parameter
	 * 
	 * @param id
	 * @param objectType
	 * @param attributes
	 * @param relationships
	 * @param links
	 * @return Resource object
	 */
	public Resource getResourceObject(String id, String objectType, Map<String, JsonNode> attributes,
			Map<String, Relationship> relationships, ObjectNode links) {
		Resource resourceObject = new Resource();
		resourceObject.setId(id);
		resourceObject.setType(objectType);
		if (attributes != null)
			resourceObject.setAttributes(attributes);
		if (relationships != null)
			resourceObject.setRelationships(relationships);
		if (links != null)
			resourceObject.setLinks(links);
		return resourceObject;
	}

	/**
	 * This method returns ResourceIdentifier for relationship object.
	 * 
	 * @param relationshipObject
	 * @return ResourceIdentifier
	 */
	public ResourceIdentifier getRelationshipData(Relationship relationshipObject) {
		Nullable<Object> resid = relationshipObject.getData();
		ResourceIdentifier resIdentifier = (ResourceIdentifier) resid.get();
		return resIdentifier;
	}

	/**
	 * This method returns the ErroData object with required input
	 * 
	 * @param code
	 * @param title
	 * @param detail
	 * @param sourcePointer
	 * @param sourceParameter
	 * @return ErrorData
	 */
	public ErrorData getErrorData(String code, String title, String detail, String sourcePointer,
			String sourceParameter) {
		return new ErrorData(null, null, null, code, title, detail, sourcePointer, sourceParameter, null);
	}

	/**
	 * This method return the Response document for Data object
	 * 
	 * @param data
	 * @param includedList
	 * @param links
	 * @param jsonapi
	 * @return Document
	 */
	public Document getDataDocument(Nullable<Object> data, List<Resource> includedList, ObjectNode links,
			ObjectNode jsonapi) {
		Document document = new Document();
		if (data != null)
			document.setData(data);
		if (includedList != null)
			document.setIncluded(includedList);
		if (links != null)
			document.setLinks(links);
		if (jsonapi != null)
			document.setJsonapi(jsonapi);
		return document;
	}

	/**
	 * This method return the Response document for Error object
	 * 
	 * @param errorList
	 * @param jsonapi
	 * @return
	 */
	public Document getErrorDocument(List<ErrorData> errorList, ObjectNode jsonapi) {
		Document document = new Document();
		if (errorList != null)
			document.setErrors(errorList);
		if (jsonapi != null)
			document.setJsonapi(jsonapi);
		return document;
	}
}
