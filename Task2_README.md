# INTERNSHIP TASK-2: REST API CLIENT

**Student:** RUTUJA KUTHE - ENTC Engineering  
**Company:** CODTECH IT SOLUTIONS  
**Task:** Java Application that consumes a public REST API

## Project Overview
A simple Java application that makes HTTP requests to a public REST API, fetches JSON data, and displays it in a structured format.

## Features
- ✅ HTTP GET requests using Java 11+ HttpClient
- ✅ JSON response parsing (without external libraries)
- ✅ Structured data display
- ✅ Error handling for network issues
- ✅ Uses free public API (no API key required)

## Source Code

### Task2_RestApiClient.java
```java
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Task2_RestApiClient {
    public static void main(String[] args) {
        System.out.println("=== INTERNSHIP TASK-2: REST API CLIENT ===");
        System.out.println("Fetching data from public API...\n");
        
        String apiUrl = "https://jsonplaceholder.typicode.com/users/1";
        
        try {
            String jsonResponse = makeHttpRequest(apiUrl);
            if (jsonResponse != null) {
                parseAndDisplayUserData(jsonResponse);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== TASK COMPLETED ===");
    }
    
    // HTTP request method
    public static String makeHttpRequest(String apiUrl) {
        // Implementation here...
    }
    
    // JSON parsing method
    public static void parseAndDisplayUserData(String jsonResponse) {
        // Implementation here...
    }
}
```

## How to Run

### Step 1: Compile
```bash
javac Task2_RestApiClient.java
```

### Step 2: Run
```bash
java Task2_RestApiClient
```

## Sample Output
```
=== INTERNSHIP TASK-2: REST API CLIENT ===
Fetching data from public API...

✓ API request successful (Status: 200)

--- USER DATA (Structured Format) ---
ID: 1
Name: Leanne Graham
Username: Bret
Email: Sincere@april.biz
Phone: 1-770-736-8031 x56442
Website: hildegard.org

--- ADDRESS INFORMATION ---
Street: Kulas Light
City: Gwenborough
Zipcode: 92998-3874

--- COMPANY INFORMATION ---
Company: Romaguera-Crona
Catch Phrase: Multi-layered client-server neural-net

=== TASK COMPLETED ===
```

## Technical Details
- **API Used:** JSONPlaceholder (https://jsonplaceholder.typicode.com/)
- **HTTP Method:** GET
- **Response Format:** JSON
- **Java Version:** 11+ (uses HttpClient)
- **No External Dependencies:** Pure Java implementation

## Key Components
1. **HTTP Request Handling** - Makes GET requests to REST API
2. **JSON Parsing** - Extracts data from JSON responses
3. **Structured Display** - Formats output in readable format
4. **Error Handling** - Manages network and parsing errors

---
**Author:** RUTUJA KUTHE - ENTC Engineering