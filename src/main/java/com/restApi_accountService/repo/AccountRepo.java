package com.restApi_accountService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restApi_accountService.model.Account;

public interface AccountRepo extends JpaRepository<Account,Integer> {
	@Query("SELECT acc FROM Account acc WHERE LOWER(acc.name) LIKE LOWER(concat('%', :name,'%'))")
    public List<Account> findByName(@Param("name") String name);
	
	@Query("SELECT acc FROM Account acc WHERE LOWER(acc.currency) LIKE LOWER(concat('%', :currency,'%'))")
    public List<Account> findByCurrency(@Param("currency") String currency);
	
	public Account findAccountById(Integer id);
}
