package FertilityClinicInterfaces;

import java.util.List;

import FertilityClinicPOJOs.Stock;

public interface StockManager {
	public List<Stock> viewStock(); 
    List<Stock> sortByName (String name);
    boolean isItemAvailable(int id);
	
	public void addStock();
	public void modifyStock();
   
}
