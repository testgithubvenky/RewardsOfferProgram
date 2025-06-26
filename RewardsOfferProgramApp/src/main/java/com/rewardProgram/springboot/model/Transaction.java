package com.rewardProgram.springboot.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Transaction {
	
	private String customerId;
	private String customerName;
	private LocalDate date;
	private double amount;
	public Transaction(String customerId, String customerName, LocalDate date, double amount) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.date = date;
		this.amount = amount;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	

}
