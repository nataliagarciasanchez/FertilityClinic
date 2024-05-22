package FertilityClinicInterfaces;

import java.util.Date;
import java.util.List;

import FertilityClinicPOJOs.Stock;

public interface StockManager {
	public List<Stock> viewStock(int id); 
    public List<Stock> sortByName (String name);
    public boolean isItemAvailable(int id);
    public void addStock(int managerId, String productName, String category, int quantity, Date expiryDate);
	public void updateStock(Stock stock); 
	
}
