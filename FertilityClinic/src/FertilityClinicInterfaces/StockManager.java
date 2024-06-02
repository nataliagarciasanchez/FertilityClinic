package FertilityClinicInterfaces;

import java.util.Date;
import java.util.List;

import FertilityClinicPOJOs.Stock;

public interface StockManager {
	public List<Stock> viewStock(); 
    public List<Stock> sortByName (String name);
    public boolean isItemAvailable(int id);
    public void addStock(Stock stock);
    public void updateStock(Stock stock);
	public List<Stock> getListOfStockItems();
	public List<Stock> searchStockItemsByName(String name);
	public List<Stock> searchStockItemsByName(String name, boolean sortByExpiry) ;
	
}
