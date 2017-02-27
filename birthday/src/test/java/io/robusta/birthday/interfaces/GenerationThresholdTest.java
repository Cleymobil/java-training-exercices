package io.robusta.birthday.interfaces;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.robusta.birthday.implementations.Generation;
import io.robusta.birthday.implementations.GenerationThreshold;

public class GenerationThresholdTest {

	GenerationThreshold generationThreshold;

	@Before
	public void setUp() throws Exception {
		generationThreshold = new GenerationThreshold();

	}

	@Test
	public void proba365() {
		assertTrue(generationThreshold.calculateProbabilityOfSame(365) > 0.9);
	}

	@Test
	public void findSmallestNumberOfPeopleRequiredToHave50() {

		assertTrue(generationThreshold.findSmallestNumberOfPeopleRequiredToHave50() == 23);
	}

}
