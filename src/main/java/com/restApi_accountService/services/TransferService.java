package com.restApi_accountService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.repo.TransferRepo;

@Service
public class TransferService {
	@Autowired
	private TransferRepo transferRepo;
	
	public void deleteTransfersOfAccount(Account account) {
		transferRepo.deleteAccount(account);
	}

}
