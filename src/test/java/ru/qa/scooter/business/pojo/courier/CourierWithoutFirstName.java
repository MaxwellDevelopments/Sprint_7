package ru.qa.scooter.business.pojo.courier;

public class CourierWithoutFirstName<T, K> extends AbstractCourier {
    private T login;
    private K password;

    public CourierWithoutFirstName() {
    }

    public CourierWithoutFirstName(T login, K password) {
        this.login = login;
        this.password = password;
    }

    public T getLogin() {
        return login;
    }

    public void setLogin(T login) {
        this.login = login;
    }

    public K getPassword() {
        return password;
    }

    public void setPassword(K password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Courier: login - \"%s\", password - \"%s\"",
                login,
                password
        );
    }
}