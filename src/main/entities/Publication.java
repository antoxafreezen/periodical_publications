package main.entities;

import main.dao.Identified;

/**
 * Entity class to represent publication.
 */
public class Publication implements Identified<Integer> {
    /**
     * Identifier to distinguish publication, primary key in database.
     */
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publication that = (Publication) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Name of publication.
     */
    private String name;
    /**
     * Description of publication.
     */
    private String description;
    /**
     * Price of publication.
     */
    private Float price;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
