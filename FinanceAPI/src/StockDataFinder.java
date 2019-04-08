import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockDataFinder {
	ArrayList<Stock> stockList = new ArrayList<>();
	final String REGEX = "\"beta\":\\{\"raw\":\\d\\.\\d+,\"fmt\":\"\\d\\.\\d+\"\\}";
	private Pattern p = Pattern.compile(REGEX);
	private Matcher m;
	
	public StockDataFinder() {}
	
	public StockDataFinder(ArrayList<Stock> stocks) throws IOException {
		stockList = stocks;
		for (Stock stock : stockList) {
			fetchBeta(stock);
			System.out.println("Beta raw: " + stock.getBetaRaw());
			System.out.println("Beta formatted: " + stock.getBetaFmt() + "\n");
		}
	}
	
	//Constructor that takes in string of symbols
	public StockDataFinder(String symbols[]) throws IOException {
		for (String sym : symbols) {
			Stock stock = new Stock(sym);
			fetchBeta(stock);
			System.out.println("Beta raw: " + stock.getBetaRaw());
			System.out.println("Beta formatted: " + stock.getBetaFmt() + "\n");
			stockList.add(stock);
		}
	}
	
	//retrieves the beta value for the stock by searching through the HTML code of the quote page on yahoo.
	public void fetchBeta(Stock s) throws IOException {
		System.out.println("Searching Yahoo Finance online for " + s.getSymbol() + " beta value...");
		URLConnection urlConn = s.getURL().openConnection();
		InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buff = new BufferedReader(in);
		
		String line = buff.readLine();
		while(line != null) {
			m = p.matcher(line);
			if (m.find()) {
				//System.out.println(m.group()); //This prints out HTML code of beta value
				String betaLine = m.group();
				
				int betaRawIndexStart = betaLine.indexOf("raw") + 5;
				int betaRawIndexEnd = betaLine.indexOf("fmt") - 2;
				
				int betaFmtIndexStart = betaLine.indexOf("fmt") + 6;
				int betaFmtIndexEnd = betaLine.length() - 2;
				
				s.setBetaRaw(Double.parseDouble(betaLine.substring(betaRawIndexStart, betaRawIndexEnd)));
				s.setBetaFmt(Double.parseDouble(betaLine.substring(betaFmtIndexStart, betaFmtIndexEnd)));
				break;
			}
			line = buff.readLine();
		}
	}
	
	//Gets the average raw beta value of the stocks in stockList
	public double averageBetaRaw() {
		DecimalFormat df = new DecimalFormat("#.######"); 
		double totalBeta = 0;
		double n = 0;
		for (Stock s : stockList) {
			totalBeta += s.getBetaRaw();
			n++;
		}
		return Double.parseDouble(df.format(totalBeta/n)); //Reformat the double for appropriate length
	}
	
	//Gets the average formatted beta value of the stocks in stockList
	public double averageBetaFmt() {
		DecimalFormat df = new DecimalFormat("#.##"); 
		double totalBeta = 0;
		double n = 0;
		for (Stock s : stockList) {
			totalBeta += s.getBetaFmt();
			n++;
		}
		return Double.parseDouble(df.format(totalBeta/n));
	}
	
	public double medianBetaRaw() {
		int n = stockList.size();
		double median;
		double beta[] = new double[n];
		int i = 0;
		for (Stock stock : stockList) {
			beta[i++] = stock.getBetaRaw();
		}
		
		//Insertion sort
		for (int x = 1; x < n; ++x) { 
            double key = beta[x]; 
            int y = x - 1; 
            while (y >= 0 && beta[y] > key) { 
                beta[y + 1] = beta[y]; 
                y = y - 1; 
            } 
            beta[y + 1] = key; 
		}
		System.out.println("=============Ordered beta values for median calculation=============");
		for (double d : beta) {
			System.out.println(d);
		}
		if (n == 0) 
			median = beta[0];
		else if (n%2 == 0) 
			median = (beta[n/2] + beta[n/2 - 1])/2; 
		else
			median = beta[(n - 1) / 2];
		return median;
   } 
	
	public double medianBetaFmt() {
		int n = stockList.size();
		double median;
		double beta[] = new double[n];
		int i = 0;
		for (Stock stock : stockList) {
			beta[i++] = stock.getBetaRaw();
		}
		
		//Insertion sort
		for (int x = 1; x < n; ++x) { 
            double key = beta[x]; 
            int y = x - 1; 
            while (y >= 0 && beta[y] > key) { 
                beta[y + 1] = beta[y]; 
                y = y - 1; 
            } 
            beta[y + 1] = key; 
		}
		System.out.println("=============Ordered beta values for median calculation=============");
		for (double d : beta) {
			System.out.println(d);
		}
		if (n == 0) 
			median = beta[0];
		else if (n%2 == 0) 
			median = (beta[n/2] + beta[n/2 - 1])/2; 
		else
			median = beta[(n - 1) / 2];
		return median;
    } 
	
	//Following methods to find variance and standard deviation of beta value are for samples, not population.
	public double sampleVarianceBetaRaw() {
		double variance;
		double squaredTotalDifference = 0.0;
		double xbar = averageBetaRaw();
		for (Stock stock : stockList) {
			squaredTotalDifference += Math.pow(stock.getBetaRaw() - xbar, 2);
		}
		variance = squaredTotalDifference / (stockList.size() - 1);
		return variance;
	}
	
	public double sampleVarianceBetaFmt() {
		double variance;
		double squaredTotalDifference = 0.0;
		double xbar = averageBetaRaw();
		for (Stock stock : stockList) {
			squaredTotalDifference += Math.pow(stock.getBetaFmt() - xbar, 2);
		}
		variance = squaredTotalDifference / (stockList.size() - 1);
		return variance;
	}
	
	public double sampleStdBetaRaw() {
		double std = Math.sqrt(sampleVarianceBetaRaw());
		return std;
	}
	
	public double sampleStdBetaFmt() {
		double std = Math.sqrt(sampleVarianceBetaFmt());
		return std;
	}
	
	//Following methods to find variance and standard deviation of beta value are for the population, not for samples.
	public double populationVarianceBetaRaw() {
		double variance;
		double squaredTotalDifference = 0.0;
		double xbar = averageBetaRaw();
		for (Stock stock : stockList) {
			squaredTotalDifference += Math.pow(stock.getBetaRaw() - xbar, 2);
		}
		variance = squaredTotalDifference / (stockList.size());
		return variance;
	}
	
	public double populationVarianceBetaFmt() {
		double variance;
		double squaredTotalDifference = 0.0;
		double xbar = averageBetaRaw();
		for (Stock stock : stockList) {
			squaredTotalDifference += Math.pow(stock.getBetaFmt() - xbar, 2);
		}
		variance = squaredTotalDifference / (stockList.size());
		return variance;
	}
	
	public double populationStdBetaRaw() {
		double std = Math.sqrt(populationVarianceBetaRaw());
		return std;
	}
	
	public double populationStdBetaFmt() {
		double std = Math.sqrt(populationVarianceBetaFmt());
		return std;
	}
	
	public void randomize(int n, ArrayList<Stock> aList) {
		//@@@@@@@@@@@@@@@@@ this method will take n random samples of stock array list, aList, and updates the main stockList 
		//@@@@@@@@@@@@@@@@@ with n random samples of aList. Suppose aList is entire list of IT sector companies, if n is 30 then stockList is
		//@@@@@@@@@@@@@@@@@ updated to 30 random samples of the IT sector input list without repetition.
	}
	
	public static void main(String[] args) throws IOException {
		SP500 sp = new SP500();
		ArrayList<Stock> allITSectorList = sp.getAllInformationTechnologySector();//This array list contains all the stocks from IT sector companies
		
		System.out.println("=============Content of IT sector array list=============");
		for(Stock s : allITSectorList) {
			//Proof: prints symbol, name, sector from each stock in array list
			System.out.print(s.getSymbol() + ", "); System.out.print(s.getName() + ", "); System.out.println(s.getSector());
		}
		System.out.println();
		
		//You can construct a Stock Data Finder by entering an array of symbol String in the parameter, such as:
		System.out.println("=============Listing of current stockList array list for demonstration purposes=============");
		String symbol[] = {"AAPL", "GOOG", "TSLA"};
		StockDataFinder sdf1 = new StockDataFinder(symbol);
		System.out.println("=============Example of calculating average, median, variance and standard deviation for AAPL, GOOG, and TSLA=============");
		System.out.println("Average beta raw: " + sdf1.averageBetaRaw());
		System.out.println("Average beta formatted: " + sdf1.averageBetaFmt());
		System.out.println("Variance beta raw: " + sdf1.sampleVarianceBetaRaw());
		System.out.println("Variance beta formatted: " + sdf1.sampleVarianceBetaFmt());
		System.out.println("Standard deviation beta raw: " + sdf1.sampleStdBetaRaw());
		System.out.println("Standard deviation beta formatted: " + sdf1.sampleStdBetaFmt());
		System.out.println("Median beta raw: " + sdf1.medianBetaRaw());
		
		//You can also construct a Stock Data Finder by entering an array list of stocks in the parameter, such as:
		System.out.println("=============Listing of current stockList array list for demonstration purposes=============");
		StockDataFinder sdf2 = new StockDataFinder(allITSectorList);
		System.out.println("=============Example of calculating average, median, variance, and standard deviation for all IT sector=============");
		System.out.println("Average beta raw: " + sdf2.averageBetaRaw());
		System.out.println("Average beta formatted: " + sdf2.averageBetaFmt());
		System.out.println("Variance beta raw: " + sdf2.populationVarianceBetaRaw());
		System.out.println("Variance beta formatted: " + sdf2.populationVarianceBetaFmt());
		System.out.println("Standard deviation beta raw: " + sdf2.populationStdBetaRaw());
		System.out.println("Standard deviation beta formatted: " + sdf2.populationStdBetaFmt());
		System.out.println("Median beta raw: " + sdf2.medianBetaRaw());
	}
}
