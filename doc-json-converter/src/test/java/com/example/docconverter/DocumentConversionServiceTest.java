package com.example.docconverter;

import com.example.docconverter.model.Document;
import com.example.docconverter.service.DocumentConversionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentConversionServiceTest {

    private final DocumentConversionService service = new DocumentConversionService();

    @Test
    void parseDocument_extractsFieldsCorrectly() {
        String raw = """
                Title: Test Doc
                Author: Jane
                Date: 2026-01-01
                Content:
                Line one.
                Line two.
                """;

        Document doc = service.parseDocument(raw);

        assertEquals("Test Doc", doc.getTitle());
        assertEquals("Jane", doc.getAuthor());
        assertEquals("2026-01-01", doc.getDate());
        assertEquals("Line one.\nLine two.", doc.getContent());
    }

    @Test
    void renderDocument_thenParseAgain_roundTrips() {
        Document original = new Document("Round Trip", "Author X", "2026-08-08", "Some content here.");

        String rendered = service.renderDocument(original);
        Document reparsed = service.parseDocument(rendered);

        assertEquals(original.getTitle(), reparsed.getTitle());
        assertEquals(original.getAuthor(), reparsed.getAuthor());
        assertEquals(original.getDate(), reparsed.getDate());
        assertEquals(original.getContent(), reparsed.getContent());
    }
}
