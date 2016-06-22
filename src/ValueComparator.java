import java.util.Comparator;


public class ValueComparator implements Comparator<card> {
	
	public int compare(card o1, card o2) {
		// TODO Auto-generated method stub
		if (o1.getS() == o2.getS()) {
			return o1.getValue() - o2.getValue();
		}
		else {
			return o1.getS() - o2.getS();
		}
	}
}
