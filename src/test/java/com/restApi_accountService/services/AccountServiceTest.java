package com.restApi_accountService.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.*;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.repo.AccountRepo;

import restApi_accountService.exceptions.AccountNotFoundException;

@SpringBootTest
class AccountServiceTest {
	@Autowired
	AccountService accountService;
	
	@MockBean
	AccountRepo accountRepository;
	

	@Test
	public void giveAllValues() {
		//given
		Account acc = new Account();
		acc.setBalance(new BigDecimal(100.0)); acc.setCurrency(Currency.getInstance("USD"));
		acc.setName("Pablo");acc.setTreasury(false);
		Mockito.when(accountRepository.save(acc)).thenReturn(acc);
		//when
		Account foundAccount = accountService.saveAccount(acc);
		//then
		assertEquals("Pablo",foundAccount.getName());
		assertEquals(new BigDecimal(100.0),foundAccount.getBalance());
		assertEquals(Currency.getInstance("USD"),foundAccount.getCurrency());
		assertEquals(false,foundAccount.getTreasury());
	}
	
	@Test
	void not_give_Balancevalue() {
		//given
		Account acc = new Account();
		acc.setCurrency(Currency.getInstance("USD"));
		acc.setName("Pablo");acc.setTreasury(true);
		Mockito.when(accountRepository.save(acc)).thenReturn(acc);
		//when
		Account foundAccount = accountService.saveAccount(acc);
		//then
		assertEquals("Pablo",foundAccount.getName());
		assertEquals(new BigDecimal(0),foundAccount.getBalance());
		assertEquals(Currency.getInstance("USD"),foundAccount.getCurrency());
		assertEquals(true,foundAccount.getTreasury());
	}
	@Test
	void not_give_TreasuryValue() {
		//given
		Account acc = new Account();
		acc.setBalance(new BigDecimal(100.0));
		acc.setCurrency(Currency.getInstance("USD"));
		acc.setName("Pablo");
		Mockito.when(accountRepository.save(acc)).thenReturn(acc);
		//when
		Account foundAccount = accountService.saveAccount(acc);
		//then
		assertEquals("Pablo",foundAccount.getName());
		assertEquals(new BigDecimal(100.0),foundAccount.getBalance());
		assertEquals(Currency.getInstance("USD"),foundAccount.getCurrency());
		assertEquals(false,foundAccount.getTreasury());
	}
	
	@Test
	void getAccount_that_exists() {
		//given
		Account acc = new Account();
		acc.setBalance(new BigDecimal(100.0)); acc.setCurrency(Currency.getInstance("USD"));
		acc.setName("Pablo");acc.setTreasury(false);
		given(accountRepository.findAccountById(1)).willReturn(acc);
	
		//when		
		Account foundAccount = accountService.getAccount(1);
		//then
		assertNotNull(foundAccount);
	}
	@Test
	void getAccount_that_not_exists() {
		//given
		Mockito.when(accountRepository.findById(1)).thenReturn(null);
		//when
		AccountNotFoundException ex=
				Assertions.assertThrows(AccountNotFoundException.class, () -> {
					accountService.getAccount(1);
					
		});
		//then
		assertEquals(ex.getMessage(),"Account not found");
	}
	
	
	@Test
	void deleteAccount_that_not_exists() {
		//given
		Mockito.when(accountRepository.findById(1)).thenReturn(null);
		//when		
		AccountNotFoundException ex=
		Assertions.assertThrows(AccountNotFoundException.class, () -> {
			accountService.deleteAccount(1);
			
		});
		//then
		assertEquals(ex.getMessage(),"Account not found");
	}
	
	@Test
	void deleteAccount_that_exists() {
		//given
		Account acc = new Account();
		acc.setBalance(new BigDecimal(100.0)); acc.setCurrency(Currency.getInstance("USD"));
		acc.setName("Pablo");acc.setTreasury(false);
		given(accountRepository.findAccountById(1)).willReturn(acc);
		//when
		Boolean result = accountService.deleteAccount(1);
		//then
		assertEquals(true,result);
	}
	
	
	
	
	
}
