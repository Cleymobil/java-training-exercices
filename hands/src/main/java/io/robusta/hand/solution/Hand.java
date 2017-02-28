package io.robusta.hand.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.HandClassifier;
import io.robusta.hand.HandValue;
import io.robusta.hand.interfaces.IDeck;
import io.robusta.hand.interfaces.IHand;
import io.robusta.hand.interfaces.IHandResolver;

public class Hand extends TreeSet<Card> implements IHand {

	private static final long serialVersionUID = 7824823998655881611L;

	@Override
	public Set<Card> changeCards(IDeck deck, Set<Card> cards) {
		// For exemple remove three cards from this hand
		// , and get 3 new ones from the Deck
		// returns the new given cards
		int size = cards.size();
		this.removeAll(cards);
		TreeSet<Card> newCards = deck.pick(size);

		return newCards;
	}

	@Override
	public boolean beats(IHand villain) {

		if (this.getClassifier().getValue() > villain.getClassifier().getValue()) {
			return true;
		}

		else if (this.getValue().getLevelValue() > villain.getValue().getLevelValue()) {
			return true;
		}

		else if (this.getValue().getSecondLevel() > villain.getValue().getSecondLevel()) {
			return true;
		}

		ArrayList<Card> yourRemainingsCards = new ArrayList<>();
		ArrayList<Card> villainRemainingsCards = new ArrayList<>();
		yourRemainingsCards.addAll(this.getValue().getOtherCards());
		villainRemainingsCards.addAll(villain.getValue().getOtherCards());

		for (int i = 0; i < this.getValue().getOtherCards().size(); i++) {
			if (yourRemainingsCards.get(this.getValue().getOtherCards().size() - (i + 1))
					.getValue() > villainRemainingsCards.get(this.getValue().getOtherCards().size() - (i + 1))
							.getValue()) {
				return true;
			}

		}

		return false;
	}

	@Override
	public IHand getHand() {
		return this;
	}

	@Override
	public HandClassifier getClassifier() {

		return this.getValue().getClassifier();
	}

	@Override
	public boolean isStraight() {
		/*
		 * Card c = this.first();
		 * 
		 * for (Card d : this) { if (c.equals(d) == false) { if (d.getValue() !=
		 * c.getValue() + 1) { return false; } else { c = d; } } } return true;
		 */

		ArrayList<Card> cards = new ArrayList<>();
		cards.addAll(this);
		if (this.isLowStraight()) {
			return true;
		}

		for (int i = 1; i < 5; i++) {
			if (cards.get(i).getValue() != cards.get(i - 1).getValue() + 1) {
				return false;

			}
		}
		return true;
	}

	public boolean isLowStraight() {

		ArrayList<Card> cards = new ArrayList<>();
		cards.addAll(this);
		if (cards.get(0).getValue() == 2 && cards.get(1).getValue() == 3 && cards.get(2).getValue() == 4
				&& cards.get(3).getValue() == 5 && cards.get(4).getValue() == 14) {

			return true;
		}
		return false;
	}

	@Override
	public boolean isFlush() {

		Card c = this.first();

		for (Card d : this) {
			if (c.equals(d) == false) {
				if (d.getColor() != c.getColor()) {
					return false;
				} else {
					c = d;
				}
			}
		}
		return true;
	}

	/**
	 * Returns number of identical cards 5s5cAd2s3s has two cardValue of 5
	 */
	@Override
	public int number(int cardValue) {
		int result = 0;
		for (Card current : this) {
			if (current.getValue() == cardValue) {
				result++;
			}
		}
		return result;
	}

	/**
	 * The fundamental map Check the tests and README to understand
	 */
	@Override
	public HashMap<Integer, List<Card>> group() {
		HashMap<Integer, List<Card>> map = new HashMap<>();
		// remplir la map pour chaque carte de la main
		for (Card current : this) {
			// si aucune valeur n'est sortie
			if (map.get(current.getValue()) == null) {

				ArrayList<Card> cards = new ArrayList<>();
				cards.add(current);
				map.put(current.getValue(), cards);

			} else {

				map.get(current.getValue()).add(current);

			}

		}

		return map;
	}

	// different states of the hand
	int mainValue;
	int tripsValue;
	int pairValue;
	int secondValue;
	TreeSet<Card> remainings;

