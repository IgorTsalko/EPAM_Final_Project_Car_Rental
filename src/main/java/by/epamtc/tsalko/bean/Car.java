package by.epamtc.tsalko.bean;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {

    private static final long serialVersionUID = 7665785395449624149L;

    private int carID;
    private boolean available;
    private String brand;
    private String model;
    private int yearProduction;
    private String transmission;
    private String engineSize;
    private String fuelType;
    private int odometerValue;
    private String pricePerDay;
    private String mainImageURI;
    private String comment;

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearProduction() {
        return yearProduction;
    }

    public void setYearProduction(int yearProduction) {
        this.yearProduction = yearProduction;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getOdometerValue() {
        return odometerValue;
    }

    public void setOdometerValue(int odometerValue) {
        this.odometerValue = odometerValue;
    }

    public String getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getMainImageURI() {
        return mainImageURI;
    }

    public void setMainImageURI(String mainImageURI) {
        this.mainImageURI = mainImageURI;
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
        Car car = (Car) o;
        return carID == car.carID &&
                available == car.available &&
                yearProduction == car.yearProduction &&
                odometerValue == car.odometerValue &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(transmission, car.transmission) &&
                Objects.equals(engineSize, car.engineSize) &&
                Objects.equals(fuelType, car.fuelType) &&
                Objects.equals(pricePerDay, car.pricePerDay) &&
                Objects.equals(mainImageURI, car.mainImageURI) &&
                Objects.equals(comment, car.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carID, available, brand, model, yearProduction, transmission, engineSize,
                fuelType, odometerValue, pricePerDay, mainImageURI, comment);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "carID=" + carID +
                ", available=" + available +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearProduction=" + yearProduction +
                ", transmission='" + transmission + '\'' +
                ", engineSize='" + engineSize + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", odometerValue=" + odometerValue +
                ", pricePerDay='" + pricePerDay + '\'' +
                ", mainImageURI='" + mainImageURI + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
