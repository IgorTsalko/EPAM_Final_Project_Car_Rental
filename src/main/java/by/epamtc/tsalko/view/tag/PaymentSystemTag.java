package by.epamtc.tsalko.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaymentSystemTag extends TagSupport {

    private static final String URL_VISA_IMG = "img/visa.png";
    private static final String URL_MASTERCARD_IMG = "img/mastercard.png";

    private Long cardNumber;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String curdNumberStr = String.valueOf(cardNumber);
            if (curdNumberStr.startsWith("4")) {
                out.write("<img src=\"" + URL_VISA_IMG + "\">");
            } else if (curdNumberStr.startsWith("5")) {
                out.write("<img src=\"" + URL_MASTERCARD_IMG + "\">");
            }
        } catch (IOException ignore) {}

        return SKIP_BODY;
    }
}
