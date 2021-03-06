package by.epamtc.tsalko.bean;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.bean.content.OrderStatus;

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
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private double totalSum;
    private boolean paid;
    private double discount;
    private String comment;

    private OrderStatus orderStatus;
    private Car car;

    private ReturnAct returnAct;

    public Order() {
        orderStatus = new OrderStatus();
        orderStatus.setStatusID(2);
        orderStatus.setStatus("new");
    }

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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        if (orderId < 0) {
            orderId = 0;
        }
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        if (totalSum < 0) {
            totalSum = 0;
        }
        this.totalSum = totalSum;
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
        if (discount < 0) {
            discount = 0;
        }
        this.discount = discount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Car getCar() {
        return car;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ReturnAct getReturnAct() {
        return returnAct;
    }

    public void setReturnAct(ReturnAct returnAct) {
        this.returnAct = returnAct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return userID == order.userID &&
                orderId == order.orderId &&
                Double.compare(order.totalSum, totalSum) == 0 &&
                paid == order.paid &&
                Double.compare(order.discount, discount) == 0 &&
                Objects.equals(userLogin, order.userLogin) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(pickUpDate, order.pickUpDate) &&
                Objects.equals(dropOffDate, order.dropOffDate) &&
                Objects.equals(comment, order.comment) &&
                Objects.equals(orderStatus, order.orderStatus) &&
                Objects.equals(car, order.car) &&
                Objects.equals(returnAct, order.returnAct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, orderId, orderDate, pickUpDate, dropOffDate,
                totalSum, paid, discount, comment, orderStatus, car, returnAct);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "userID=" + userID +
                ", userLogin='" + userLogin + '\'' +
                ", orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", pickUpDate=" + pickUpDate +
                ", dropOffDate=" + dropOffDate +
                ", totalSum=" + totalSum +
                ", paid=" + paid +
                ", discount=" + discount +
                ", comment='" + comment + '\'' +
                ", orderStatus=" + orderStatus +
                ", car=" + car +
                ", returnAct=" + returnAct +
                '}';
    }
}
