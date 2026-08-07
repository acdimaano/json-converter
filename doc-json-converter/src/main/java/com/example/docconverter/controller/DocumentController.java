package com.example.docconverter.controller;

import com.example.docconverter.model.Document;
import com.example.docconverter.service.DocumentConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentConversionService conversionService;

    public DocumentController(DocumentConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Upload a .txt document file and get back the parsed JSON object.
     * Example: curl -F "file=@sample-document.txt" http://localhost:8080/api/documents/to-json
     */
    @PostMapping(value = "/to-json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> convertFileToJson(@RequestParam("file") MultipartFile file) throws IOException {
        String rawText = new String(file.getBytes(), StandardCharsets.UTF_8);
        Document document = conversionService.parseDocument(rawText);
        return ResponseEntity.ok(document);
    }

    /**
     * Send raw document text in the request body and get back the parsed JSON object.
     * Example: curl -X POST -H "Content-Type: text/plain" --data-binary @sample-document.txt \
     *          http://localhost:8080/api/documents/to-json/text
     */
    @PostMapping(value = "/to-json/text", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Document> convertTextToJson(@RequestBody String rawText) {
        Document document = conversionService.parseDocument(rawText);
        return ResponseEntity.ok(document);
    }

    /**
     * Send a JSON document object and get back the rendered plain-text document
     * as a downloadable file.
     * Example: curl -X POST -H "Content-Type: application/json" -d '{...}' \
     *          http://localhost:8080/api/documents/to-document -o document.txt
     */
    @PostMapping(value = "/to-document", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> convertJsonToDocument(@RequestBody Document document) {
        String rendered = conversionService.renderDocument(document);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document.txt\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(rendered);
    }
}
