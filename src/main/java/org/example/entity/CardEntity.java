package org.example.entity;

public class CardEntity {

    private Long id;
    private Long userId;

    private double balance;
    private String number;

    private String firstName;
    private String lastName;

    public CardEntity() {
    }

    public CardEntity(Long id, Long userId, double balance, String number, String firstName, String lastName) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
