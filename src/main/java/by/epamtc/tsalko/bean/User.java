package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 880041323907629570L;

    private final String id;
    private final String login;
    private final String role;
    private final String rating;

    public User(String id, String login, String role, String rating) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(role, user.role) &&
                Objects.equals(rating, user.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, role, rating);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
