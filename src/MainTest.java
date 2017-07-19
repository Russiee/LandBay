import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainTest {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Loan, List<Investor>> investmentMap;
		CsvImporter importer = new CsvImporter("..\\..\\Desktop\\loans.csv", "..\\..\\Desktop\\investmentRequests.csv");
		ProductTypeSplitter typeSplitter = new ProductTypeSplitter(importer.getInvestmentMap());
		TermLengthSplitter termSplitter = new TermLengthSplitter(typeSplitter.getInvestmentMap());
		InvestorFirstSorter investorFirst = new InvestorFirstSorter(termSplitter.getInvestmentMap());
		investmentMap = investorFirst.getInvestmentMap();
		Set<Loan> keySet = investmentMap.keySet();
		List<Loan> loanList = keySet.stream().sorted().collect(Collectors.toList());
		for(Loan l : loanList) {
			System.out.println("LoanID: " + l.getLoanId());
			if(l.getInvestorList().size() > 0) {
				for(Investor investor : l.getInvestorList().keySet()) {
					System.out.println("Investor Name: " + investor.getName() + " Investment Amount: " + l.getInvestorList().get(investor) + " Loan Left: " + l.getLoanAmount() + " Investor has money left: " + investor.getInvestmentAmount());
				}
			}
		}
	}

}
