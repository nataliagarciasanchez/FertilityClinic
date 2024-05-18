package FertilityClinicInterfaces;

import java.util.List;

import FertilityClinicPOJOs.Stock;

public interface StockManager {
    List<Stock> sortByName (String name);
    boolean isItemAvailable(int id);
   
}
