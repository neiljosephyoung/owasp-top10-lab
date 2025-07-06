package com.example.injection.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@RestController
@RequestMapping("/xxe")
public class XxeController {

    // Vulnerable endpoint — unsafe XML parsing
    @PostMapping("/vulnerable")
    public ResponseEntity<String> vulnerable(@RequestBody String xml) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Enable external entity processing (vulnerable!)
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", true);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true);
            dbf.setXIncludeAware(true);
            dbf.setExpandEntityReferences(true);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml)));

            String rootElement = doc.getDocumentElement().getNodeName();
            String content = doc.getDocumentElement().getTextContent();

            return ResponseEntity.ok("Parsed XML Root Element: " + rootElement + "\nContent: " + content);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to parse XML: " + e.getMessage());
        }
    }


    // Secure endpoint — disables external entities to prevent XXE
    @PostMapping("/secure")
    public ResponseEntity<String> secure(@RequestBody String xml) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Prevent XXE by disabling external entities
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml)));

            String rootElement = doc.getDocumentElement().getNodeName();
            return ResponseEntity.ok("Parsed XML Root Element (secure): " + rootElement);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to parse XML securely: " + e.getMessage());
        }
    }
}