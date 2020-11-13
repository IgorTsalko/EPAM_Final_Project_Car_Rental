package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ReturnAct implements Serializable {

    private static final long serialVersionUID = 4632786558936597165L;

    private int orderID;
    private int actID;
    private LocalDate returnDate;
    private int carOdometer;
    private String carDamage;
    private double fine;
    private String actComment;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getActID() {
        return actID;
    }

    public void setActID(int actID) {
        if (actID < 0) {
            actID = 0;
        }
        this.actID = actID;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getCarOdometer() {
        return carOdometer;
    }

    public void setCarOdometer(int carOdometer) {
        if (carOdometer < 0) {
            carOdometer = 0;
        }
        this.carOdometer = carOdometer;
    }

    public String getCarDamage() {
        return carDamage;
    }

    public void setCarDamage(String carDamage) {
        this.carDamage = carDamage;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        if (fine < 0) {
            fine = 0;
        }
        this.fine = fine;
    }

    public String getActComment() {
        return actComment;
    }

    public void setActComment(String actComment) {
        this.actComment = actComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnAct returnAct = (ReturnAct) o;
        return orderID == returnAct.orderID &&
                actID == returnAct.actID &&
                carOdometer == returnAct.carOdometer &&
                Objects.equals(returnDate, returnAct.returnDate) &&
                Objects.equals(carDamage, returnAct.carDamage) &&
                Objects.equals(fine, returnAct.fine) &&
                Objects.equals(actComment, returnAct.actComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, actID, returnDate, carOdometer, carDamage, fine, actComment);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "orderID=" + orderID +
                ", actID=" + actID +
                ", returnDate=" + returnDate +
                ", carOdometer=" + carOdometer +
                ", carDamage='" + carDamage + '\'' +
                ", fine='" + fine + '\'' +
                ", actComment='" + actComment + '\'' +
                '}';
    }
}
