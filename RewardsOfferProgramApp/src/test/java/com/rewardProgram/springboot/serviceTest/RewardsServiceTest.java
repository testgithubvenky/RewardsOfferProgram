package com.rewardProgram.springboot.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rewardProgram.springboot.model.Transaction;
import com.rewardProgram.springboot.service.RewardsService;

public class RewardsServiceTest {
	
	
	private RewardsService rewardsService;


	@BeforeEach
	void setUp() {
		rewardsService = new RewardsService();
	}


	@Test
	void testCalculateMonthlyRewards() {
		List<Transaction> transactions = Arrays.asList(
				new Transaction("C1001", "Alice", LocalDate.of(2024, 1, 15), 120),
				new Transaction("C1001", "Alice", LocalDate.of(2024, 1, 20), 80),
				new Transaction("C1002", "Bob", LocalDate.of(2024, 2, 10), 60));
		Map<String, Map<Month, Integer>> result = rewardsService.calculateMonthlyRewards(transactions);

		assertNotNull(result);
		assertEquals(2, result.size());

		Map<Month, Integer> aliceRewards = result.get("C1001");
		assertNotNull(aliceRewards);
		assertTrue(aliceRewards.containsKey(Month.JANUARY));

		Map<Month, Integer> bobRewards = result.get("C1002");
		assertEquals(10, bobRewards.get(Month.FEBRUARY));
	}


	@Test
	void testCalculateTotalRewards() {
		Map<String, Map<Month, Integer>> monthlyRewards = new HashMap<>();
		Map<Month, Integer> aliceRewards = new HashMap<>();
		aliceRewards.put(Month.JANUARY, 90);
		aliceRewards.put(Month.FEBRUARY, 60);

		Map<Month, Integer> bobRewards = new HashMap<>();
		bobRewards.put(Month.MARCH, 40);

		monthlyRewards.put("C1001", aliceRewards);
		monthlyRewards.put("C1002", bobRewards);

		Map<String, Integer> result = rewardsService.calculateTotalRewards(monthlyRewards);

		assertEquals(2, result.size());
		assertEquals(150, result.get("C1001"));
		assertEquals(40, result.get("C1002"));
	}




}
