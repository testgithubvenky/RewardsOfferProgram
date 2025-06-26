package com.rewardProgram.springboot.service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rewardProgram.springboot.model.Transaction;

@Service
public class RewardsService {
	
	
	public Map<String, Map<Month, Integer>> calculateMonthlyRewards(List<Transaction> transactions){
		
		Map<String, Map<Month, Integer>> rewards = new HashMap<>();
		for(Transaction transaction : transactions) {
			String customerId = transaction.getCustomerId();
			Month month = transaction.getDate().getMonth();
			int points = RewardCalculator.calculatePoints(transaction.getAmount());
			
			rewards.putIfAbsent(customerId, new HashMap<>());
			Map<Month, Integer> monthlyRewards = rewards.get(customerId);
			monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
			
		}
		return rewards;
		
	}
	
	public Map<String, Integer> calculateTotalRewards(Map<String, Map<Month, Integer>> monthlyRewards){
		Map<String, Integer> totalRewards = new HashMap<>();
		
		for(Map.Entry<String, Map<Month, Integer>> entry : monthlyRewards.entrySet()) {
			String customerId = entry.getKey();
			int totalPoints = entry.getValue().values().stream().mapToInt(Integer::intValue).sum();
			totalRewards.put(customerId, totalPoints);
		}
		return totalRewards;
	}

}
