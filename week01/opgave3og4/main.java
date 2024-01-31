package opgave3og4;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class main {
    public static void main(String[] args){
        System.out.println("3.1");
        //// 1.
        ArrayList<Integer> aarray = new ArrayList();
        aarray.add(1);
        aarray.add(3);
        aarray.add(67);
        aarray.add(7);
        Predicate<Integer> divBySeven = (x) -> x%7 == 0;
        ArrayList divedBySeven = aarray.stream().map(x -> divBySeven.test(x)).collect(Collectors.toCollection(ArrayList::new));
        divedBySeven.stream().forEach(System.out::println);
        ////
        System.out.println("-------------- \n3.2");
        //// 2.
        Random randomNumberGenerator = new Random();
        List<String> listOfNames = Arrays.asList("John", "Jane", "Jack", "Joe", "Jill");

        LocalDate[] birthdates = {
                LocalDate.of(1950,4,15),
                LocalDate.of(1988,3,31),
                LocalDate.of(1975,6,12),
                LocalDate.of(1992,12,26),
                LocalDate.of(2020,9,18),
                LocalDate.of(1991,1,25),
                LocalDate.of(1995,8,22),
                LocalDate.of(1932,7,18),
                LocalDate.of(1922,6,14)
        };
        /*
        List<Employee> listOfEmployees = new ArrayList<>();

        for (String s:listOfNames) {
            int randomAge = randomNumberGenerator.nextInt(16,65); // added for part 5. of assignment.
            listOfEmployees.add(new Employee(s,randomAge));
        }
         */
        ArrayList<Employee> listOfEmployees = listOfNames.stream()
                .map(name -> new Employee(name,birthdates[randomNumberGenerator.nextInt(0, birthdates.length)]))
                .collect(Collectors.toCollection(ArrayList::new));

        Supplier<Employee> getEmployee = () -> {
            int randomNumber = randomNumberGenerator.nextInt(0,listOfEmployees.size());
            return listOfEmployees.get(randomNumber);
        };
        ArrayList<Employee> randomListOfEmployees = generateEmployeeList(10,getEmployee);
        randomListOfEmployees.stream()
                .map(x -> x.getName())
                .forEach(System.out::println);
        ////
        System.out.println("-------- \n3.3");
        //// 3.
        System.out.println("Printed with comsumer:");
        Consumer<ArrayList<Employee>> printEmployeeList = list -> list.stream()
                .map(x -> x.getName())
                .forEach(System.out::println);
        printEmployeeList.accept(randomListOfEmployees);
        ////
        System.out.println("-------- \n3.4");
        //// 4.
        System.out.println("Printing The List of names from generated from Function:");
        Function<ArrayList<Employee>, ArrayList<String>> getNamesOfEmployessAsList = list -> list.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        List<String> listOfNamesInRandomList = getNamesOfEmployessAsList.apply(randomListOfEmployees);
        listOfNamesInRandomList.forEach(System.out::println);
        ////
        System.out.println("--------- \n3.5");
        //// 5.
        Predicate<Employee> ageCheckEmployeeList = x -> x.getAge() > 18 ? true : false;
        StringBuilder newText = new StringBuilder();
        newText.append("Is Employee is above 18?");
        newText.append(ageCheckEmployeeList.test(randomListOfEmployees.get(0)) ? "\nYes" : newText.append("\nNo"));
        newText.append("\nThier age: "+ randomListOfEmployees.get(0).getAge());
        System.out.println(newText);
        ////
        //// Opgave 4
        System.out.println("------\n4.1");
        listOfEmployees.forEach(System.out::println);
        ////
        System.out.println("--------\n4.2");
        int totalAge = listOfEmployees.stream().map(x -> x.getAge()).reduce(Integer::sum).get();
        int averageAge = totalAge/listOfEmployees.size();
        System.out.println("Average age of all employees are: " + averageAge);
        ////
        System.out.println("-------\n4.3");
        Map<Month,List<Employee>> groupByYear = listOfEmployees.stream()
                .collect(Collectors.groupingBy(x -> x.getBirthday().getMonth()));
        groupByYear.forEach((month, employees) ->{
            System.out.println("Month: "+ month + "(amount:"+employees.size()+")");
            employees.forEach(System.out::println);
        });
        ////
        System.out.println("-------\n4.4");
        Map<Month,Long> countByMonth = listOfEmployees.stream()
                .collect(Collectors.groupingBy(x -> x.getBirthday().getMonth(),Collectors.counting()));
        countByMonth.forEach((month, Long) ->{
            System.out.println("Number of a Employees with birthday in " + month + " is: " + Long);
        });
        ////
        System.out.println("-------\n4.5");
        Predicate<Employee> isBirthdayMarch = (emp) -> emp.getBirthday().getMonth().getValue() == 3;
        ArrayList<Employee> employeesInMarch = listOfEmployees.stream().filter(isBirthdayMarch).collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Numbers of Employees with a birthday in March: " + employeesInMarch.size());
        employeesInMarch.forEach(System.out::println);
    }
    static ArrayList<Employee> generateEmployeeList(int amount,Supplier sup){
        ArrayList<Employee> tempEmpList = new ArrayList<>();
        for (int i = 1; i <= amount; i++){
            tempEmpList.add((Employee) sup.get());
        }
        return tempEmpList;
    }
}
