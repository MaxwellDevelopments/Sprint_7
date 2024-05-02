package ru.qa.scooter.business.pojo.courier;

public class CourierWithoutLogin<T, K> extends AbstractCourier {
    private T password;
    private K firstName;

    public CourierWithoutLogin() {
    }

    public CourierWithoutLogin(T password, K firstName) {
        this.password = password;
        this.firstName = firstName;
    }


    public T getPassword() {
        return password;
    }

    public void setPassword(T password) {
        this.password = password;
    }


    public K getFirstName() {
        return firstName;
    }

    public void setFirstName(K firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return String.format(
                "Courier: password - \"%s\", firstName - \"%s\"",
                password,
                firstName
        );
    }
}
