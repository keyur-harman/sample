# Common utility 

This utility aim to provide collection of common function in the Provenance micro services.

* Utility for Crnk client library functions to form and parse request and response object.



## Get the code

Git:

    git clone https://github.com/parallel-6/provenance-utils-java-library.git

Or simply [download a zip](https://github.com/parallel-6/provenance-utils-java-library.git/archive/development.zip) file.


## Use Maven to execut library locally

Open a command window and run:
               
    cd (directory location of the cloned project)
               
    mvn clean install
    
Above command will execute library locally and will add library in .m2 folder locally.
	
	
## Use library in other service 

Add dependency in pom.zml
               
   		<dependency>
			<groupId>com.prahs.clinical6</groupId>
			<artifactId>prahs-common-util</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>





