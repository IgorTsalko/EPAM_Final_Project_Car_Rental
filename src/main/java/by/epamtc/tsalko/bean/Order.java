package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = 1648826363500527628L;

    private int userID;
    private int orderId;
    private Date orderDate;
    private String orderStatus;
    private Date rentalStart;
    private Date rentalEnd;
    private int carID;
    private String carBrand;
    private String carModel;
    private String carPricePerDay;
    private String orderPrice;
    private String comment;
    private int managerID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
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

    public String getCarPricePerDay() {
        return carPricePerDay;
    }

    public void setCarPricePerDay(String carPricePerDay) {
        this.carPricePerDay = carPricePerDay;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return userID == order.userID &&
                orderId == order.orderId &&
                carID == order.carID &&
                managerID == order.managerID &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(orderStatus, order.orderStatus) &&
                Objects.equals(rentalStart, order.rentalStart) &&
                Objects.equals(rentalEnd, order.rentalEnd) &&
                Objects.equals(carBrand, order.carBrand) &&
                Objects.equals(carModel, order.carModel) &&
                Objects.equals(carPricePerDay, order.carPricePerDay) &&
                Objects.equals(orderPrice, order.orderPrice) &&
                Objects.equals(comment, order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, orderId, orderDate, orderStatus, rentalStart, rentalEnd, carID,
                carBrand, carModel, carPricePerDay, orderPrice, comment, managerID);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", rentalStart=" + rentalStart +
                ", rentalEnd=" + rentalEnd +
                ", carID=" + carID +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carPricePerDay='" + carPricePerDay + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", comment='" + comment + '\'' +
                ", managerID=" + managerID +
                '}';
    }
}
