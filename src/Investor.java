import java.util.HashMap;

import com.opencsv.bean.CsvBindByName;

public class Investor{

	@CsvBindByName
	private String investor;
	
	@CsvBindByName
	private long investmentAmount;
	
	@CsvBindByName
	private String productType;
	
	@CsvBindByName
	private int term;
	
	private HashMap<Loan, Long> investmentsMap;
	
	public Investor() {
		investmentsMap = new HashMap<>();
	}
	
	public String getName() {
		return investor;
	}
	
	public long getInvestmentAmount() {
		return investmentAmount;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public int getTerm() {
		return term;
	}
	
	public void setName(String investor) {
		this.investor = investor;
	}
	
	public void setInvestmentAmount(long investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public void setTerm(int term) {
		this.term = term;
	}
	
	public HashMap<Loan, Long> getInvestmentsMap() {
		return investmentsMap;
	}
	
	public void setInvestments(Loan l, Long sum) {
		investmentsMap.put(l, sum);
	}
}
