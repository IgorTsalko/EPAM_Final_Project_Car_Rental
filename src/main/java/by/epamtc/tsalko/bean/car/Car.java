package by.epamtc.tsalko.bean.car;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Car implements Serializable {

    private static final long serialVersionUID = 7665785395449624149L;

    private int carID;
    private String brand;
    private String model;
    private int yearProduction;
    private String transmission;
    private String engineSize;
    private String fuelType;
    private int odometerValue;
    private double pricePerDay;
    private String comment;

    private List<String> carImages;

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        if (carID < 0) {
            carID = 0;
        }
        this.carID = carID;
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
        if (yearProduction < 0) {
            yearProduction = 0;
        }
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
        if (odometerValue < 0) {
            odometerValue = 0;
        }
        this.odometerValue = odometerValue;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        if (pricePerDay < 0) {
            pricePerDay = 0;
        }
        this.pricePerDay = pricePerDay;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<String> carImages) {
        this.carImages = carImages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carID == car.carID &&
                yearProduction == car.yearProduction &&
                odometerValue == car.odometerValue &&
                Double.compare(car.pricePerDay, pricePerDay) == 0 &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(transmission, car.transmission) &&
                Objects.equals(engineSize, car.engineSize) &&
                Objects.equals(fuelType, car.fuelType) &&
                Objects.equals(comment, car.comment) &&
                Objects.equals(carImages, car.carImages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carID, brand, model, yearProduction, transmission, engineSize,
                fuelType, odometerValue, pricePerDay, comment, carImages);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "carID=" + carID +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearProduction=" + yearProduction +
                ", transmission='" + transmission + '\'' +
                ", engineSize='" + engineSize + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", odometerValue=" + odometerValue +
                ", pricePerDay=" + pricePerDay +
                ", comment='" + comment + '\'' +
                ", carImages=" + carImages +
                '}';
    }
}
