package com.harman.clinical6.poc.springsecurity.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * class implementing FilterInvocationSecurityMetadataSource
 * 
 * @author RChoudhury2
 *
 */
public class MyFilterSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public List<ConfigAttribute> getAttributes(Object object) {
		FilterInvocation fi = (FilterInvocation) object;
		String requestUrl = fi.getRequestUrl();
		System.out.println("Request URL: " + requestUrl);

		try {
			String[] values = getDatabaseValues(requestUrl);
			return createList(values);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param attributeNames
	 * @return
	 */
	public static List<ConfigAttribute> createList(String... attributeNames) {
		List<ConfigAttribute> attributes = new ArrayList(attributeNames.length);
		String[] var2 = attributeNames;
		int var3 = attributeNames.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String attribute = var2[var4];
			attributes.add(new SecurityConfig(attribute.trim()));
		}
		return attributes;
	}

	private String[] getDatabaseValues(String requestUrl) throws SQLException {
		String role = null;
		Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres", "postgres",
				"harman@123");
		PreparedStatement preparedStatement = connection
				.prepareStatement("select r.name from role r INNER JOIN( role_permission_map rp INNER JOIN permission p on p.id=rp.permission_id )ON r.id = rp.role_id where p.api_path='"
						+ requestUrl + "'");
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			role = resultSet.getString("name");
			System.out.println("role is" + role);
		}
		return new String[] { requestUrl, role };

	}
}
