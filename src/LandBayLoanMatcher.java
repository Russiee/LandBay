import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class LandBayLoanMatcher {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Loan, List<Investor>> investmentMap;
		
		System.out.println(":::::LandBay - Loan-Investor Matcher:::::");
		boolean answerGiven = false;
		String answer = "";
		String loanPath = "";
		String investmentPath = "";
		Scanner in = new Scanner(System.in);
		while(!answerGiven) {
			System.out.println("Enter 'z' to specify Loan and Investment CSV file paths, or 'x' to continue with default file paths");
			answer = in.nextLine();
			if(answer.equals("z")) {
				System.out.println("Please specify the filepath to the Loans CSV file");
				loanPath = in.nextLine();
				System.out.println("Please specify the filepath to the Investments CSV file");
				investmentPath = in.nextLine();
				answerGiven = true;
			} else if(answer.equals("x")) {
				loanPath = "loans.csv";
				investmentPath = "investmentRequests.csv";
				answerGiven = true;
			}
		}
		int sortingOrder = 0;
		while(answerGiven) {
			System.out.println("Enter '0' to match Loans to Investors by Wealthiest Investor First, or Enter '1' to match Loans by Most Investors to a Loan");
			answer = in.nextLine();
			if(answer.equals("0")) {
				sortingOrder = InvestorMatcher.FIT_RICHEST_INVESTOR_FIRST;
				answerGiven = false;
			} else if(answer.equals("1")) {
				sortingOrder = InvestorMatcher.FIT_MOST_INVESTORS_FIRST;
				answerGiven = false;
			}
		}
		//First String Path = Path to Loans CSV
		//Second String Path = Path to Investors CSV
		CsvImporter importer = new CsvImporter(loanPath, investmentPath);
		ProductTypeSplitter typeSplitter = new ProductTypeSplitter(importer.getInvestmentMap());
		TermLengthSplitter termSplitter = new TermLengthSplitter(typeSplitter.getInvestmentMap());
		InvestorMatcher investorFirst = new InvestorMatcher(termSplitter.getInvestmentMap(), sortingOrder);
		List<Loan> loanList = investorFirst.getInvestmentMap().keySet().stream().sorted().collect(Collectors.toList());
		
		//Prints Loans that have been matched out in a JSON format
		//Used the Gson library for "PrettyPrinting" - Better readability
		for(Loan l : loanList) {
			if(l.loanGiven() && l.getInvestorList().size() > 0) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				System.out.println(gson.toJson(new JsonParser().parse(l.getJSON().toString())));
			}
		}
	}

}
