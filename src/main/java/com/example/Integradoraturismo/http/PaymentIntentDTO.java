package com.example.Integradoraturismo.http;

public class PaymentIntentDTO {
  public enum Currency {
    USD, EUR;
  }

  private String description;
  private int amount;
  private Currency currency;

  public PaymentIntentDTO(String description, int amount, Currency currency) {
    this.description = description;
    this.amount = amount;
    this.currency = currency;
  }

  public String getDescription() {
    return description;
  } 

  public int getAmount() {
    return amount;
  }

  public Currency getCurrency() {
    return currency;
  }   

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
