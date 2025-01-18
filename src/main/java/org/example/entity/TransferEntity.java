package org.example.entity;

import org.example.enums.Step;

import java.time.LocalDateTime;

public class TransferEntity {

    private Long userId;

    private String senderCardNumber;

    private String getterCardNumber;

    private double amount;

    private String date;

    private Step step;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSenderCardNumber() {
        return senderCardNumber;
    }

    public void setSenderCardNumber(String senderCardNumber) {
        this.senderCardNumber = senderCardNumber;
    }

    public String getGetterCardNumber() {
        return getterCardNumber;
    }

    public void setGetterCardNumber(String getterCardNumber) {
        this.getterCardNumber = getterCardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
