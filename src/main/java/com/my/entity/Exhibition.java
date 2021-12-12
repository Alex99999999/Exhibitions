package com.my.entity;

import java.io.Serializable;

public class Exhibition implements Serializable {

  private long id;
  private String topic;
  private String description;
  private String startDate;
  private String endDate;
  private String startTime;
  private String endTime;
  private double price;
  private int ticketsAvailable;
  private ExhibitionStatus status;
  private Currency currency;

  private Exhibition() {
  }

  public static Exhibition getInstance() {
    return new Exhibition();
  }

  public long getId() {
    return id;
  }

  public String getTopic() {
    return topic;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public double getPrice() {
    return price;
  }

  public int getTicketsAvailable() {
    return ticketsAvailable;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ExhibitionStatus getStatus() {
    return status;
  }

  public Currency getCurrency() {
    return currency;
  }

  public String getDescription() {
    return description;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setTicketsAvailable(int ticketsAvailable) {
    this.ticketsAvailable = ticketsAvailable;
  }

  public void setStatus(ExhibitionStatus status) {
    this.status = status;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public String toString() {
    return "Exhibition{" +
        "id=" + id +
        ", topic='" + topic + '\'' +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", startTime='" + startTime + '\'' +
        ", endTime='" + endTime + '\'' +
        ", price=" + price +
        ", ticketsAvailable=" + ticketsAvailable +
        ", status=" + status.getStatus() +
        ", currency=" + currency.getCurrency() +
        '}';
  }
}
