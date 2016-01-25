package main.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom tag to represent duration of something.
 */
public class DurationTag extends TagSupport {
    /**
     * Date format.
     */
    private String format;
    /**
     * Start date.
     */
    private Date startDate;
    /**
     * End date.
     */
    private Date endDate;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            Date start = startDate;
            Date end = endDate;
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            out.print(dateFormatter.format(start) + " - " + dateFormatter.format(end));

        } catch(IOException ioe) {
            throw new JspException("Error: " + ioe.getMessage());
        }
        return SKIP_BODY;
    }


    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
