package com.java.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Hello world!
 */
public class App {
  public static void main(String[] args)  {
    System.out.println("Hello World!");
  }
  
  public static Document transformAsDocument(String inputXml, String xslt) {
      String outputString = transformAsString(inputXml, xslt);
      return stringToDocument(outputString);
  }
  
  public static String transformAsString(String inputXml, String xslt) {
      String outputString = null;
      try {
          // Create the stream sources for the XML and XSLT
          StreamSource xmlSource = new StreamSource(new StringReader(inputXml));
          StreamSource xsltSource = new StreamSource(new StringReader(xslt));

          // Create a new transformer using the XSLT
          TransformerFactory factory = TransformerFactory.newInstance();
          Transformer transformer = factory.newTransformer(xsltSource);

          // Prepare the output
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          StreamResult result = new StreamResult(output);

          // Apply transform
          transformer.transform(xmlSource, result);
          outputString = new String(output.toByteArray());

      } catch (TransformerException te) {
//          log.error("Error transforming XML with XSLT.");
          te.printStackTrace();
      }

      return outputString;
  }

  
  public static Document stringToDocument(String xmlString) {
      Document document = null;
      // Build an XML document using the transformed string
      try {

          DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();

          DocumentBuilder factory = builder.newDocumentBuilder();

//          factory.setErrorHandler(new SimpleErrorHandler());

          StringReader reader = new StringReader(xmlString);
          InputSource source = new InputSource(reader);
          document = factory.parse(source);
      } catch (ParserConfigurationException pce) {
//          log.error("Error creating DocumentBuilder.");
          pce.printStackTrace();
      } catch (IOException | SAXException e) {
//          log.error("Error parsing transform result into Document.");
          e.printStackTrace();
      }

      return document;
  }
}
