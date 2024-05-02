package ru.qa.scooter.business.pojo.courier;

public class CourierId {
    private int id;

    public CourierId() {
    }

    public CourierId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "Courier: id - \"%s\"",
                id
        );
    }
}
