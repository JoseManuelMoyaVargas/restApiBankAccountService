package com.restApi_accountService.repo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.restApi_accountService.model.Account;

@DataJpaTest
class AccountRepoTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private AccountRepo accountRepository;
	
		@BeforeEach 
	    public void init() {
			//given
			Account acc1 = new Account();
			acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
			acc1.setName("Pablo");acc1.setTreasury(false);
			
			Account acc2 = new Account();
			acc2.setBalance(new BigDecimal(1000.0)); acc2.setCurrency(Currency.getInstance("USD"));
			acc2.setName("Jose");acc2.setTreasury(false);
			
			Account acc3 = new Account();
			acc3.setBalance(new BigDecimal(200.0)); acc3.setCurrency(Currency.getInstance("USD"));
			acc3.setName("Marta");acc3.setTreasury(true);
			
			accountRepository.save(acc1); 	accountRepository.save(acc2); 	accountRepository.save(acc3);
	    }
		
		@Test
		public void testFindAll() {
			//when
			List<Account> accounts = accountRepository.findAll();
			//then
			assertEquals(3,accounts.size());
		}
		
		@Test
		public void whenFindByName_returnAccount() {
			//when
			Account accountFound = accountRepository.findByName("Pa").get(0);
			//then
			assertEquals("Pablo",accountFound.getName());
		}
		
		@Test
		public void testFindByNameAfterDeletion() {
			//when
			Account account_to_delete = accountRepository.findByName("Pa").get(0);
			accountRepository.delete(account_to_delete);
			List<Account> accounts = accountRepository.findByName("Pa");
			//then
			assertEquals(0,accounts.size());
		}
		
		@Test
		public void testFindMoreThanOneByName() {
			//when
			List<Account>foundAccounts = accountRepository.findByName("o");
			//then 
			assertEquals(2,foundAccounts.size());
		}
		
		@Test 
		public void findByCurrency_completeName() {
			//when
			List<Account> foundAccounts = accountRepository.findByCurrency("USD");
			//then
			assertEquals(3,foundAccounts.size());
		}
		
		@Test 
		public void findByCurrency_uncompleteName() {
			//when
			List<Account> foundAccounts = accountRepository.findByCurrency("U");
			//then
			assertEquals(3,foundAccounts.size());
		}
		
		
	

}
