package ru.qa.scooter.business.pojo.courier;

public class CourierOnlyPassword<T> extends AbstractCourier {
    private T password;

    public CourierOnlyPassword() {
    }

    public CourierOnlyPassword(T password) {
        this.password = password;
    }

    public T getPassword() {
        return password;
    }

    public void setPassword(T password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Courier: password - \"%s\"",
                password
        );
    }
}
