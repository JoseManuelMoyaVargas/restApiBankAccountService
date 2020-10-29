package com.restApi_accountService.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import static org.mockito.BDDMockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restApi_accountService.model.Account;
import com.restApi_accountService.services.AccountService;

import restApi_accountService.exceptions.AccountNotFoundException;
@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {
	  /* We test here AccountController and ExceptionHandlerController */
	  @Autowired
	  private MockMvc mvc;

	  @Autowired
	  private ObjectMapper objectMapper;
	  
	  @MockBean
	  private AccountService mockAccountService;
	  
	  
	  @Test
	  void find_all_accounts() throws Exception {
		  //when
		  	Account acc1 = new Account();
			acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
			acc1.setName("Pablo");acc1.setTreasury(false);
			
			Account acc2 = new Account();
			acc2.setBalance(new BigDecimal(1000.0)); acc2.setCurrency(Currency.getInstance("USD"));
			acc2.setName("Jose");acc2.setTreasury(false);
			
			Account acc3 = new Account();
			acc3.setBalance(new BigDecimal(200.0)); acc3.setCurrency(Currency.getInstance("USD"));
			acc3.setName("Marta");acc3.setTreasury(true);
			
			List<Account> allEmployees = Arrays.asList(acc1,acc2,acc3);
			given(mockAccountService.getAccounts()).willReturn(allEmployees);
		    //given //then
			mvc.perform(get("/account/list")
				      .contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk())
				      .andExpect(jsonPath("$", Matchers.hasSize(3)))
				      .andExpect(jsonPath("$[0].name", Matchers.is("Pablo")));
		 
	  }
	  @Test
	  void good_save_account() throws Exception {
		//given
	    Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
		acc1.setTreasury(false);acc1.setName("Pablo");
	
		given(mockAccountService.saveAccount(acc1)).willReturn(acc1);
		String body = objectMapper.writeValueAsString(acc1);
	    //when//then
	    mvc.perform(post("/account/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(body))
	            .andExpect(status().isOk());
	    
	  }
	  @Test
	  void whenNameIsNull_thenReturnsStatus400() throws Exception {
		//given
	    Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
		acc1.setTreasury(false);
	    //when
	    String body = objectMapper.writeValueAsString(acc1);
	    Mockito.when(mockAccountService.saveAccount(acc1)).thenThrow(HttpMessageNotReadableException.class);
	    //then
	    mvc.perform(post("/account/save")
	            .contentType("application/json")
	            .content(body))
	            .andExpect(status().isBadRequest())
	            ;
	  }
	  @Test
	  void whenCurrencyIsNull_thenReturnsStatus400() throws Exception {
		//given
	    Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(100.0));
		acc1.setName("Pablo");
		acc1.setTreasury(false);
	    //when
	    String body = objectMapper.writeValueAsString(acc1);
	    Mockito.when(mockAccountService.saveAccount(acc1)).thenThrow(HttpMessageNotReadableException.class);
	    //then
	    mvc.perform(post("/account/save")
	            .contentType("application/json")
	            .content(body))
	            .andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  void whenCurrencyIsInvalid_thenReturnStatus400() throws Exception{
		  	//given
			  JSONObject jo = new JSONObject();
			  jo.put("id", null);
			  jo.put("name", "Pablo");
			  jo.put("currency", "US");
			  jo.put("balance", 100);
			  jo.put("treasury", false);
			
			  String body = jo.toString();
			  Mockito.when(mockAccountService.saveAccount(Mockito.any(Account.class))).thenThrow(HttpMessageNotReadableException.class);

		    //when
		    //then
		    mvc.perform(post("/account/save")
		            .contentType("application/json")
		            .content(body))
		            .andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  void whenBalanceIsInvalid_thenReturnStatus400() throws Exception{
		  	//given
			  JSONObject jo = new JSONObject();
			  jo.put("id", null);
			  jo.put("name", "Pablo");
			  jo.put("currency", "US");
			  jo.put("balance", "hello");
			  jo.put("treasury", false);
			
			  String body = jo.toString();
			  Mockito.when(mockAccountService.saveAccount(Mockito.any(Account.class))).thenThrow(HttpMessageNotReadableException.class);
		    //when
			  
		    //then
		    mvc.perform(post("/account/save")
		            .contentType("application/json")
		            .content(body))
		            .andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  void whenAccountDoesNotExist_thenReturnStatus400() throws Exception{
		  	//given
		  	Mockito.when(mockAccountService.getAccount(10)).thenThrow(AccountNotFoundException.class);
		    //when
		    //then
		    mvc.perform(get("/account/edit/10"))
		            .andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  void deleteAccountDoesNotExist_thenReturnStatus400() throws Exception{
		  	//given
		  	Mockito.when(mockAccountService.deleteAccount(10)).thenThrow(AccountNotFoundException.class);
		    //when
		    //then
		    mvc.perform(delete("/account/remove/10"))
		            .andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  void deleteAccountExist() throws Exception{
		  	//given
		  	Mockito.when(mockAccountService.deleteAccount(10)).thenReturn(true);
		    //when
		    //then
		  	MvcResult result=mvc.perform(delete("/account/remove/10"))
		            .andExpect(status().isOk()).andReturn();
		  	
		  	String content = result.getResponse().getContentAsString();
		    assertEquals(content,"Account deleted correctly!");
	  }
	  

}
