package com.restApi_accountService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.restApi_accountService.model.Account;
import com.restApi_accountService.services.AccountService;

@RestController
@RequestMapping("account")
@CrossOrigin(origins="*")
//@CrossOrigin(origins = "http://localhost:8000/")
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
	
	@GetMapping("/get_account/{id}")
    public Account get_account(@PathVariable Integer id){
		return accountService.getAccount(id);
    }
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> remove(@PathVariable Integer id){
		accountService.deleteAccount(id);
		return new ResponseEntity<>("Account deleted correctly!", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAccount(@Valid @RequestBody Account account) {
		accountService.editAccount(account);
		return new ResponseEntity<>("Account edited correctly!", HttpStatus.OK);
	}
	
	@GetMapping("/find_name")
    public List<Account> find_by_name(@Param("searchInput") String searchInput){
		//System.out.println(searchInput);
		return accountService.find_by_name(searchInput);
		
    }
	
	
	

	
}
