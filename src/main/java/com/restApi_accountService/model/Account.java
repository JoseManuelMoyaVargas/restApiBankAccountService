package com.restApi_accountService.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @RequiredArgsConstructor
public class Account {
	@Id
	@GeneratedValue
	private Integer id;
	
	//@NotEmpty(message = "Name is mandatory")
	//@Column
	@NotNull
	private String name;
	
	@NotNull
	private Currency currency;
	
	@Column
	private BigDecimal balance;
	
	@Column
	private Boolean treasury;
	
	@OneToMany(mappedBy = "accOrigin")
    Set<Transfer> transfersOrigin;
	
	@OneToMany(mappedBy = "accDestination")
    Set<Transfer> transfersDestination;
	/*
	 * WE COMMENT THIS BECAUSE WE HAVE ADDED LOMBOK THAT DOES IT FOR US.
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Boolean getTreasury() {
		return treasury;
	}

	public void setTreasury(Boolean treasury) {
		this.treasury = treasury;
	}*/
}
