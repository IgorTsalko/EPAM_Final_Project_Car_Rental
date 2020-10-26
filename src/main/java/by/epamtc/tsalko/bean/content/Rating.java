package by.epamtc.tsalko.bean.content;

import java.io.Serializable;
import java.util.Objects;

public class Rating implements Serializable {

    private static final long serialVersionUID = 1985927038673272218L;

    private int ratingID;
    private String ratingName;
    private int discount;

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        if (ratingID < 0) {
            ratingID = 0;
        }
        this.ratingID = ratingID;
    }

    public String getRatingName() {
        return ratingName;
    }

    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        if (discount < 0) {
            discount = 0;
        }
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return ratingID == rating1.ratingID &&
                discount == rating1.discount &&
                Objects.equals(ratingName, rating1.ratingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingID, ratingName, discount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "ratingID=" + ratingID +
                ", rating='" + ratingName + '\'' +
                ", discount=" + discount +
                '}';
    }
}
