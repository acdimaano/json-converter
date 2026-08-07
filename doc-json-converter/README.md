# doc-json-converter

A minimal Spring Boot sample project that converts a simple plain-text
"document" into a JSON object, and converts a JSON object back into a
plain-text document.

## Document format

```
Title: My Sample Document
Author: John Doe
Date: 2026-08-08
Content:
This is the body of the document.
It can span multiple lines.
```

## Run it

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## Endpoints

### 1. Upload a document file -> JSON

```bash
curl -F "file=@sample-document.txt" http://localhost:8080/api/documents/to-json
```

Response:

```json
{
  "title": "My Sample Document",
  "author": "John Doe",
  "date": "2026-08-08",
  "content": "This is the body of the document.\nIt can span multiple lines and will be\npreserved as a single content field in the JSON output."
}
```

### 2. Send raw document text -> JSON

```bash
curl -X POST -H "Content-Type: text/plain" \
  --data-binary @sample-document.txt \
  http://localhost:8080/api/documents/to-json/text
```

### 3. Send JSON -> plain-text document

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"My Doc","author":"Jane","date":"2026-08-08","content":"Hello world."}' \
  http://localhost:8080/api/documents/to-document \
  -o document.txt
```

## Run the tests

```bash
mvn test
```

## Project structure

```
src/main/java/com/example/docconverter/
├── DocConverterApplication.java      # Spring Boot entry point
├── model/Document.java               # JSON-mapped document object
├── service/DocumentConversionService.java  # text <-> Document logic
└── controller/DocumentController.java      # REST endpoints
```

## Extending this

This sample uses a simple key-value + content text format so the whole
project stays dependency-light (just `spring-boot-starter-web`). If you want
to support real Word (`.docx`) or PDF documents instead of plain text, swap
out `DocumentConversionService` for a library like Apache POI (`.docx`) or
Apache PDFBox (`.pdf`), while keeping the same `Document` model and
controller endpoints.
