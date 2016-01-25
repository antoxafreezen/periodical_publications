package main.entities;

import main.dao.Identified;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity class to represent subscription.
 */
public class Subscription implements Identified<Integer> {
    /**
     * Identifier to distinguish subscription, primary key in database.
     */
    private Integer id;
    /**
     * Identifier of user, who has subscription, foreign key in database.
     */
    private User user;
    /**
     * Price of subscription.
     */
    private Float price;
    /**
     * Subscribing date.
     */
    private Date startDate = new Date();
    /**
     * End of subscription..
     */
    private Date endDate;
    /**
     * List of publications in this subscription.
     */
    private List<SubscriptionPart> subsParts = new ArrayList<>();

    public List<SubscriptionPart> getSubsParts() {
        return subsParts;
    }

    public void setSubsParts(List<SubscriptionPart> subsParts) {
        this.subsParts = subsParts;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void addSubscriptionPart(SubscriptionPart part){
        subsParts.add(part);
    }
}
