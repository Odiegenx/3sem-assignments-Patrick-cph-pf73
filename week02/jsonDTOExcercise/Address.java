package jsonDTOExcercise;

public class Address {
    String street;
    String city;
    int zipCode;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "Address{\n" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
