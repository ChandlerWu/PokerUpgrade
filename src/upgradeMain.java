import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class upgradeMain {
	
	private static int numOfBoublePairs = 0;
	private static int numOfTriplets = 0;

	public static void main(String[] args) {
		
		int numOfGmaes = 100000;
		
		for (int i = 0; i < numOfGmaes; i++) {
			mockGame();
		}
		
		System.out.println("Tractor: " + numOfBoublePairs + " Three of a kind: " + numOfTriplets + " out of " + numOfGmaes + " games");
	}
	
	/*
	 * Here we assume that player 1 gets to be the host and get 6 more cards than others.
	 * And player 1 will never give up on any triplets and double pairs.
	 * Ace is "zhu" here. 3 Aces with the same suit can be triplets
	 * 2 High Aces and 2 any normal ace in the same suit can form a tractor.
	 * Here we assume this is no suit is "zhu" in this game.
	 */
	private static void mockGame() {
		
		List<card> cardDeck = new ArrayList<card>();
		
		// Prepare the deck
		int numOfDecks = 3;
		for (int t = 0; t < numOfDecks; t++) { // three decks of cards
			for (int i = 2; i < 14; i++) { // from 2 to J, Q, K, no Ace
		    	for (int j = 1; j <= 4; j ++) { // four suits
		    		card c = new card(i, j);
			    	cardDeck.add(c);
		    	}
		    	
		    }
		    card sJoker = new card(14, 5); // Small joker
		    card lJoker = new card(15, 5); // Big joker
		    cardDeck.add(sJoker);
		    cardDeck.add(lJoker);
		}
	    
		// Add 1(ace) as high card (Zhu) to the deck
		for (int t = 0; t < numOfDecks; t++) {
			card h1 = new card(1, 6); // normal Ace
			card h2 = new card(1, 7); // normal Ace
			card h3 = new card(1, 8); // normal Ace
			card h4 = new card(2, 9); // Higher Ace
		    cardDeck.add(h1);
		    cardDeck.add(h2);
		    cardDeck.add(h3);
		    cardDeck.add(h4);
		}
		
		// Shuffle
	    Collections.shuffle(cardDeck);
	    
	    //System.out.println("deck " + cardDeck.size());
	    
	    // 6 players
	    List<card> p1 = new ArrayList<card>();
	    List<card> p2 = new ArrayList<card>();
	    List<card> p3 = new ArrayList<card>();
	    List<card> p4 = new ArrayList<card>();
	    List<card> p5 = new ArrayList<card>();
	    List<card> p6 = new ArrayList<card>();
	    
	    
	    // Banker's cards
	    // P1 gets more cards because he is a banker and assume he never gives up double pairs and triplets
	    for (int i = 0; i < 6; i++) {
	    	p1.add(cardDeck.remove(i));
	    }
	    
	    // Distribute
	    for (int i = 0; i < cardDeck.size(); i++) {
	    	card c = cardDeck.get(i);
	    	switch (i % 6) {
				case 0:
					p1.add(c);
					break;
				case 1:
					p2.add(c);
					break;
				case 2:
					p3.add(c);
					break;
				case 3:
					p4.add(c);
					break;
				case 4:
					p5.add(c);
					break;
				case 5:
					p6.add(c);
					break;
				default:
					break;
			}
	    	//System.out.println(c.getValue() + " " + c.getS());
	    }
	    
//	    System.out.println("p1 " + p1.size());
//	    System.out.println("p2 " + p2.size());
//	    System.out.println("p3 " + p3.size());
//	    System.out.println("p4 " + p4.size());
	    
	    // First sort the deck for each player
	    Collections.sort(p1, new ValueComparator());
	    Collections.sort(p2, new ValueComparator());
	    Collections.sort(p3, new ValueComparator());
	    Collections.sort(p4, new ValueComparator());
	    Collections.sort(p5, new ValueComparator());
	    Collections.sort(p6, new ValueComparator());
	    
//	    PrintDeck(p1 , "p1");
//	    PrintDeck(p2 , "p2");
//	    PrintDeck(p3 , "p3");
//	    PrintDeck(p4 , "p4");
//	    PrintDeck(p5 , "p5");
//	    PrintDeck(p6 , "p6");
	    
	    // Then find pairs
	    numOfBoublePairs  += findNumOfDoublePairs(p1);
	    numOfBoublePairs += findNumOfDoublePairs(p2);
	    numOfBoublePairs += findNumOfDoublePairs(p3);
	    numOfBoublePairs += findNumOfDoublePairs(p4);
	    numOfBoublePairs += findNumOfDoublePairs(p5);
	    numOfBoublePairs += findNumOfDoublePairs(p6);
	    
	    // Find Triplets
	    numOfTriplets += findNumOfTriplets(p1);
	    numOfTriplets += findNumOfTriplets(p2);
	    numOfTriplets += findNumOfTriplets(p3);
	    numOfTriplets += findNumOfTriplets(p4);
	    numOfTriplets += findNumOfTriplets(p5);
	    numOfTriplets += findNumOfTriplets(p6);

	}

	private static int findNumOfTriplets(List<card> p1) {	
		int result = 0;
		for (int i = 0; i < p1.size() - 2; i++) {
			card c1 = p1.get(i);
			card c3 = p1.get(i+2);
			if (c1.getValue() == c3.getValue()) { // When values are the same
				if (c1.getS() == c3.getS()) { // When suits are the same
					result++;
					i += 2;
					//System.out.print(c1.getValue() + " " + c1.getS());
				}
			}
		}
		return result;
	}

	private static void PrintDeck(List<card> p1, String s) {
		// TODO Auto-generated method stub
		for (int i = 0; i < p1.size(); i++) {
	    	System.out.print(s + ": " + p1.get(i).getValue() + " " + p1.get(i).getS() + ";   ");
	    }
		System.out.println("");
	}

	private static int findNumOfDoublePairs(List<card> p1) {
		int result = 0;
		// TODO Auto-generated method stub
		for (int i = 0; i < p1.size() - 3; i++) {
	    	card c1 = p1.get(i);
	    	card c2 = p1.get(i+1);
	    	if (c1.getValue() == c2.getValue()) { // Fist pair
	    		card c3 = p1.get(i+2);
		    	card c4 = p1.get(i+3);
		    	if (c1.getS() == c4.getS()) { // Two pairs are in the same suit
		    		if (c3.getValue() == c4.getValue() && c3.getValue() - c2.getValue() == 1) { // Two pairs are 1 rank away
		    			result++;
		    			//System.out.print("dd: " + c1.getValue() + " " + c1.getS());
		    			i = i + 2;
			    	}
		    	}
		    	else if (c1.getValue() == 1 && c1.getS() == c2.getS() // Two pairs are normal ace and high ace
		    			&& c4.getS() == 9 && c3.getS() == c4.getS()) {
		    		result++;
		    		i = i + 2;
		    		//System.out.print("zhu: " + c1.getValue() + " " + c1.getS() + " ");
		    		//PrintDeck(p1, "zhu");
		    	}	
	    	}
	    }
		return result;
	}

	
	
}



