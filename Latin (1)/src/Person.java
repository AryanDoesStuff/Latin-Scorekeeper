import java.util.Objects;

public class Person {
    private int chipCount;
    private String name;
    public Person(int cc, String name) {
        chipCount = cc;
        this.name = name;
    }

    public int getChipCount() {
        return chipCount;
    }
    public void adjustChips(int adjusted) {
        chipCount += adjusted;
    }
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chipCount, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return chipCount == person.chipCount && Objects.equals(name, person.name);
    }

    @Override
    public String toString() {
        return name + " " + chipCount;
    }
}
