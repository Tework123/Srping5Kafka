package ru.tework.spring6kafka;

import java.math.BigDecimal;

public class KafkaEventMy {
    private Integer kafkaId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public KafkaEventMy() {
    }

    public KafkaEventMy(Integer kafkaId, String title, BigDecimal price, Integer quantity) {
        this.kafkaId = kafkaId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getKafkaId() {
        return kafkaId;
    }

    public void setKafkaId(Integer kafkaId) {
        this.kafkaId = kafkaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
