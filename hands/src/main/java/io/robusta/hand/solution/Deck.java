package io.robusta.hand.solution;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.interfaces.IDeck;

public class Deck extends LinkedList<Card> implements IDeck {

	private static final long serialVersionUID = -4686285366508321800L;

	public Deck() {

	}

	@Override
	public Card pick() {
		// shuffle;
		// remove card from deck and returns it

		Collections.shuffle(this);
		Card card = this.getFirst();
		this.remove(this.getFirst());

		return card;
	}

	@Override
	public TreeSet<Card> pick(int number) {
		TreeSet<Card> cards = new TreeSet<Card>();
		for (int i = 0; i < number; i++) {
			cards.add(this.pick());
		}
		// reuse pick()
		return cards;
	}

	@Override
	public Hand giveHand() {
		Hand hand = new Hand();
		for (int i = 0; i < 5; i++) {
			hand.add(this.pick());
		}

		// A hand is a **5** card TreeSet
		return hand;
	}

}
