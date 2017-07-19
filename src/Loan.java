import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

/**
 * Loan class storing a LoanId, LoanAmount, ProductType, Term and Completed Date.
 * As well as whether a loan has been given and which investors have funded the loan
 * Binds CSV Columns to Variable Names using OpenCSV
 * 
 * Compares Loans by Date (Oldest to Newest)
 * 
 * @author Nikita Vorontsov
 *
 */
public class Loan implements Comparable<Loan>{

	@CsvBindByName
	private int loanId;
	
	@CsvBindByName
	private long loanAmount;
	
	@CsvBindByName
	private String product;
	
	@CsvBindByName
	private int term;
	
	@CsvBindByName
	@CsvDate("dd/MM/yyyy")
	private Date completedDate;
	
	private boolean loanGiven;
	private HashMap<Investor, Long> investors;
	
	public Loan() {
		investors = new HashMap<>();
	}
	
	public int getLoanId() {
		return loanId;
	}
	
	public long getLoanAmount() {
		return loanAmount;
	}
	
	public String getProductType() {
		return product;
	}
	
	public int getTerm() {
		return term;
	}
	
	public Date getCompletedDate() {
		return completedDate;
	}
	
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	
	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public void setProductType(String product) {
		this.product = product;
	}

	@Override
	public int compareTo(Loan l) {
		return getCompletedDate().compareTo(l.getCompletedDate());
	}
	
	public boolean loanGiven() {
		return loanGiven;
	}
	
	public void setLoanGiven(boolean given) {
		loanGiven = given;
	}
	
	public HashMap<Investor, Long> getInvestorList() {
		return investors;
	}
	
	public void addInvestor(Investor i, long amount) {
		investors.put(i, amount);
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		if(loanGiven) {
			 try {
				json.put("loanId", getLoanId());
				JSONArray investorsArray = new JSONArray();
				for(Investor i : getInvestorList().keySet()) {
					JSONObject investorObject = new JSONObject();
					investorObject.put("investorName", i.getName());
					investorObject.put("investmentAmount", getInvestorList().get(i));
					investorsArray.put(investorObject);
				}
				json.put("investors", investorsArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}
