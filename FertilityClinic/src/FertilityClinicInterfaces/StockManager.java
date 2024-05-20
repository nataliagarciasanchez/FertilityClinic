package FertilityClinicInterfaces;

import java.util.List;

import FertilityClinicPOJOs.Stock;

public interface StockManager {
	public List<Stock> viewStock(); 
    public List<Stock> sortByName (String name);
    public boolean isItemAvailable(int id);
	public void addStock();
	public void modifyStock();
   
}
