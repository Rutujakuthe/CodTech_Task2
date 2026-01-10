import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApiClient {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=19.0760&longitude=72.8777&current_weather=true&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m";
            
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String jsonData = response.toString();
            parseAndDisplayWeatherData(jsonData);

            conn.disconnect();
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void parseAndDisplayWeatherData(String json) {
        System.out.println("\n=== WEATHER DATA FOR MUMBAI, INDIA ===");
        
        String temp = extractValue(json, "\"temperature\":");
        String windSpeed = extractValue(json, "\"windspeed\":");
        String windDirection = extractValue(json, "\"winddirection\":");
        String weatherCode = extractValue(json, "\"weathercode\":");
        String time = extractValue(json, "\"time\":", true);
        
        System.out.println("📅 Date & Time: " + time);
        System.out.println("🌡️  Temperature: " + temp + "°C");
        System.out.println("💨 Wind Speed: " + windSpeed + " km/h");
        System.out.println("🧭 Wind Direction: " + windDirection + "°");
        System.out.println("☁️  Weather Code: " + weatherCode);
        System.out.println("🌍 Location: Mumbai (19.0760°N, 72.8777°E)");
        
        String condition = getWeatherCondition(weatherCode);
        System.out.println("🌤️  Condition: " + condition);
    }

    public static String extractValue(String json, String key) {
        return extractValue(json, key, false);
    }

    public static String extractValue(String json, String key, boolean isString) {
        int startIndex = json.indexOf(key);
        if (startIndex == -1) return "N/A";
        
        startIndex += key.length();
        while (startIndex < json.length() && (json.charAt(startIndex) == ' ' || json.charAt(startIndex) == '\t')) {
            startIndex++;
        }
        
        int endIndex;
        if (isString && json.charAt(startIndex) == '"') {
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
        
        return json.substring(startIndex, endIndex).trim();
    }

    public static String getWeatherCondition(String code) {
        if (code.equals("N/A")) return "Unknown";
        
        int weatherCode = Integer.parseInt(code);
        switch (weatherCode) {
            case 0: return "Clear sky";
            case 1: case 2: case 3: return "Partly cloudy";
            case 45: case 48: return "Fog";
            case 51: case 53: case 55: return "Drizzle";
            case 61: case 63: case 65: return "Rain";
            case 71: case 73: case 75: return "Snow";
            case 80: case 81: case 82: return "Rain showers";
            case 95: return "Thunderstorm";
            default: return "Unknown condition";
        }
    }
}