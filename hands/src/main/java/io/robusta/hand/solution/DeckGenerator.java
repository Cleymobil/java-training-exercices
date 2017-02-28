package io.robusta.hand.solution;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.interfaces.IDeckGenerator;

public class DeckGenerator implements IDeckGenerator {

	@Override
	public Deck generate() {
		Deck deck = new Deck();

		// fill the deck with cards
		// Probably use the good modulo
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 4; j++) {
				Card card = new Card(i, CardColor.getByValue(j));

				deck.add(card);
			}

		}
		return deck;
	}

}
