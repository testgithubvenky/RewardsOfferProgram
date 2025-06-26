package com.rewardProgram.springboot.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewardProgram.springboot.model.Transaction;
import com.rewardProgram.springboot.service.RewardsService;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {
	
	@Autowired
	private RewardsService rewardsService;
	
	@GetMapping
	public Map<String, Object> getRewards(){
		try {
		List<Transaction> transactions = Arrays.asList(
				new Transaction("C1001", "Alina", LocalDate.of(2025, 4, 4),120),
				new Transaction("C1001", "Alina", LocalDate.of(2025, 3, 5),75),
				new Transaction("C1002", "Bob", LocalDate.of(2025, 1, 23),200),
				new Transaction("C1002", "Bob", LocalDate.of(2025, 11, 4),50),
				new Transaction("C1003", "Roger", LocalDate.of(2025, 5, 11),150)
				);
		
		Map<String, Map<Month, Integer>> monthlyRewards = rewardsService.calculateMonthlyRewards(transactions);
		Map<String, Integer> totalRewards = rewardsService.calculateTotalRewards(monthlyRewards);
		
		return Map.of("monthlyRewards", monthlyRewards,
				"totalRewards", totalRewards);
		}catch(Exception e) {
			System.out.println("Error occured during rewards calculation");
			return Map.of("error", "An error occured","deatisl", e.getMessage());
		}
		
	}

}
