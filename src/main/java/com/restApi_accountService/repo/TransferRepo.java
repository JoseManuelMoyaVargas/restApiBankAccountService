package com.restApi_accountService.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.model.Transfer;


public interface TransferRepo extends JpaRepository<Transfer,Integer>{
	@Transactional
	@Modifying
 	@Query("delete from Transfer t where t.accOrigin=:account or t.accDestination=:account ")
 	public void deleteAccount(@Param("account") Account account);

}
