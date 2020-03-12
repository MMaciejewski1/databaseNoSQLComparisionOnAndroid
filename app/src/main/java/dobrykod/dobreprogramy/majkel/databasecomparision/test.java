package dobrykod.dobreprogramy.majkel.databasecomparision;

public class test {

    public test(String name, double value) {
        this.name = name;
        this.value = value;
    }

    String name;
    double value;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
