import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

/**
 * Import data from CSV file using OpenCSV
 * Creates List of Loan and Investor Classes (respectively) and populates using data from CSV file
 * 
 * Initialises HashMap mapping Loans to Eligible Investors (Once rules are applied)
 * 
 * @author Nikita Vorontsov
 *
 */
public class CsvImporter {

	List<Loan> loansList;
	List<Investor> investorList;
	HashMap<Loan, List<Investor>> investmentMap;
	
	public CsvImporter(String loanPath, String investorPath) {
		try {
		loansList = new CsvToBeanBuilder(new FileReader(loanPath)).withType(Loan.class).build().parse();
		investorList = new CsvToBeanBuilder(new FileReader(investorPath)).withType(Investor.class).build().parse();
		investmentMap = new HashMap<Loan, List<Investor>>();
		createMap();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void createMap() {
		for(Loan l : loansList) {
			investmentMap.put(l, investorList);
		}
	}
	
	public HashMap<Loan, List<Investor>> getInvestmentMap() {
		return investmentMap;
	}
	
	
}
