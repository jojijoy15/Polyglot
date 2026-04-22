package annotations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonFieldProcessorTest {

    @Test
    void toJson_serializesAnnotatedFieldsOnly() {
        Person person = new Person("John", "Doe", 30, "password123");

        String json = JsonFieldProcessor.toJson(person);

        assertThat(json).isEqualTo("{\"first_name\": \"John\", \"last_name\": \"Doe\", \"age\": 30}");
        assertThat(json).doesNotContain("secret");
        assertThat(json).doesNotContain("password123");
    }

    @Test
    void toJson_returnsEmptyJsonForNull() {
        String json = JsonFieldProcessor.toJson(null);

        assertThat(json).isEqualTo("{}");
    }

    @Test
    void toJson_returnsEmptyJsonForObjectWithNoAnnotatedFields() {
        Object obj = new Object() {
            private String unannotated = "value";
        };

        String json = JsonFieldProcessor.toJson(obj);

        assertThat(json).isEqualTo("{}");
    }
}

