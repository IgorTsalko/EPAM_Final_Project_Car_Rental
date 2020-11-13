package by.epamtc.tsalko.bean.content;

import java.io.Serializable;
import java.util.Objects;

public class OrderStatus implements Serializable {

    private static final long serialVersionUID = -8222145100381409952L;

    private int statusID;
    private String status;

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return statusID == that.statusID &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusID, status);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "orderStatusID=" + statusID +
                ", orderStatus='" + status + '\'' +
                '}';
    }
}
