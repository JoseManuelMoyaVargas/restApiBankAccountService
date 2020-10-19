package com.restApi_accountService.repo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.model.Transfer;
@DataJpaTest
class TransferRepoTest {
	@Autowired
	private TransferRepo transferRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
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
		acc3.setBalance(new BigDecimal(1000.0)); acc3.setCurrency(Currency.getInstance("USD"));
		acc3.setName("Jose");acc3.setTreasury(false);
		
		entityManager.persist(acc1);
		entityManager.persist(acc2);
		entityManager.persist(acc3);
		entityManager.flush();
		
		Transfer tr1 = new Transfer();
		tr1.setAmount(new BigDecimal(100));
		tr1.setAccOrigin(acc1); tr1.setAccDestination(acc2);
		
		Transfer tr2 = new Transfer();
		tr2.setAmount(new BigDecimal(150));
		tr2.setAccOrigin(acc1); tr2.setAccDestination(acc2);
		
		Transfer tr3 = new Transfer();
		tr3.setAmount(new BigDecimal(200));
		tr3.setAccOrigin(acc2); tr3.setAccDestination(acc1);
		
		Transfer tr4 = new Transfer();
		tr4.setAmount(new BigDecimal(200));
		tr4.setAccOrigin(acc2); tr4.setAccDestination(acc3);
		
		transferRepository.save(tr1);
		transferRepository.save(tr2);
		transferRepository.save(tr3);
		transferRepository.save(tr4);
    }
	@Test
	void testFindAll() {
		//when
		List<Transfer> transfers = transferRepository.findAll();
		//then
		assertEquals(4,transfers.size());
	}
	
	@Test
	void delete_transfer_correctly_v1(){
		//when
		Account foundAccount = transferRepository.findAll().get(0).getAccOrigin();
		transferRepository.deleteAccount(foundAccount);
		List<Transfer> transfers = transferRepository.findAll();
		//then
		assertEquals(1,transfers.size());
	}
	
	@Test
	void delete_transfer_correctly_v2(){
		//when
		Account foundAccount = transferRepository.findAll().get(0).getAccOrigin();
		transferRepository.deleteAccount(foundAccount);
		List<Transfer> transfers = transferRepository.findAll();
		//then
		assertEquals(1,transfers.size());
	}
	@Test
	void delete_transfer_correctly_v3(){
		//when
		Account foundAccount = transferRepository.findAll().get(0).getAccDestination();
		transferRepository.deleteAccount(foundAccount);
		List<Transfer> transfers = transferRepository.findAll();
		//then
		assertEquals(0,transfers.size());
	}
	
	@Test
	void delete_transfer_correctly_v4(){
		//when
		Account foundAccount = transferRepository.findAll().get(3).getAccDestination();
		transferRepository.deleteAccount(foundAccount);
		List<Transfer> transfers = transferRepository.findAll();
		//then
		assertEquals(3,transfers.size());
	}

}
