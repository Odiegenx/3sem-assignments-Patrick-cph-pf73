package threadExcercise.DTOs;

public class PatrickDTO implements DTOInterface{
    String name;
    int age;
    @Override
    public Object getResults() {
        return "Name "+ name+" Age: "+age;
    }

    @Override
    public String toString() {
        return "Name = " + name + " Age= " + age;
    }
}
