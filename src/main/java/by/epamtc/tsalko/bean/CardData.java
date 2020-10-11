package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CardData implements Serializable {

    private static final long serialVersionUID = -3945500796807866469L;

    private int userID;
    private long cardAccount;
    private Date cardValidTrue;
    private String cardUserFirstname;
    private String cardUserLastname;
    private int authorizationCode;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(long cardAccount) {
        this.cardAccount = cardAccount;
    }

    public Date getCardValidTrue() {
        return cardValidTrue;
    }

    public void setCardValidTrue(Date cardValidTrue) {
        this.cardValidTrue = cardValidTrue;
    }

    public String getCardUserFirstname() {
        return cardUserFirstname;
    }

    public void setCardUserFirstname(String cardUserFirstname) {
        this.cardUserFirstname = cardUserFirstname;
    }

    public String getCardUserLastname() {
        return cardUserLastname;
    }

    public void setCardUserLastname(String cardUserLastname) {
        this.cardUserLastname = cardUserLastname;
    }

    public int getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(int authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardData cardData = (CardData) o;
        return userID == cardData.userID &&
                cardAccount == cardData.cardAccount &&
                authorizationCode == cardData.authorizationCode &&
                Objects.equals(cardValidTrue, cardData.cardValidTrue) &&
                Objects.equals(cardUserFirstname, cardData.cardUserFirstname) &&
                Objects.equals(cardUserLastname, cardData.cardUserLastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, cardAccount, cardValidTrue, cardUserFirstname, cardUserLastname,
                authorizationCode);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", cardAccount=" + cardAccount +
                ", cardValidTrue=" + cardValidTrue +
                ", cardUserFirstname='" + cardUserFirstname + '\'' +
                ", cardUserLastname='" + cardUserLastname + '\'' +
                ", authorizationCode=" + authorizationCode +
                '}';
    }
}
