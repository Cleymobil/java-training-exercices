package io.robusta.birthday.implementations;

import io.robusta.birthday.interfaces.IPeople;
import io.robusta.birthday.interfaces.IPeopleCollection;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nicolas Zozol on 04/10/2016.
 */
public class PeopleCollection extends ArrayList<People> implements IPeopleCollection<People> {

	public PeopleCollection() {

	}

	public PeopleCollection(int size) {
		super(size);
		for (int i = 0; i < size; i++) {
			People people = new People();
			Random random = new Random();
			int date = random.nextInt(365) + 1;
			people.setBirthday(date);
			this.add(people);
		}
	}

	@Override
	public boolean hasSame() {

		/*
		 * return true if two people of the same collection has same birthday
		 */

		int size = this.size();
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (this.get(i).getBirthday() == this.get(j).getBirthday()) {

					return true;
				}
			}
		}

		return false;
	}

}
