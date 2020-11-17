package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Bankcard implements Serializable {

    private static final long serialVersionUID = -3945500796807866469L;

    private int userID;
    private int bankcardID;
    private long bankcardNumber;
    private LocalDate bankcardValidTrue;
    private String bankcardUserFirstname;
    private String bankcardUserLastname;
    private String bankcardCVV;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        if (userID < 0) {
            userID = 0;
        }
        this.userID = userID;
    }

    public int getBankcardID() {
        return bankcardID;
    }

    public void setBankcardID(int bankcardID) {
        if (bankcardID < 0) {
            bankcardID = 0;
        }
        this.bankcardID = bankcardID;
    }

    public long getBankcardNumber() {
        return bankcardNumber;
    }

    public void setBankcardNumber(long bankcardNumber) {
        if (bankcardNumber > 0) {
            this.bankcardNumber = bankcardNumber;
        }
    }

    public LocalDate getBankcardValidTrue() {
        return bankcardValidTrue;
    }

    public void setBankcardValidTrue(LocalDate bankcardValidTrue) {
        this.bankcardValidTrue = bankcardValidTrue;
    }

    public String getBankcardUserFirstname() {
        return bankcardUserFirstname;
    }

    public void setBankcardUserFirstname(String bankcardUserFirstname) {
        this.bankcardUserFirstname = bankcardUserFirstname;
    }

    public String getBankcardUserLastname() {
        return bankcardUserLastname;
    }

    public void setBankcardUserLastname(String bankcardUserLastname) {
        this.bankcardUserLastname = bankcardUserLastname;
    }

    public String getBankcardCVV() {
        return bankcardCVV;
    }

    public void setBankcardCVV(String bankcardCVV) {
        this.bankcardCVV = bankcardCVV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bankcard bankcard = (Bankcard) o;
        return userID == bankcard.userID &&
                bankcardID == bankcard.bankcardID &&
                bankcardNumber == bankcard.bankcardNumber &&
                Objects.equals(bankcardValidTrue, bankcard.bankcardValidTrue) &&
                Objects.equals(bankcardUserFirstname, bankcard.bankcardUserFirstname) &&
                Objects.equals(bankcardUserLastname, bankcard.bankcardUserLastname) &&
                Objects.equals(bankcardCVV, bankcard.bankcardCVV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, bankcardID, bankcardNumber, bankcardValidTrue,
                bankcardUserFirstname, bankcardUserLastname, bankcardCVV);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", bankcardID=" + bankcardID +
                ", bankcardNumber=" + bankcardNumber +
                ", bankcardValidTrue=" + bankcardValidTrue +
                ", bankcardUserFirstname='" + bankcardUserFirstname + '\'' +
                ", bankcardUserLastname='" + bankcardUserLastname + '\'' +
                ", bankcardCVV='" + bankcardCVV + '\'' +
                '}';
    }
}
