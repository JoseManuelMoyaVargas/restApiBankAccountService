package com.restApi_accountService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.services.AccountService;

@RestController
@RequestMapping("account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/save")
	public Account saveAccount(@Valid @RequestBody Account account) {
		return accountService.saveAccount(account);
	}
	
	@GetMapping("/list")
	public List<Account> getAccounts(){
		return accountService.getAccounts();
	}
	
	@GetMapping("/edit/{id}")
    public Account edit(@PathVariable Integer id){
		return accountService.getAccount(id);
    }
	
	
	
}
