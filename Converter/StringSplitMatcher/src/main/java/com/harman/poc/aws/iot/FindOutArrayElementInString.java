package com.harman.poc.aws.iot;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class FindOutArrayElementInString {

	static String test1 = "BODY.ODM.ClinicalData[100].StudyOID";
	static String test2 = "BODY.ODM.ClinicalData[0].StudyOID";
	static String test3 = "BODY.ODM.ClinicalData[100000000].StudyOID";
	static String test4 = "BODY.ODM.ClinicalData[*].StudyOID";
	static String test5 = "BODY.ODM.ClinicalData[#].StudyOID";
	static String test6 = "BODY.ODM.ClinicalData[0].SubjectData[0].StudyEventData[0].FormData[0].ItemGroupData[0].ItemData";
	static String test7 = "BODY.ODM.ClinicalDat[a[100]].StudyOID";
	static String test8 = "BODY.ODM.ClinicalDat[a[100]].StudyOID";
	static String test9 = "BODY.ODM.ClinicalDat[100].StudyOID]";
	static String test10 = "BODY.ODM.ClinicalDat[-100].StudyOID";

	public static void main(String[] args) {

		String[] jsonkeyInArray = test10.split("\\.");
		ArrayList<String> jsonKeyInList = Lists.newArrayList(jsonkeyInArray);
		System.out.println("jsonKeyInList = " + jsonKeyInList + " size = " + jsonKeyInList.size());
		for (String jsonKeyInString : jsonKeyInList) {
			if (isArrayIndexAvailable(jsonKeyInString)) {
				System.out.println("Available key with array element ====================== " + jsonKeyInString);
				String indexValue = getArrayIndexValue(jsonKeyInString);
				System.out.println("indexvalue = " + indexValue);
				if (isArrayIndexValid(indexValue)) {
					System.out.println("Array Index is valid");
					/**
					 * Get value from specific JSON Array and put it in the output JSON
					 */
				} else {
					System.out.println("Array Index is Invalid");
					/**
					 * Throw exception invalid array index and stop processing the mapping
					 */
				}
			} else {

				/**
				 * Continue with normal mapping
				 */
			}

		}

	}

	public static boolean isArrayIndexAvailable(String stringWithArrayIndex) {
		if (String.valueOf(stringWithArrayIndex.charAt(stringWithArrayIndex.length() - 1)).equals("]")) {
			if (stringWithArrayIndex.indexOf("[") >= 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static String getArrayIndexValue(String stringWithArrayIndex) {
		int indexOfEndofArrayParanthesis = stringWithArrayIndex.length() - 1;
		int indexOfStartOfArrayParanthesis = stringWithArrayIndex.indexOf("[");
		return stringWithArrayIndex.substring(indexOfStartOfArrayParanthesis + 1, indexOfEndofArrayParanthesis);
	}

	/**
	 * Valid index values are any integer value and *
	 * 
	 * @param arrayIndexValue
	 * @return
	 */
	public static boolean isArrayIndexValid(String arrayIndexValue) {
		try {
			if (arrayIndexValue.equals("*") || Integer.valueOf(arrayIndexValue) >= 0) {
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println("Not a valid integer number "+e.getLocalizedMessage().toString());
		}
		return false;
	}

}
