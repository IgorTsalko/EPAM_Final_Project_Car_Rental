package by.epamtc.tsalko.bean.user;

import java.io.Serializable;
import java.util.Objects;

public class RegistrationData implements Serializable {

    private static final long serialVersionUID = -4346261359891988745L;

    private String email;
    private String phone;
    private String login;
    private String password;

    public RegistrationData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationData that = (RegistrationData) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone, login, password);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
