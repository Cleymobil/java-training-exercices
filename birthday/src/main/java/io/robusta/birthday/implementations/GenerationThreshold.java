package io.robusta.birthday.implementations;

import io.robusta.birthday.interfaces.IGenerationThreshold;

/**
 * Created by Nicolas Zozol on 04/10/2016.
 */
public class GenerationThreshold implements IGenerationThreshold {

	public GenerationThreshold() {

	}

	@Override
	public int getSmallNumber() {

		return 20;
	}

	@Override
	public int getBigNumber() {
		return 30;
	}

	@Override
	public int findSmallestNumberOfPeopleRequiredToHave50() {
		int i = 1;
		while (calculateProbabilityOfSame(i) < 0.5) {
			i++;
		}

		return i;
	}

	@Override
	public float calculateProbabilityOfSame(int size) {
		Generation generation = new Generation(10000, size);

		return (float)generation.getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday() / (float) 10000;
	}
}
