package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserDetails implements Serializable {

    private static final long serialVersionUID = 7729699712582084183L;

    private int userID;
    private String userLogin;
    private String userRole;
    private String userRating;
    private String userPhone;
    private String userEmail;
    private Date userRegistrationDate;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
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

    public Date getUserRegistrationDate() {
        return userRegistrationDate;
    }

    public void setUserRegistrationDate(Date userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return userID == that.userID &&
                Objects.equals(userLogin, that.userLogin) &&
                Objects.equals(userRole, that.userRole) &&
                Objects.equals(userRating, that.userRating) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userRegistrationDate, that.userRegistrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, userRole, userRating, userPhone, userEmail, userRegistrationDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", userLogin='" + userLogin + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userRating='" + userRating + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRegistrationDate=" + userRegistrationDate +
                '}';
    }
}
