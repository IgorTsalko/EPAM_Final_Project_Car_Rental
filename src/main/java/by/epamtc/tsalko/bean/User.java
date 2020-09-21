package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 880041323907629570L;

    private int id;
    private String email;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                email.equals(user.email) &&
                Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone);
    }

    @Override
    public String toString() {
        return  User.class.getSimpleName() + "{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phone + '\'' +
                '}';
    }
}
