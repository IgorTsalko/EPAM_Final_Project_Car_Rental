package by.epamtc.tsalko.bean.user;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserDetails implements Serializable {

    private static final long serialVersionUID = 7729699712582084183L;

    private int userID;
    private String userLogin;
    private String userPhone;
    private String userEmail;
    private LocalDateTime userRegistrationDate;

    private Role userRole;
    private Rating userRating;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        if (userID < 0) {
            userID = 0;
        }
        this.userID = userID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getUserRegistrationDate() {
        return userRegistrationDate;
    }

    public void setUserRegistrationDate(LocalDateTime userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating userRating) {
        this.userRating = userRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return userID == that.userID &&
                Objects.equals(userLogin, that.userLogin) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userRegistrationDate, that.userRegistrationDate) &&
                Objects.equals(userRole, that.userRole) &&
                Objects.equals(userRating, that.userRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, userPhone, userEmail, userRegistrationDate,
                userRole, userRating);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", userLogin='" + userLogin + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRegistrationDate=" + userRegistrationDate +
                ", userRole=" + userRole +
                ", userRating=" + userRating +
                '}';
    }
}