	/**
	 * return all single cards not used to build the classifier
	 * 
	 * @param map
	 * @return
	 */
	TreeSet<Card> getGroupRemainingsCard(Map<Integer, List<Card>> map) {
		TreeSet<Card> groupRemaining = new TreeSet<>();
		// May be adapted at the end of project:
		// if straight or flush : return empty
		// If High card, return 4 cards

		for (List<Card> group : map.values()) {
			if (group.size() == 1) {
				groupRemaining.add(group.get(0));
			}
		}
		return groupRemaining;
	}

	@Override
	public boolean isPair() {

		int size = this.group().size();
		if (size == 4) {
			for (Card current : this) {
				if (this.group().get(current.getValue()).size() == 2) {
					mainValue = current.getValue();
				}

			}
			return true;
		}
		return false;

	}

	@Override
	public boolean isDoublePair() {

		int size = this.group().size();
		if (size == 3 && this.getGroupRemainingsCard(this.group()).size() == 1) {
			ArrayList<Integer> pairCards = new ArrayList<>();
			for (Card current : this) {
				if (this.group().get(current.getValue()).size() == 2) {
					pairCards.add(current.getValue());
				}
			}
			mainValue = Collections.max(pairCards);
			secondValue = Collections.min(pairCards);
			return true;
		}

		return false;
	}

	@Override
	public boolean isHighCard() {

		ArrayList<Card> cards = new ArrayList<>();
		cards.addAll(this);
		if (this.isFlush() || this.isStraight())
			return false;
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; i < 5; i++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					return false;
				}

			}
		}

		return true;
	}

	@Override
	public boolean isTrips() {

		int size = this.group().size();
		if (size == 3) {
			for (Card current : this) {
				if (this.group().get(current.getValue()).size() == 3) {
					mainValue = current.getValue();
					return true;
				}

			}

		}
		return false;

	}

	@Override
	public boolean isFourOfAKind() {

		for (Card current : this) {
			if (this.group().get(current.getValue()).size() == 4) {
				mainValue = current.getValue();
				return true;
			}
		}

		return false;

	}

	@Override
	public boolean isFull() {
		int size = this.group().size();
		if (size == 2) {
			for (Card current : this) {
				if (this.group().get(current.getValue()).size() == 3) {
					mainValue = current.getValue();
					return true;
				}

			}

		}
		return false;
	}

	@Override
	public boolean isStraightFlush() {
		if (this.isStraight() && this.isFlush()) {
			return true;
		}
		return false;
	}

	@Override
	public HandValue getValue() {
		HandValue handValue = new HandValue();

		// !!!Warning!!! Put the most compelling on the beginning.
		if (this.isHighCard()) {
			handValue.setClassifier(HandClassifier.HIGH_CARD);
			handValue.setLevelValue(this.last().getValue());
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isFull()) {
			handValue.setClassifier(HandClassifier.FULL);
			handValue.setLevelValue(this.mainValue);
			handValue.setSecondLevel(this.first().getValue());
			return handValue;
		}

		if (this.isStraightFlush()) {
			handValue.setClassifier(HandClassifier.STRAIGHT_FLUSH);
			handValue.setLevelValue(this.last().getValue());
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}
		if (this.isStraight()) {
			handValue.setClassifier(HandClassifier.STRAIGHT);
			handValue.setLevelValue(this.last().getValue());
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isFlush()) {
			handValue.setClassifier(HandClassifier.FLUSH);
			handValue.setLevelValue(this.last().getValue());
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isPair()) {
			handValue.setClassifier(HandClassifier.PAIR);
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isDoublePair()) {
			handValue.setClassifier(HandClassifier.TWO_PAIR);
			handValue.setLevelValue(this.mainValue);
			handValue.setSecondLevel(this.secondValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		if (this.isTrips()) {
			handValue.setClassifier(HandClassifier.TRIPS);
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group()));
			return handValue;
		}

		// Exemple for FourOfAKind ; // do for all classifiers
		if (this.isFourOfAKind())

		{
			handValue.setClassifier(HandClassifier.FOUR_OF_A_KIND);
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.getGroupRemainingsCard(this.group())); // or
																				// this.getRemainings()
			return handValue;
		}

		return handValue;
	}

	@Override
	public boolean hasCardValue(int level) {

		if (this.last().getValue() == level) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasAce() {

		for (Card current : this) {
			if (current.getValue() == 14) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int highestValue() {

		return this.last().getValue();
	}

	@Override
	public int compareTo(IHandResolver o) {
		return 0;
	}

}
