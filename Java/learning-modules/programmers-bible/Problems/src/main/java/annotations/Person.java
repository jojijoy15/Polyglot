package annotations;


public class Person {

    @JsonField(name = "first_name")
    private String firstName;

    @JsonField(name = "last_name")
    private String lastName;

    @JsonField
    private int age;

    private String secret;

    public Person(String firstName, String lastName, int age, String secret) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.secret = secret;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getSecret() {
        return secret;
    }
}

