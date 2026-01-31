import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * INTERNSHIP TASK-2: REST API CLIENT
 * Student: RUTUJA KUTHE - ENTC Engineering
 * Company: CODTECH IT SOLUTIONS
 * Simple Java application that consumes a public REST API
 * and displays data in structured format
 */
public class Task2_RestApiClient {

    public static void main(String[] args) {
        System.out.println("=== INTERNSHIP TASK-2: REST API CLIENT ===");
        System.out.println("Fetching data from public API...\n");
        
        // Try real API first, fallback to demo data
        String apiUrl = "https://jsonplaceholder.typicode.com/users/1";
        
        try {
            String jsonResponse = makeHttpRequest(apiUrl);
            if (jsonResponse != null) {
                parseAndDisplayUserData(jsonResponse);
            } else {
                // Fallback to demo data
                System.out.println("Using demo data (API unavailable)...\n");
                String demoJson = getDemoJsonData();
                parseAndDisplayUserData(demoJson);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            // Use demo data as fallback
            System.out.println("Using demo data...\n");
            String demoJson = getDemoJsonData();
            parseAndDisplayUserData(demoJson);
        }
        
        System.out.println("\n=== TASK COMPLETED ===");
    }
    
    /**
     * Returns sample JSON data for demonstration
     */
    public static String getDemoJsonData() {
        return "{\n" +
               "  \"id\": 1,\n" +
               "  \"name\": \"Leanne Graham\",\n" +
               "  \"username\": \"Bret\",\n" +
               "  \"email\": \"Sincere@april.biz\",\n" +
               "  \"address\": {\n" +
               "    \"street\": \"Kulas Light\",\n" +
               "    \"suite\": \"Apt. 556\",\n" +
               "    \"city\": \"Gwenborough\",\n" +
               "    \"zipcode\": \"92998-3874\"\n" +
               "  },\n" +
               "  \"phone\": \"1-770-736-8031 x56442\",\n" +
               "  \"website\": \"hildegard.org\",\n" +
               "  \"company\": {\n" +
               "    \"name\": \"Romaguera-Crona\",\n" +
               "    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
               "    \"bs\": \"harness real-time e-markets\"\n" +
               "  }\n" +
               "}";
    }
    
    /**
     * Makes HTTP GET request to the specified URL
     * @param apiUrl The API endpoint URL
     * @return JSON response as string
     */
    public static String makeHttpRequest(String apiUrl) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, 
                HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("✓ API request successful (Status: " + response.statusCode() + ")");
                return response.body();
            } else {
                System.err.println("✗ HTTP Error: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("✗ Request failed: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Parses JSON response and displays user data in structured format
     * @param jsonResponse The JSON response from API
     */
    public static void parseAndDisplayUserData(String jsonResponse) {
        System.out.println("--- USER DATA (Structured Format) ---");
        
        try {
            // Extract user information
            String id = extractJsonValue(jsonResponse, "id");
            String name = extractJsonValue(jsonResponse, "name");
            String username = extractJsonValue(jsonResponse, "username");
            String email = extractJsonValue(jsonResponse, "email");
            String phone = extractJsonValue(jsonResponse, "phone");
            String website = extractJsonValue(jsonResponse, "website");
            
            // Display in structured format
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("Website: " + website);
            
            // Extract nested address data
            System.out.println("\n--- ADDRESS INFORMATION ---");
            String street = extractNestedValue(jsonResponse, "address", "street");
            String city = extractNestedValue(jsonResponse, "address", "city");
            String zipcode = extractNestedValue(jsonResponse, "address", "zipcode");
            
            System.out.println("Street: " + street);
            System.out.println("City: " + city);
            System.out.println("Zipcode: " + zipcode);
            
            // Extract company data
            System.out.println("\n--- COMPANY INFORMATION ---");
            String companyName = extractNestedValue(jsonResponse, "company", "name");
            String catchPhrase = extractNestedValue(jsonResponse, "company", "catchPhrase");
            
            System.out.println("Company: " + companyName);
            System.out.println("Catch Phrase: " + catchPhrase);
            
        } catch (Exception e) {
            System.err.println("JSON parsing error: " + e.getMessage());
            System.out.println("\nRaw JSON Response:");
            System.out.println(jsonResponse);
        }
    }
    
    /**
     * Extracts value for a given key from JSON string
     * @param json The JSON string
     * @param key The key to search for
     * @return The extracted value
     */
    public static String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return "N/A";

        startIndex += searchKey.length();
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }

        if (startIndex >= json.length()) return "N/A";

        int endIndex;
        if (json.charAt(startIndex) == '"') {
            startIndex++;
            endIndex = json.indexOf('"', startIndex);
        } else {
            endIndex = startIndex;
            while (endIndex < json.length() &&
                    json.charAt(endIndex) != ',' &&
                    json.charAt(endIndex) != '}' &&
                    json.charAt(endIndex) != ']') {
                endIndex++;
            }
        }

        if (endIndex == -1) endIndex = json.length();
        return json.substring(startIndex, endIndex).trim();
    }
    
    /**
     * Extracts nested JSON values
     * @param json The JSON string
     * @param parentKey The parent object key
     * @param childKey The child key within parent object
     * @return The extracted value
     */
    public static String extractNestedValue(String json, String parentKey, String childKey) {
        String parentSearch = "\"" + parentKey + "\":";
        int parentStart = json.indexOf(parentSearch);
        if (parentStart == -1) return "N/A";
        
        // Find the opening brace of the nested object
        int braceStart = json.indexOf("{", parentStart);
        if (braceStart == -1) return "N/A";
        
        // Find the matching closing brace
        int braceCount = 1;
        int braceEnd = braceStart + 1;
        while (braceEnd < json.length() && braceCount > 0) {
            if (json.charAt(braceEnd) == '{') braceCount++;
            else if (json.charAt(braceEnd) == '}') braceCount--;
            braceEnd++;
        }
        
        if (braceCount != 0) return "N/A";
        
        String parentObject = json.substring(braceStart, braceEnd);
        return extractJsonValue(parentObject, childKey);
    }
}