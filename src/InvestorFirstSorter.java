import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that funds loans from investors with the most amount of funds to invest first
 * For Example: "A" with £10,000 to invest will be sorted before "B" with £1,000 to invest
 * 
 * @author Nikita Vorontsov
 *
 */
public class InvestorFirstSorter {

	//HashMap that contains Loans as keys, with a List of Investors matching specified Rules as Values
	HashMap<Loan, List<Investor>> investmentMap;
	
	//HashMap containing temporary investors - Investors who are attempting to fund a loan
	//But not sure whether the sum of investors meets the requested loan
	HashMap<Investor, Long> temporaryInvestors;
	
	public InvestorFirstSorter(HashMap<Loan, List<Investor>> investmentMap) {
		this.investmentMap = investmentMap;
		sortInvestors();
	}
	
	private void sortInvestors() {
		
		//Sort the list of Loans by Completed Date (Oldest -> Newest)
		List<Loan> loanList = investmentMap.keySet().stream().sorted().collect(Collectors.toList());
		
		for(Loan l : loanList) {
			List<Investor> investorList = investmentMap.get(l); //List of Investors that are eligible to invest in Loan l after rules have been applied
			
			//Sorts Investors by Most Amount of Funds available to invest first
			Comparator<Investor> comparator = Comparator.comparingLong(Investor::getInvestmentAmount).reversed();
			investorList = investorList.stream().sorted(comparator).collect(Collectors.toList());
			
			temporaryInvestors = new HashMap<>();
			long originalLoan = l.getLoanAmount();
			
			for(Investor i : investorList) {
				
				if(i.getInvestmentAmount() >= l.getLoanAmount()) {		//If the invester has more (or equal) number of funds than the loan is requesting
					i.setInvestmentAmount(i.getInvestmentAmount() - l.getLoanAmount()); 	//Change the remaining amount of funds available to invest
					l.addInvestor(i, l.getLoanAmount()); 	//Add the investor to the given Loan, as well as the amount invested
					i.setInvestments(l, l.getLoanAmount());	 //Add the loan to the given investor
					l.setLoanAmount(0); 	//Set the outstanding loan amount to 0
					l.setLoanGiven(true); 	//Set loanGiven boolean to true - Loan has been successfully been matched
					break; //Continue to next loan
				} else {
					temporaryInvestors.put(i, i.getInvestmentAmount());		//Place the current investor and amount invested into temporary storage
					i.setInvestments(l, i.getInvestmentAmount());
					l.addInvestor(i, i.getInvestmentAmount());
					l.setLoanAmount(l.getLoanAmount() - i.getInvestmentAmount());
					long remainingInvestment = l.getLoanAmount() - i.getInvestmentAmount();
					
					if(remainingInvestment > 0) {	//If the investor has funds left after investing
						i.setInvestmentAmount(remainingInvestment);
					} else {
						i.setInvestmentAmount(0);
					}
				
				}
			}
			//If after all eligible investors have been accounted for, and the loan hasn't been matched
			//Remove the temporary investors
			if(!l.loanGiven() && l.getInvestorList().size() > 0) {
				removeUnfundedInvestors(l, investorList);
				l.setLoanAmount(originalLoan);
			}
			
		}
	}
	
	public HashMap<Loan, List<Investor>> getInvestmentMap() {
		return investmentMap;
	}
	
	/*
	 * Remove investors from a given loan if the loan has not been met
	 * Return original investment amounts to investors
	 */
	private void removeUnfundedInvestors(Loan l, List<Investor> investorList) {
		List<Investor> investorsToRemoveList = new ArrayList<Investor>();
		
		for(Investor i : l.getInvestorList().keySet()) {
			for(Investor investor : investorList) {
				
				if(i.getName().equals(investor.getName())) {
					i.setInvestmentAmount(temporaryInvestors.get(i));
					investorsToRemoveList.add(i);
				}
			}
		}
		
		if(investorsToRemoveList.size() > 0) {
			for(Investor i : investorsToRemoveList) {
				l.getInvestorList().remove(i);
				i.getInvestmentsMap().remove(l);
			}
		}
	}
}
