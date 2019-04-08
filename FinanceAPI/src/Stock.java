import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Stock {
	private String symbol;
	private String name;
	private URL url;
	private String sector;
	private double betaRaw;
	private double betaFmt;
	
	public Stock(String stockName) throws MalformedURLException {
		symbol = stockName;
		StringBuilder sb = new StringBuilder("https://ca.finance.yahoo.com/quote/");
		sb.append(symbol + "/");
		url = new URL(sb.toString());
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public URL getURL() {
		return url;
	}
	
	public String getSector() {
		return sector;
	}
	
	public double getBetaRaw() {
		return betaRaw;
	}
	
	public double getBetaFmt() {
		return betaFmt;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public void setBetaRaw(double beta) {
		betaRaw = beta;
	}
	
	public void setBetaFmt(double beta) {
		betaFmt = beta;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) throws IOException {
	}
}
