package auth.domain.model;

public class Address {
    private final String road;
    private final String number;
    private final String city;
    private final String state;
    private final String zipCode;

    private Address(String road, String number, String city, String state, String zipCode) {
        this.road = road;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public static Address create(String street, String number, String city, String state, String zipCode) {
        validate(street, number, city, state, zipCode);
        return new Address(street, number, city, state, zipCode);
    }

    private static void validate(String street, String number, String city, String state, String zipCode) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Number cannot be null or empty");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("Zip code cannot be null or empty");
        }
    }

    public String street() {
        return road;
    }

    public String number() {
        return number;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }

    public String zipCode() {
        return zipCode;
    }
}
