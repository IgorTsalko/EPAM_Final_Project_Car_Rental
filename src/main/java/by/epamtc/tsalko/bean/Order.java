package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = 1648826363500527628L;

    private int userID;
    private String userLogin;
    private int orderId;
    private LocalDateTime orderDate;
    private String orderStatus;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private int carID;
    private String carBrand;
    private String carModel;
    private double pricePerDay;
    private double billSum;
    private boolean paid;
    private double discount;
    private String comment;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        if (userID >= 0) {
            this.userID = userID;
        }
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        if (orderId >= 0) {
            this.orderId = orderId;
        }
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalDate getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(LocalDate dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        if (carID >= 0) {
            this.carID = carID;
        }
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        if (pricePerDay >= 0) {
            this.pricePerDay = pricePerDay;
        }
    }

    public double getBillSum() {
        return billSum;
    }

    public void setBillSum(double billSum) {
        if (billSum >= 0) {
            this.billSum = billSum;
        }
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        if (discount >= 0) {
            this.discount = discount;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return userID == order.userID &&
                orderId == order.orderId &&
                carID == order.carID &&
                Double.compare(order.billSum, billSum) == 0 &&
                paid == order.paid &&
                Double.compare(order.discount, discount) == 0 &&
                Objects.equals(userLogin, order.userLogin) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(orderStatus, order.orderStatus) &&
                Objects.equals(pickUpDate, order.pickUpDate) &&
                Objects.equals(dropOffDate, order.dropOffDate) &&
                Objects.equals(carBrand, order.carBrand) &&
                Objects.equals(carModel, order.carModel) &&
                Objects.equals(comment, order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, orderId, orderDate, orderStatus, pickUpDate,
                dropOffDate, carID, carBrand, carModel, billSum, paid, discount, comment);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", userLogin='" + userLogin + '\'' +
                ", orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", pickUpDate=" + pickUpDate +
                ", dropOffDate=" + dropOffDate +
                ", carID=" + carID +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", billSum='" + billSum + '\'' +
                ", paid=" + paid +
                ", discount=" + discount +
                ", comment='" + comment + '\'' +
                '}';
    }
}
