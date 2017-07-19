import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Class to sort investors and loans via Product Types
 * If Investor product type matches Loan product type
 * Then Investor added to List of investors for specified Loan
 * 
 * Can be altered depending on updated ruleset
 * 
 * @author Nikita Vorontsov
 *
 */
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
