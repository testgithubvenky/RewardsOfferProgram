package com.rewardProgram.springboot.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rewardProgram.springboot.controller.RewardsController;
import com.rewardProgram.springboot.model.Transaction;
import com.rewardProgram.springboot.service.RewardsService;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {
	

 @Autowired
 private MockMvc mockMvc;
 
 @MockBean
 private RewardsService rewardsService;
 
 private List<Transaction> mockTransactions;
 private Map<String, Map<Month, Integer>> mockMonthlyRewards;
 private Map<String, Integer> mockTotalRewards;
 
 

 @BeforeEach
 void setUp() {
	 mockTransactions = Arrays.asList(
			 new Transaction("C1001", "Alina", LocalDate.of(2025, 4, 4),120),
				new Transaction("C1001", "Alina", LocalDate.of(2025, 3, 5),75),
				new Transaction("C1002", "Bob", LocalDate.of(2025, 1, 23),200)
			 );
	 mockMonthlyRewards = new HashMap<>();
     Map<Month, Integer> alinaRewards = new HashMap<>();
     alinaRewards.put(Month.APRIL, 90);
     alinaRewards.put(Month.FEBRUARY, 70);
     mockMonthlyRewards.put("C1001", alinaRewards);
     mockTotalRewards = new HashMap<>();
     mockTotalRewards.put("C1001", 115);


 }
 

 @Test
 void testGetRewardsSuccess() throws Exception {
	 when(rewardsService.calculateMonthlyRewards(Mockito.anyList())).thenReturn(mockMonthlyRewards);
	 when(rewardsService.calculateTotalRewards(mockMonthlyRewards)).thenReturn(mockTotalRewards);
	 mockMvc.perform(get("/api/rewards"))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$.monthlyRewards.C1001.APRIL").value(90))
	 .andExpect(jsonPath("$.totalRewards.C1001").value(115));
}
 
 
 @Test
 void testGetRewardsException() throws Exception {
	 when(rewardsService.calculateMonthlyRewards(Mockito.anyList())).thenThrow(new RuntimeException("Service error"));

	 mockMvc.perform(get("/api/rewards"))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$.error").value("An error occured"))
	 .andExpect(jsonPath("$.deatisl").value("Service error"));
}

 

 
}



