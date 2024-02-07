package jsonDTOExcercise;
// part 4 - Create AccountDTO
public class AccountDTO {
    String fullName;
    String city;
    String zipCode;
    String isActive;

    public AccountDTO(String fullName, String city, String zipCode, String isActive) {
        this.fullName = fullName;
        this.city = city;
        this.zipCode = zipCode;
        this.isActive = isActive;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "AccountDTO{ \n" +
                "Full Name = " + fullName +
                "\nCity = " + city  +
                "\nZipCode = " + zipCode +
                "\nisActive = " + isActive +
                "\n}";
    }
}
