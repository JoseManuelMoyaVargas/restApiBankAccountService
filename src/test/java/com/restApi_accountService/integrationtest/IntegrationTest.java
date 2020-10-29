package com.restApi_accountService.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Currency;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restApi_accountService.model.Account;
import com.restApi_accountService.services.AccountService;
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
		
		//we save in the real account service
		accountService.saveAccount(acc1);
		accountService.saveAccount(acc2);
		accountService.saveAccount(acc3);
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
	
	
}
