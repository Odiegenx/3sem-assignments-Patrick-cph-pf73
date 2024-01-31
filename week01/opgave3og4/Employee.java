package opgave3og4;

import java.time.LocalDate;
import java.time.Period;

public class Employee {
    private String name;
    LocalDate birthday;

    Employee(String name,LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName(){
        return this.name;
    }
    public LocalDate getBirthday(){
        return birthday;
    }
    //// 1.
    public int getAge() {
        LocalDate present = LocalDate.now();
        Period period = Period.between(birthday, present);
        return period.getYears();
    }
    ////

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age = " + this.getAge() +
                '}';
    }
}
