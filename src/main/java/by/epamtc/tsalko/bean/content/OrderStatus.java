package by.epamtc.tsalko.bean.content;

import java.io.Serializable;
import java.util.Objects;

public class OrderStatus implements Serializable {

    private static final long serialVersionUID = -8222145100381409952L;

    private int orderStatusID;
    private String orderStatus;

    public int getOrderStatusID() {
        return orderStatusID;
    }

    public void setOrderStatusID(int orderStatusID) {
        this.orderStatusID = orderStatusID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return orderStatusID == that.orderStatusID &&
                Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatusID, orderStatus);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "orderStatusID=" + orderStatusID +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
