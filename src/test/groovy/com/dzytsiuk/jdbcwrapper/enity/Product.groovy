package com.dzytsiuk.jdbcwrapper.enity

import java.time.LocalDateTime

class Product {
    private long id
    private LocalDateTime creationDate
    private String name
    private double price

    Product() {
    }

    Product(long id, LocalDateTime creationDate, String name, double price) {
        this.id = id
        this.creationDate = creationDate
        this.name = name
        this.price = price
    }


    long getId() {
        return id
    }

    void setId(long id) {
        this.id = id
    }

    LocalDateTime getCreationDate() {
        return creationDate
    }

    void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    double getPrice() {
        return price
    }

    void setPrice(double price) {
        this.price = price
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Product product = (Product) o

        if (id != product.id) return false
        if (Double.compare(product.price, price) != 0) return false
        if (creationDate != product.creationDate) return false
        if (name != product.name) return false

        return true
    }

    int hashCode() {
        int result
        long temp
        result = (int) (id ^ (id >>> 32))
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L
        result = 31 * result + (int) (temp ^ (temp >>> 32))
        return result
    }
}


