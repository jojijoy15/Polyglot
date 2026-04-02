package annotations;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class JsonFieldProcessor {

    public static String toJson(Object obj) {
        if (obj == null) {
            return "{}";
        }

        Map<String, String> jsonEntries = new LinkedHashMap<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonField.class)) {
                field.setAccessible(true);

                JsonField annotation = field.getAnnotation(JsonField.class);
                String key = annotation.name().isEmpty() ? field.getName() : annotation.name();

                try {
                    Object value = field.get(obj);
                    String jsonValue = (value instanceof String)
                            ? "\"" + value + "\""
                            : String.valueOf(value);
                    jsonEntries.put(key, jsonValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field: " + field.getName(), e);
                }
            }
        }

        return jsonEntries.entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\": " + e.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }
}

