package by.epamtc.tsalko.bean.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Passport implements Serializable {

    private static final long serialVersionUID = 7782641646015319564L;

    private int userID;
    private int passportID;
    private String passportSeries;
    private String passportNumber;
    private Date passportDateOfIssue;
    private String passportIssuedBy;
    private String passportUserAddress;
    private String passportUserSurname;
    private String passportUserName;
    private String passportUserThirdName;
    private Date passportUserDateOfBirth;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        if (userID < 0) {
            userID = 0;
        }
        this.userID = userID;
    }

    public int getPassportID() {
        return passportID;
    }

    public void setPassportID(int passportID) {
        if (passportID < 0) {
            passportID = 0;
        }
        this.passportID = passportID;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getPassportDateOfIssue() {
        return passportDateOfIssue;
    }

    public void setPassportDateOfIssue(Date passportDateOfIssue) {
        this.passportDateOfIssue = passportDateOfIssue;
    }

    public String getPassportIssuedBy() {
        return passportIssuedBy;
    }

    public void setPassportIssuedBy(String passportIssuedBy) {
        this.passportIssuedBy = passportIssuedBy;
    }

    public String getPassportUserAddress() {
        return passportUserAddress;
    }

    public void setPassportUserAddress(String passportUserAddress) {
        this.passportUserAddress = passportUserAddress;
    }

    public String getPassportUserSurname() {
        return passportUserSurname;
    }

    public void setPassportUserSurname(String passportUserSurname) {
        this.passportUserSurname = passportUserSurname;
    }

    public String getPassportUserName() {
        return passportUserName;
    }

    public void setPassportUserName(String passportUserName) {
        this.passportUserName = passportUserName;
    }

    public String getPassportUserThirdName() {
        return passportUserThirdName;
    }

    public void setPassportUserThirdName(String passportUserThirdName) {
        this.passportUserThirdName = passportUserThirdName;
    }

    public Date getPassportUserDateOfBirth() {
        return passportUserDateOfBirth;
    }

    public void setPassportUserDateOfBirth(Date passportUserDateOfBirth) {
        this.passportUserDateOfBirth = passportUserDateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return userID == passport.userID &&
                passportID == passport.passportID &&
                Objects.equals(passportSeries, passport.passportSeries) &&
                Objects.equals(passportNumber, passport.passportNumber) &&
                Objects.equals(passportDateOfIssue, passport.passportDateOfIssue) &&
                Objects.equals(passportIssuedBy, passport.passportIssuedBy) &&
                Objects.equals(passportUserAddress, passport.passportUserAddress) &&
                Objects.equals(passportUserSurname, passport.passportUserSurname) &&
                Objects.equals(passportUserName, passport.passportUserName) &&
                Objects.equals(passportUserThirdName, passport.passportUserThirdName) &&
                Objects.equals(passportUserDateOfBirth, passport.passportUserDateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, passportID, passportSeries, passportNumber, passportDateOfIssue,
                passportIssuedBy, passportUserAddress, passportUserSurname, passportUserName,
                passportUserThirdName, passportUserDateOfBirth);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", passportID=" + passportID +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", passportDateOfIssue=" + passportDateOfIssue +
                ", passportIssuedBy='" + passportIssuedBy + '\'' +
                ", userPassportAddress='" + passportUserAddress + '\'' +
                ", userPassportSurname='" + passportUserSurname + '\'' +
                ", userPassportName='" + passportUserName + '\'' +
                ", userPassportThirdName='" + passportUserThirdName + '\'' +
                ", userDateOfBirth=" + passportUserDateOfBirth +
                '}';
    }
}
