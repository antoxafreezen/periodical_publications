package main.entities;

import main.dao.Identified;

/**
 * Entity class to represent publications in subscription.
 */
public class SubscriptionPart implements Identified<Integer> {
    /**
     * Identifier to distinguish subscription part, primary key in database.
     */
    private Integer id;
    /**
     * Identifier of publication in subscription, foreign key in database.
     */
    private Publication publication;
    /**
     * Identifier of subscription, foreign key in database.
     */
    private Subscription subscription;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
