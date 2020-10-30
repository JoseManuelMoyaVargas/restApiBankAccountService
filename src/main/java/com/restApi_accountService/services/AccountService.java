package com.restApi_accountService.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.repo.AccountRepo;

import restApi_accountService.exceptions.AccountNotFoundException;

@Service
public class AccountService {
	@Autowired
	private AccountRepo accountRepo;
	
	
	public Account saveAccount(Account acc) {
		 //User has to provide name and currency
		 if(acc.getBalance()==null) {
			acc.setBalance(new BigDecimal(0));
		 }
		 if(acc.getTreasury()==null) {
			acc.setTreasury(false);
		 }
		 
		 if(acc.getId()!=null) {
			 acc.setId(null);
		 }
		 
		 return accountRepo.save(acc);
	}
	public List<Account> getAccounts(){
		return accountRepo.findAll();
	}
	
	public Account getAccount(Integer id_account) throws AccountNotFoundException{
		//Account foundAccount = accountRepo.findById(id_account).get();
		Account foundAccount = accountRepo.findAccountById(id_account);
		if(foundAccount==null) {
			throw new AccountNotFoundException();
		}
		return foundAccount;
	}
	
	public Boolean deleteAccount(Integer id_account)throws AccountNotFoundException {
		Account foundAccount = accountRepo.findAccountById(id_account);
		if(foundAccount==null) {
			throw new AccountNotFoundException();
		}
		accountRepo.delete(foundAccount);
		return true;
	}
	
	public Account editAccount(Account acc) throws AccountNotFoundException {
		/* If the account does not exist.*/
		Integer id_account= acc.getId();
		Account foundAccount = accountRepo.findAccountById(id_account);
		if(foundAccount==null) {
			throw new AccountNotFoundException();
		}
		/* If the account does exist.*/
		
		//Requirement : Treasury can not be edited so we only overrite the rest of fields.
		foundAccount.setName(acc.getName());
		foundAccount.setBalance(acc.getBalance());
		foundAccount.setCurrency(acc.getCurrency());
		
		return accountRepo.save(foundAccount);
	}
	
	public List<Account> find_by_name(String name){
		return accountRepo.findByName(name);
	}
	
}
