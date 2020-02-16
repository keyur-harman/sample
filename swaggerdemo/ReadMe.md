# Introduction
Spring Boot makes developing RESTful services ridiculously easy. However, with RESTFul web services, your API documentation becomes more critical. API documentation should be structured so that it’s informative and easy to read. Swagger makes documenting your RESTful services easy. In this demo application, I’ve covered how to use Swagger2 to generate REST API documentation for a Spring Boot application.

# Swagger2 in Spring Boot
Swagger2 defines a set HTML, JavaScript, and CSS assets to dynamically generate documentation from a Swagger-compliant API. These files are bundled by the Swagger UI project to display the API on the browser. Besides rendering documentation, Swagger UI allows other API developers or consumers to interact with the API’s resources without having any of the implementation logic in place.
The Swagger2 specification has several implementations. Springfox is one of them and I have used the same in this demo application.

# Prerequisites
Simple Spring Boot RESTful Application
	
# Step 1: Adding dependency in pom.xml
Refer pom.xml file for the same
	
	<dependency>
		<groupId>io.springfox</groupId>
		<artifactId>springfox-swagger2</artifactId>
		<version>2.6.1</version>
		<scope>compile</scope>
	</dependency>

	<dependency>
		<groupId>io.springfox</groupId>
		<artifactId>springfox-swagger-ui</artifactId>
		<version>2.6.1</version>
		<scope>compile</scope>
	</dependency>

# Step 2: Controller of the application, ProductController
Make Sure Your RestController have following annotations
	
	@RestController: marks ProductController as a REST API controller.
	@RequestMapping: class-level annotation maps requests to /product onto the ProductController class. 
	@RequestMapping: method-level annotations map web requests to the handler methods of the controller.

# Step 3: Configuring Swagger2 in the Application
we need to create a Docket bean in a Spring Boot configuration to configure Swagger2 for the application. A Springfox Docket instance provides the primary API configuration with sensible defaults and convenience methods for configuration. Our Spring Boot configuration class, SwaggerConfig is this.

Refer SwaggerConfig.java for this.

In this configuration class, the **@EnableSwagger2** annotation enables Swagger support in the class. The **select()** method called on the Docket bean instance returns an ApiSelectorBuilder, which provides the **apis()** and **paths()** methods that are used to filter the controllers and methods that are being documented using String predicates.

In the code, the RequestHandlerSelectors.basePackage predicate matches the **com.harman.controller** base package to filter the API. The regex parameter passed to **paths()** acts as an additional filter to generate documentation only for the path starting with __/product__.
	In the SwaggerConfig class, we have added a **metaData()** method that returns and ApiInfo object initialized with information about our API.

At this point, you should be able to test the configuration by starting the app and pointing your browser to [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs).

This will give Swagger JSON OutputObviously, the above JSON dump that Swagger2 generates for our endpoints is not something we want.

What we want is some nice human readable structured documentation, and this is where Swagger UI takes over.
On pointing your browser to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html), you will see the generated documentation rendered by Swagger UI.

# Step 4: Swagger2 Annotations for REST Endpoints
	@Api- annotation on our ProductController class to describe our API.
	@ApiOperation- Customized Swagger Generated API Description For each of our operation endpoints, use this annotation to describe the endpoint and its response type
	@ApiResponses- Swagger2 also allows overriding the default response messages of HTTP methods. Use this annotation to document other responses, in addition to the regular HTTP 200 OK.

# Step 5: Swagger2 Annotations for Model
	@ApiModelProperty- annotation to describe the properties of the Product model. This is also used to document a property as required.
