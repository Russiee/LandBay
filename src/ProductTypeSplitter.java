import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProductTypeSplitter {

	HashMap<Loan, List<Investor>> investmentMap;
	
	public ProductTypeSplitter(HashMap<Loan, List<Investor>> investmentMap) {
		this.investmentMap = investmentMap;
		splitProductType();
	}
	
	private void splitProductType() {
		Set<Loan> keySet = investmentMap.keySet();
		for(Loan l : keySet) {
			List<Investor> updatedList = new ArrayList<Investor>();
			for(Investor i : investmentMap.get(l)) {
				if(l.getProductType().equals(i.getProductType())) {
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
