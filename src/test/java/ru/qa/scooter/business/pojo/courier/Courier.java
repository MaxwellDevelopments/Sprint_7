package ru.qa.scooter.business.pojo.courier;

public class Courier <T, V, K> extends AbstractCourier {
    private T login;
    private V password;
    private K firstName;

    public Courier(T login, V password, K firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public T getLogin() {
        return login;
    }

    public void setLogin(T login) {
        this.login = login;
    }

    public V getPassword() {
        return password;
    }

    public void setPassword(V password) {
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
                    "Courier: login - \"%s\", password - \"%s\", firstName - \"%s\"",
                    login,
                    password,
                    firstName
                );
    }
}
