package com.poc.crnk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.poc.crnk.model.Organization;
import com.poc.crnk.model.Role;
import com.poc.crnk.model.Staff;
import com.poc.crnk.util.CrnkUtil;

import io.crnk.core.engine.document.Document;
import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.document.Relationship;
import io.crnk.core.engine.document.Resource;
import io.crnk.core.engine.document.ResourceIdentifier;
import io.crnk.core.utils.Nullable;

@RestController
public class PocController {

	@Autowired
	private CrnkUtil mCrnkUtil;

	@RequestMapping("/data")
	public Object getData() {
		/**
		 * Initialize Object with data as not using any DB here
		 */
		Staff staffData = new Staff(12L, "Keyur", "Gandnhi", "keyur.gandhi@harman.com");
		Organization org = new Organization(1L, "Org1");
		Role role = new Role(2L, "C6 Admin");

		/**
		 * Preparing Attributes for ornganization
		 */
		Map<String, JsonNode> organizationAttributes = new HashMap<>();
		organizationAttributes.put("name", mCrnkUtil.convertStringToJsonNode(org.getName()));

		/**
		 * Preparing Attributes for role
		 */
		Map<String, JsonNode> roleAttributes = new HashMap<>();
		roleAttributes.put("name", mCrnkUtil.convertStringToJsonNode(role.getName()));

		/**
		 * Preparing Attributes for Staff
		 */
		Map<String, JsonNode> staffAttributes = new HashMap<>();
		staffAttributes.put("first_name", mCrnkUtil.convertStringToJsonNode(staffData.getFirstName()));
		staffAttributes.put("last_name", mCrnkUtil.convertStringToJsonNode(staffData.getLastName()));
		staffAttributes.put("email", mCrnkUtil.convertStringToJsonNode(staffData.getEmail()));
		
		/**
		 * set up self link for Staff object
		 */
		ObjectNode staffLinkObject = mCrnkUtil.getLinkObject("http://localhost:8080/self/link");

		/**
		 * Preparing Relationship for Staff, As it has two relationship organization and
		 * role Put both relationship in map.
		 */
		Map<String, Relationship> staffRelationShip = new HashMap<>();
		staffRelationShip.put(Organization.class.getSimpleName(),
				mCrnkUtil.getRelationshipObject(String.valueOf(org.getId()), Organization.class.getSimpleName(),staffLinkObject));
		staffRelationShip.put(Role.class.getSimpleName(),
				mCrnkUtil.getRelationshipObject(String.valueOf(role.getId()), Role.class.getSimpleName(),null));

		

		/**
		 * Final document for Staff object
		 */
		Resource resourceStaff = mCrnkUtil.getResourceObject(String.valueOf(staffData.getId()),
				Staff.class.getSimpleName(), staffAttributes, staffRelationShip, null);

		/**
		 * If included is required then need to include two relationship Organization
		 * and Role
		 */
		Resource includedResOrganization = mCrnkUtil.getResourceObject(String.valueOf(org.getId()),
				Organization.class.getSimpleName(), organizationAttributes, null, null);

		Resource includedResRole = mCrnkUtil.getResourceObject(String.valueOf(role.getId()), Role.class.getSimpleName(),
				roleAttributes, null, null);

		List<Resource> includedDocumentList = new ArrayList<>();
		includedDocumentList.add(includedResRole);
		includedDocumentList.add(includedResOrganization);

		/**
		 * Final document as response which consist data and included.
		 */
		return mCrnkUtil.getDataDocument(Nullable.of(resourceStaff), includedDocumentList, null, null);
	}

	@RequestMapping("/save")
	public Object saveData(@RequestBody Document document) {

		/**
		 * Collecting data document from primary object
		 */
		Nullable<Object> resourceData = document.getData();
		/**
		 * Collecting included list from primary object
		 */
		List<Resource> includedList = document.getIncluded();

		/**
		 * Fetching out Staff attributes and relationship data from Staff document
		 */
		Resource resourceStaff = (Resource) resourceData.get();
		Map<String, JsonNode> staffAttributes = resourceStaff.getAttributes();
		Map<String, Relationship> staffRelationship = resourceStaff.getRelationships();

		/**
		 * Converting staff document data to staff java object
		 */
		Staff staffUser = new Staff(Long.valueOf(resourceStaff.getId()), staffAttributes.get("first_name").textValue(),
				staffAttributes.get("last_name").textValue(), staffAttributes.get("email").textValue());
		System.out.println("staffUser object= " + staffUser);

		/**
		 * Fetching relationship data from document
		 */
		ResourceIdentifier relationshiopOrganizationData = mCrnkUtil
				.getRelationshipData(staffRelationship.get(Organization.class.getSimpleName()));
		System.out.println("Relationship Organization -- " + relationshiopOrganizationData.getId() + "-- "
				+ relationshiopOrganizationData.getType());

		ResourceIdentifier relationshiopRoleData = mCrnkUtil
				.getRelationshipData(staffRelationship.get(Role.class.getSimpleName()));
		System.out.println(
				"Relationship Role -- " + relationshiopRoleData.getId() + "-- " + relationshiopRoleData.getType());

		/**
		 * Get organization & role object from included list
		 */
		Organization orgnizationData = null;
		Role roleData = null;
		for (Resource resource : includedList) {
			if (resource.getType().equals(Organization.class.getSimpleName())) {
				Map<String, JsonNode> orgAttributes = resource.getAttributes();
				orgnizationData = new Organization(Long.valueOf(resource.getId()),
						orgAttributes.get("name").textValue());
			} else if (resource.getType().equals(Role.class.getSimpleName())) {
				Map<String, JsonNode> roleAttributes = resource.getAttributes();
				roleData = new Role(Long.valueOf(resource.getId()), roleAttributes.get("name").textValue());
			}
		}

		System.out.println("orgnizationData object= " + orgnizationData);
		System.out.println("roleData object= " + roleData);

		return null;
	}

	@RequestMapping("/errorResponse")
	public Object getError() {

		List<ErrorData> errorDataList = new ArrayList<ErrorData>();
		errorDataList.add(mCrnkUtil.getErrorBuilderData("SUM-01-101", "Input not valid", "Email id not valid", "sourcePointer",
				"sourceParam"));

		return mCrnkUtil.getErrorDocument(errorDataList, null);
	}

}
