package swingsample;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeJsonToFile(String fileName, Map<String, Object> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static<T> T readJsonFromFile(String fileName, Class<T> clazz) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
