package com.restApi_accountService.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Transfer {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "accountOrigin_id")
    Account accOrigin;
 
	@ManyToOne
    @JoinColumn(name = "accountDestination_id")
    Account accDestination;
	
	@Column
	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccOrigin() {
		return accOrigin;
	}

	public void setAccOrigin(Account accOrigin) {
		this.accOrigin = accOrigin;
	}

	public Account getAccDestination() {
		return accDestination;
	}

	public void setAccDestination(Account accDestination) {
		this.accDestination = accDestination;
	}
	
	
}
