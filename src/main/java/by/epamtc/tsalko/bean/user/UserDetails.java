package by.epamtc.tsalko.bean.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserDetails implements Serializable {

    private static final long serialVersionUID = 7729699712582084183L;

    private int userID;
    private String userLogin;
    private String userRoleName;
    private int userRoleID;
    private String userRatingName;
    private int userRatingID;
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

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public int getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getUserRatingName() {
        return userRatingName;
    }

    public void setUserRatingName(String userRatingName) {
        this.userRatingName = userRatingName;
    }

    public int getUserRatingID() {
        return userRatingID;
    }

    public void setUserRatingID(int userRatingID) {
        this.userRatingID = userRatingID;
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
                userRoleID == that.userRoleID &&
                userRatingID == that.userRatingID &&
                Objects.equals(userLogin, that.userLogin) &&
                Objects.equals(userRoleName, that.userRoleName) &&
                Objects.equals(userRatingName, that.userRatingName) &&
                Objects.equals(userPhone, that.userPhone) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userRegistrationDate, that.userRegistrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, userRoleName, userRoleID, userRatingName,
                userRatingID, userPhone, userEmail, userRegistrationDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", userLogin='" + userLogin + '\'' +
                ", userRoleName='" + userRoleName + '\'' +
                ", userRoleID=" + userRoleID +
                ", userRatingName='" + userRatingName + '\'' +
                ", userRatingID=" + userRatingID +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRegistrationDate=" + userRegistrationDate +
                '}';
    }
}
