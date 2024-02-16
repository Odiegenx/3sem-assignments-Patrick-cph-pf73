package Lombok;

public class main {
    public static void main(String[] args) {
        Person person = new Person("Patrick","Hansen",95);
        System.out.println(person);
        person.setAge(35);
        System.out.println(person.getAge());
        Person person1 = Person.builder()
                .firstName("Hans")
                .lastName("Hansen")
                .age(45)
                .build();
        System.out.println(person1);
        if(person.canEqual(person1)){
            System.out.println("its the same person!");
        }else{
            System.out.println("Not the same person!");
        }
    }
}
