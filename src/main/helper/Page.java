package main.helper;

/**
 * Additional class uses in commands.
 */
public class Page {
    /**
     * Page name, that will be displayed later.
     */
    private String page;
    /**
     * Type of retrieving page.
     * <code>true</code> if user will be redirected
     * <code>false</code> if user will be forwarded.
     */
    private boolean redirected;

    /**
     * Empty constructor.
     */
    public Page(){}

    /**
     * Constructor with two parameters.
     * @param page page name
     * @param redirected type of retrieving page
     */
    public Page(String page, boolean redirected){
        this.page = page;
        this.redirected = redirected;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isRedirected() {
        return redirected;
    }

    public void setRedirected(boolean redirected) {
        this.redirected = redirected;
    }
}
