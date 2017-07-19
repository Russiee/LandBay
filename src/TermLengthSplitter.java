import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Class to sort list of Investors by Term Length
 * If the investors term length is greater than the requested term length
 * for the Loan, Investor is added to List of eligible investors
 * 
 * Can be altered depending on updated ruleset
 * 
 * @author Nikita Vorontsov
 *
 */
public class TermLengthSplitter {
	
	HashMap<Loan, List<Investor>> investmentMap;
	
	public TermLengthSplitter(HashMap<Loan, List<Investor>> investmentMap) {
		this.investmentMap = investmentMap;
		splitTermLength();
	}
	
	private void splitTermLength() {
		Set<Loan> keySet = investmentMap.keySet();
		for(Loan l : keySet) {
			List<Investor> updatedList = new ArrayList<Investor>();
			for(Investor i : investmentMap.get(l)) {
				if(i.getTerm() > l.getTerm()) {
					updatedList.add(i);
				}
			}
			investmentMap.put(l, updatedList);
		}
	}
	
	public HashMap<Loan, List<Investor>> getInvestmentMap() {
		return investmentMap;
	}

}
