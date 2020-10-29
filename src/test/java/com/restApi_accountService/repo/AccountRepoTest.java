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
			acc1.setBalance(new BigDecimal(100.0)); 
			acc1.setCurrency(Currency.getInstance("USD"));
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
		
		
		@Test
		public void saveAccount_setting_id() {
			
			//given
			Account ac = accountRepository.findAll().get(0);
			Integer id_ac = ac.getId();
			String name_ac = ac.getName();
			BigDecimal balance_ac = ac.getBalance();
			Boolean treasury_ac = ac.getTreasury();
			Currency currency_ac = ac.getCurrency();
			
			Account acc2 = new Account();
			acc2.setId(id_ac);
			acc2.setName("Marcos");
			acc2.setBalance(new BigDecimal(200000));
			acc2.setTreasury(true);
			acc2.setCurrency(Currency.getInstance("EUR"));
			//when
			accountRepository.save(acc2);
			Account acc1 = accountRepository.findAccountById(id_ac);
			
			//then (the account has been modified)
			assertEquals(acc1.getId(),id_ac);
			assertEquals(acc1.getName(),"Marcos");
			assertEquals(acc1.getBalance(),new BigDecimal(200000));
			assertEquals(acc1.getTreasury(),true);
			assertEquals(acc1.getCurrency(),Currency.getInstance("EUR"));
	
		}
		@Test
		public void saveAccount_not_setting_id() {
			
			//given
			
		
			Account acc2 = new Account();
			acc2.setId(null);
			acc2.setName("Marcos");
			acc2.setBalance(new BigDecimal(200000));
			acc2.setTreasury(true);
			acc2.setCurrency(Currency.getInstance("EUR"));
			//when
			accountRepository.save(acc2);
			int size_list=accountRepository.findAll().size();
			Account found_acc = accountRepository.findAll().get(size_list-1);
			
			//then (the account has been modified)
			assertEquals(acc2.getName(),found_acc.getName());
			assertEquals(acc2.getBalance(),found_acc.getBalance());
			assertEquals(acc2.getTreasury(),found_acc.getTreasury());
			assertEquals(acc2.getCurrency(),found_acc.getCurrency());
	
		}
		
		
		
	

}
