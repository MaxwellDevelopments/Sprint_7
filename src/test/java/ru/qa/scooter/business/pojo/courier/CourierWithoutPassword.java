package ru.qa.scooter.business.pojo.courier;

public class CourierWithoutPassword<T, K> extends AbstractCourier {
    private T login;
    private K firstName;

    public CourierWithoutPassword() {
    }

    public CourierWithoutPassword(T login, K firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    public T getLogin() {
        return login;
    }

    public void setLogin(T login) {
        this.login = login;
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
                "Courier: login - \"%s\", firstName - \"%s\"",
                login,
                firstName
        );
    }
}
