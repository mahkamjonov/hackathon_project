package org.example.entity;

import org.example.enums.Step;

public class TolovEntity {

    private Long userId;

    private Long id;

    private double amount;

    private String type;

    private Step step;

    private String date;

    private String phoneNumber;

    private String senderCardNumber;

    public TolovEntity() {
    }

    public TolovEntity(Long userId, Long id, double amount, String type, Step step, String date, String phoneNumber, String senderCardNumber) {
        this.userId = userId;
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.step = step;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.senderCardNumber = senderCardNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSenderCardNumber() {
        return senderCardNumber;
    }

    public void setSenderCardNumber(String senderCardNumber) {
        this.senderCardNumber = senderCardNumber;
    }
}
