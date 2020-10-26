package by.epamtc.tsalko.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class DiscountTag extends TagSupport {

    private double carPrice;
    private double discount;

    public double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(double carPrice) {
        this.carPrice = carPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            double discountedPricePerDay = carPrice * (1 - discount / 100);
            out.write(String.format("%.2f", discountedPricePerDay));
        } catch (IOException ignore) {}

        return SKIP_BODY;
    }
}
