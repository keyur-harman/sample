Example from https://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-1.html

Highlight

- in pom.xml packaging is bundle instead of jar
- used Felix maven plugin to build this bundle and it will update manifest file for required parameters for osgi bundle

- this bundle is tested on apache karaf 
- Download karaf, https://karaf.apache.org/download
- Default URL for karaf web console after installation - http://localhost:8181/system/console/bundles

- In this example it just start and stop the bundle

