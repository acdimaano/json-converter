package com.example.docconverter.service;

import com.example.docconverter.model.Document;
import org.springframework.stereotype.Service;

/**
 * Handles conversion between a simple plain-text "document" format and the
 * Document object (which the controller then serializes to/from JSON).
 *
 * Expected text document format:
 *
 *   Title: My Document
 *   Author: John Doe
 *   Date: 2024-01-01
 *   Content:
 *   This is the body of the document.
 *   It can span multiple lines.
 */
@Service
public class DocumentConversionService {

    private static final String TITLE_PREFIX = "Title:";
    private static final String AUTHOR_PREFIX = "Author:";
    private static final String DATE_PREFIX = "Date:";
    private static final String CONTENT_PREFIX = "Content:";

    /**
     * Parses raw document text into a Document object.
     */
    public Document parseDocument(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            throw new IllegalArgumentException("Document text must not be empty");
        }

        String title = "";
        String author = "";
        String date = "";
        StringBuilder content = new StringBuilder();

        String[] lines = rawText.split("\\R");
        boolean inContent = false;

        for (String line : lines) {
            if (inContent) {
                content.append(line).append("\n");
                continue;
            }

            if (line.startsWith(TITLE_PREFIX)) {
                title = line.substring(TITLE_PREFIX.length()).trim();
            } else if (line.startsWith(AUTHOR_PREFIX)) {
                author = line.substring(AUTHOR_PREFIX.length()).trim();
            } else if (line.startsWith(DATE_PREFIX)) {
                date = line.substring(DATE_PREFIX.length()).trim();
            } else if (line.startsWith(CONTENT_PREFIX)) {
                inContent = true;
                String remainder = line.substring(CONTENT_PREFIX.length()).trim();
                if (!remainder.isEmpty()) {
                    content.append(remainder).append("\n");
                }
            }
            // any other lines before "Content:" are ignored
        }

        return new Document(title, author, date, content.toString().trim());
    }

    /**
     * Renders a Document object back into the plain-text document format.
     */
    public String renderDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document must not be null");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(TITLE_PREFIX).append(" ").append(nullToEmpty(document.getTitle())).append("\n");
        sb.append(AUTHOR_PREFIX).append(" ").append(nullToEmpty(document.getAuthor())).append("\n");
        sb.append(DATE_PREFIX).append(" ").append(nullToEmpty(document.getDate())).append("\n");
        sb.append(CONTENT_PREFIX).append("\n");
        sb.append(nullToEmpty(document.getContent()));

        return sb.toString();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
