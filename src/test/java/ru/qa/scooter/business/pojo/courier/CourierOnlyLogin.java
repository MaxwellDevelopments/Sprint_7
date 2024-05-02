package ru.qa.scooter.business.pojo.courier;

public class CourierOnlyLogin<T> extends AbstractCourier {
    private T login;

    public CourierOnlyLogin() {
    }

    public CourierOnlyLogin(T login) {
        this.login = login;
    }

    public T getLogin() {
        return login;
    }

    public void setLogin(T login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return String.format(
                "Courier: login - \"%s\"",
                login
        );
    }
}
