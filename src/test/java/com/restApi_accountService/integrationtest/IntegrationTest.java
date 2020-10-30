package com.restApi_accountService.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Currency;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restApi_accountService.model.Account;
import com.restApi_accountService.services.AccountService;

import restApi_accountService.exceptions.AccountNotFoundException;
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTest {
	@Autowired
	 private MockMvc mvc;
	
	@Autowired
	  private ObjectMapper objectMapper;
	
	@Autowired
	private AccountService accountService;
	
	@Test
	void test_find_allaccounts() throws Exception {
		
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
		
		//we save in the real account service
		accountService.saveAccount(acc1);
		accountService.saveAccount(acc2);
		accountService.saveAccount(acc3);
		//given //then
		MvcResult result=mvc.perform(get("/account/list")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(4)))
			      .andExpect(jsonPath("$[0].name", Matchers.is("Pablo Morales Vargas"))).andReturn();
		
	}
	
	
	@Test
	  void good_save_account() throws Exception {
		//given
	    Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
		acc1.setTreasury(false);acc1.setName("Pablo");
	
		accountService.saveAccount(acc1);
		String body = objectMapper.writeValueAsString(acc1);
	    //when//then
		mvc.perform(post("/account/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(body))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name", Matchers.is("Pablo")))
	            .andExpect(jsonPath("$.balance", Matchers.is(100)))
				.andExpect(jsonPath("$.currency", Matchers.is("USD")))
				.andExpect(jsonPath("$.treasury", Matchers.is(false)));
	  }
	
	@Test
	  void whenNameIsNull_thenReturnsStatus400() throws Exception {
		//given
	    Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
		acc1.setTreasury(false);
	    //when
	    String body = objectMapper.writeValueAsString(acc1);
	    //then
	    mvc.perform(post("/account/save")
	            .contentType("application/json")
	            .content(body))
	            .andExpect(status().isBadRequest());
	            
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
		    //when
		    //then
		    mvc.perform(get("/account/get_account/10"))
		            .andExpect(status().isBadRequest());
	  }
	
	@Test
	  void deleteAccountDoesNotExist_thenReturnStatus400() throws Exception{
		  	//given
		    //when
		    //then
		    mvc.perform(delete("/account/remove/10"))
		            .andExpect(status().isBadRequest());
	  }
	
	@Test
	  void deleteAccountExist() throws Exception{
		  	//given
			Account acc1 = new Account();
			acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
			acc1.setName("Pablo");acc1.setTreasury(false);
			
			accountService.saveAccount(acc1);
			Account acc = accountService.getAccounts().get(0);
			Integer id_account =  acc.getId();
			
		    //when
		    //then
		  	MvcResult result=mvc.perform(delete("/account/remove/"+id_account))
		            .andExpect(status().isOk()).andReturn();
		  	
		  	String content = result.getResponse().getContentAsString();
		    assertEquals(content,"Account deleted correctly!");
	  }
	
	@Test
      void save_account_setting_id() {
		//given
		Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(20000));
		acc1.setCurrency(Currency.getInstance("EUR"));
		acc1.setName("John");
		acc1.setTreasury(false);
		accountService.saveAccount(acc1);
		
		//when
		int size_accounts =  accountService.getAccounts().size();
		Account foundAccount = accountService.getAccounts().get(size_accounts-1);
		Account acc2 = new Account();
		acc2.setId(foundAccount.getId());
		acc2.setBalance(new BigDecimal(30000));
		acc2.setCurrency(Currency.getInstance("USD"));
		acc2.setName("John 2");
		acc2.setTreasury(true);
		accountService.saveAccount(acc2);
		
		//then
		Account foundAccount2 = accountService.getAccount(foundAccount.getId());
		assertEquals("John",foundAccount2.getName());
		assertEquals(false,foundAccount2.getTreasury());
		
	}
	
	
	@Test
	  void edit_accout_that_exists() throws Exception{
		  	
		    //given
			Account acc = new Account();
			acc.setBalance(new BigDecimal(100.0));
			acc.setCurrency(Currency.getInstance("USD"));
			acc.setTreasury(true);
			acc.setName("Pablo");
			Account foundAccount = accountService.saveAccount(acc);
			
		
			//when
			foundAccount.setBalance(new BigDecimal(200.0));
			foundAccount.setCurrency(Currency.getInstance("EUR"));
			foundAccount.setTreasury(false);//we change this
			foundAccount.setName("Pablo Morales Vargas");
		
			String body = objectMapper.writeValueAsString(foundAccount);
			
			//when//then
			MvcResult result=mvc.perform(post("/account/edit")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(body))
		            .andExpect(status().isOk()).andReturn();
			String content = result.getResponse().getContentAsString();
		    assertEquals(content,"Account edited correctly!");
	  }
	  
	  @Test
	  void edit_accout_that_not_exists() throws Exception{
		 //given
		  Account acc1 = new Account();
		  acc1.setId(1);
		  acc1.setBalance(new BigDecimal(100.0));
		  acc1.setCurrency(Currency.getInstance("USD"));
		  acc1.setTreasury(false);acc1.setName("Pablo");
		  String body = objectMapper.writeValueAsString(acc1);
		  
		  //when
		 //then
		  mvc.perform(post("/account/edit")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(body))
		            .andExpect(status().isBadRequest());
	
	  }
	
	
	
}
