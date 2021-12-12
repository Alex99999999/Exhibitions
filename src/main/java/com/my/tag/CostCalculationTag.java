package com.my.tag;

import com.my.entity.Booking;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;

public class CostCalculationTag extends TagSupport {

  private static final Logger LOG = Logger.getLogger(CostCalculationTag.class);
  private Booking booking;
  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  @Override
  public int doStartTag() throws JspException {
    double price = booking.getExhibition().getPrice();
    int ticketQty = booking.getTicketQty();
    double result = price * ticketQty;
    try {
      pageContext.getOut().println(result);
    } catch (IOException e) {
      LOG.error("Unable to deliver tag results");
      throw new JspException(e.getMessage());
    }
    return SKIP_BODY;
  }
}
