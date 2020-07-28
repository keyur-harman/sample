package com.java.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JSONtoXML {

	public static void main(String[] args) throws JSONException {
//		String json = "{employee : { age:30, name : Raja, technology:Java}}";
		String json = "{\"config_id\":\"C6.SOS\",\"is_enabled\":true,\"is_deleted\":false,\"general_config\":{\"connection_name\":\"SOS\",\"connection_description\":\"SOS service configurations\",\"branding\":{\"icon\":\"iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAADgUlEQVR4Xu2aWciOQRTHf59I+LiRpSTZblCIuLFF4YI7RLiS\\/bNk37Mku8gSuVC4sJcrF4TIDdlJKcWVUm6sZe9fZ2r6en3eeZ\\/nW955Z27eet6ZM\\/P\\/zzlnzpwzVVR4q6pw\\/CQCkgZUOAPJBCpcAZITTCaQTKDCGShkAr2AUUAnaDI+4hvwBLgF\\/Mxzz3wCugGHgQl5TpCzrLfAEuBKXnIdAX2BG0DHvATXs5zlwP485hABrYHnQHdTr93AeeAd8DuPSXKQ0QYYAWyxdUrkWOBaVtkiYCUg0GqTgEtZhdbjeGnoQ6AL8AzoD\\/zJMF87ESCBA4H7wJAMwhpq6DJgn03WD3hRwsQtgXnAWhHwFWgFHAUWliCsoYcMA+7YpFOACwELaAZMAw4C7YFfIsCpkFhdESCssboONm3V\\/DOBM0UsRDjlM3YCA\\/z+lUCACNsFjC5EVMwEKKDbDshMXPtupt4BmB6rCSiC3QTMAZobcpm5TEXf3wAHLKCKyge0NR+mIElxg2tX5e0tlHbfoiJAQdtcYCMg1XbtHrDa7g+1zT8aAnR0jwd6eAhfAeuAy3UESdEQ4O+sQvfNwEngx3+OxrImYAZw2gP40Y45BTdfiogJ1KWsCVDwc8qACrzU\\/0ORwKNwgj4Bx4D5geCj0oBEQNKAZALJByQnmE6BcAaiCYQU+SkQeh\\/IQVkToESGnwb7DOy1OsGnIokoawL8nKCPV1qwFTgBKPNTV4uGgEN2He7toX0NbLDizr8KO9EQoHvBOWCWXYWVDnNN9Q4lRK4XUIWoCHD+oBpYCqwClB5zTQSsAR5436IkwOFTWmw9sABo4YE+a6YhE4maAIdZxd5tlv523\\/S24LhVhabGmhavbfKqe+4AxhU6FmIujNTGO8ZSZ4P8P0SAzkzZyhGgpshAojG7DQdu2wImAxcDFqPiqJ4AKH\\/Y2ZnAY6uzK48+NEBYY3VVAXePTd4HeFnCQrThs+UspQE6NlQ8VAtltIS5Mw3Rrj2y3dOv1DnLA4lqEaAy0lO7VMhLqkzunshkEZ4JqTfYrVFPZJT372qgZdM3s05Sro+kFgMKgzM3\\/5mcmJUjnJhZav0JUBCzCFDBM5dW6KFkT2CkFRqbylti3fv1UPKuPHcuyE1IUwGYJ6YgWYmAILoi7Jw0IMJNDYKUNCCIrgg7Jw2IcFODICUNCKIrws5\\/AfHlC8s3uNMhAAAAAElFTkSuQmCC\",\"icon_mime_type\":\"image\\/png\"},\"hot_connector\":{\"binary_file\":\"java-plugin-1.jar\",\"class_name\":\"com.pra.pluginImpl\"},\"Authorization\":{\"api_key\":{\"can_override\":true,\"auth_params\":{\"user-key\":\"alsweirsldmfwrepwpoip\"},\"input_type\":\"header\"}},\"communications\":{\"type\":\"Synchronous\",\"request_timeout\":60,\"retry\":3},\"api\":{\"f45ca8ab-b57f-4f73-a7e0-a3f9e9f192a4\":{\"path\":\"\\/blood-pressuer-data\",\"method\":\"GET\",\"is_requestbody\":false,\"params\":{\"systolic\":{\"is_mandatory\":true,\"type\":\"Query\",\"data_type\":\"Integer\"},\"diaslotic\":{\"is_mandatory\":true,\"type\":\"Query\",\"data_type\":\"Integer\"}}},\"vb19323d-3ff4-4fa3-8bea-e09edf644464\":{\"path\":\"\\/blood-pressuer-data\",\"method\":\"POST\",\"is_requestbody\":true,\"params\":{}}},\"data_security\":{\"intenal_encryption\":{\"is_required\":true,\"key_name\":\"xyz\",\"key_password\":\"abc\"},\"external_encryption\":{\"is_required\":false,\"key_name\":\"\",\"key_password\":\"\",\"keystore_location\":\"\",\"keystore_password\":\"\"}},\"data_structure\":\"\",\"log_level\":\"DEBUG\"},\"specific_config\":{\"connection_name\":\"C6\",\"connection_description\":\"C6 specific config for SOS\",\"is_loopback_mode\":false,\"base_url\":\"http:\\/\\/sos-qa.com\\/v1\",\"credential\":{\"api_key\":{\"input_type\":\"header\",\"auth_param\":{\"user-key\":\"adedgsdfalsweirsldmfwrepwpoip\"}}},\"data_map\":{\"BP\":\"Blood Pressure\"},\"data_transformation\":\"\"},\"jsonapi\":{\"version\":\"1.0\"}}";
		// Convert JSON to XML
		json = json.replace("-", "");
		String xml = convert(json, "root"); // This method converts json object to xml string
		System.out.println(xml);
	}

	public static String convert(String json, String root) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<" + root + ">" + XML.toString(jsonObject)
				+ "</" + root + ">";
		return xml;
	}
}
