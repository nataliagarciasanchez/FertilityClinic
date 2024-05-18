package FertilityClinicInterfaces;

import java.util.List;

import FertilityClinicPOJOs.Stock;

public interface StockManager {
    List<Stock> getAllStock();
    List<Stock> searchStockByName(String name);
    boolean isItemAvailable(int id);
    void registerStockEntry(int id, int quantity);
    void registerStockOut(int id, int quantity);
    //List<Item> getExpiredItems();
    //List<StockMovement> getStockMovementHistory(int id);
}
