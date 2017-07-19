import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
