import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//SP500 class contains an array list containing every single S&P500 companies
public class SP500 {
	ArrayList<Stock> sp500List = new ArrayList<>();
	
	//Constructor initializes each stock object by reading reach line of the sp500.txt file
	public SP500() throws IOException {
		FileReader fr = new FileReader("C:\\Users\\xxx\\Desktop\\sp500.txt"); //@@@@@@Replace xxx by the name of the current user of your computer
		BufferedReader br = new BufferedReader(fr);
		String line;
		
		while ((line = br.readLine()) != null) {
			int indexFirstTab = line.indexOf((char) 9);
			String symbol = line.substring(0, indexFirstTab); //extracts the symbol from file line
			Stock s = new Stock(symbol);		
			
			int indexSecondTab = line.indexOf((char) 9, indexFirstTab + 1);
			String name = line.substring(indexFirstTab + 1, indexSecondTab); //extracts the name from file line
			s.setName(name);
			
			String sector = line.substring(indexSecondTab + 1, line.length()); //extracts the sector from file line
			s.setSector(sector);
			
			sp500List.add(s);
		}
	}
	
	//Returns an array list of stocks only from consumer discretionary sector
	public ArrayList<Stock> getAllConsumerDiscretionarySector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Consumer Discretionary")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from consumer staples sector
	public ArrayList<Stock> getAllConsumerStaplesSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Consumer Staples")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from energy sector
	public ArrayList<Stock> getAllEnergySector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Energy")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from financial sector
	public ArrayList<Stock> getAllFinancialsSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Financials")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from health care sector
	public ArrayList<Stock> getAllHealthCareSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Health Care")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from industrial sector
	public ArrayList<Stock> getAllIndustrialsSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Industrials")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from information technlogy sector
	public ArrayList<Stock> getAllInformationTechnologySector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Information Technology")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from materials sector
	public ArrayList<Stock> getAllMaterialsSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Materials")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from real estate sector
	public ArrayList<Stock> getAllRealEstateSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Real Estate")) {
				list.add(s);
			}
		}
		return list;
	}
	
	//Returns an array list of stocks only from utilities sector
	public ArrayList<Stock> getAllUtilitiesSector() {
		ArrayList<Stock> list = new ArrayList<>();
		for (Stock s : sp500List) {
			if (s.getSector().equals("Utilities")) {
				list.add(s);
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		
	}
}
