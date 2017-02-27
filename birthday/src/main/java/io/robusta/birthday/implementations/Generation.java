package io.robusta.birthday.implementations;

import java.util.ArrayList;
import java.util.List;

import io.robusta.birthday.interfaces.IGeneration;

/**
 * Created by Nicolas Zozol on 04/10/2016.
 */
public class Generation implements IGeneration {

	List<PeopleCollection> collections;

	public Generation() {

	}

	public Generation(int n, int collectionSize) {
		this.collections = createAllRandom(n, collectionSize);
	}

	@Override
	public PeopleCollection createRandom(int size) {

		PeopleCollection PeopleCollection = new PeopleCollection(size);

		return PeopleCollection;
	}

	@Override
	public List<PeopleCollection> createAllRandom(int n, int size) {
		// call n times createRandom(size)
		List<PeopleCollection> collection = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			collection.add(createRandom(size));
		}
		
		return collection;
	}

	@Override
	public List<PeopleCollection> getPeopleCollections() {

		return this.collections;
	}

	@Override
	public int getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday() {
		
		int size=collections.size();
		int l=0;
		for (int i=0;i<size;i++){
			if (collections.get(i).hasSame()){
				l=l+1;
			}
		}
		
		
		
		return l;
	}

	@Override
	public boolean isLessThan50() {
		
		//int lol=collections.size()/2;
		if ((float)getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday()/(float)collections.size()<0.5){
			return true;
		}else{
			return false;
		}
		

	}

}
