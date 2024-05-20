package FertilityClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import FertilityClinicInterfaces.StockManager;
import FertilityClinicPOJOs.Stock;

public class JDBCStockManager  implements StockManager{
	private JDBCManager manager;

	public JDBCStockManager (JDBCManager manager) {
		this.manager=manager;
	}
	
	public List<Stock> sortByName(String name) {
        List<Stock> stockList = new ArrayList<>();


        try {

            String sql = "SELECT * FROM Stock WHERE name LIKE ? ORDER BY name";

            Statement stmt = manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {
                Integer product_id = rs.getInt("id");
                String productName = rs.getString("name");
                String category = rs.getString("category");
                Integer quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiryDate");

                Stock stock = new Stock(product_id, productName, category, quantity, expiryDate);
                stockList.add(stock);
            }

            rs.close();
            stmt.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return stockList;
    }
	
	public List<Stock> sortByDate(Date date) {
        List<Stock> stockList = new ArrayList<>();


        try {

            String sql = "SELECT * FROM Stock WHERE date LIKE ? ORDER BY date";

            Statement stmt = manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {
                Integer product_id = rs.getInt("id");
                String productName = rs.getString("name");
                String category = rs.getString("category");
                Integer quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiryDate");

                Stock stock = new Stock(product_id, productName, category, quantity, expiryDate);
                stockList.add(stock);
            }

            rs.close();
            stmt.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return stockList;
    }
	
	public List<Stock> sortByCategory(String category) {
        List<Stock> stockList = new ArrayList<>();


        try {

            String sql = "SELECT * FROM Stock WHERE category LIKE ? ORDER BY name";

            Statement stmt = manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {
                Integer product_id = rs.getInt("id");
                String productName = rs.getString("name");
                String categoryProduct = rs.getString("category");
                Integer quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiryDate");

                Stock stock = new Stock(product_id, productName, categoryProduct, quantity, expiryDate);
                stockList.add(stock);
            }

            rs.close();
            stmt.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return stockList;
    }
	
	
    public boolean isItemAvailable(int id) {
	        boolean available = false;

	        try {

		        String sql = "SELECT quantity FROM Stock WHERE id = ?";
				PreparedStatement p = manager.getConnection().prepareStatement(sql);
				
	            p.setInt(1, id);
	            ResultSet resultSet = p.executeQuery();

	            if (resultSet.next()) {
	                int quantity = resultSet.getInt("quantity");
	                available = quantity > 0;
	            }

	            resultSet.close();
	            p.close();


	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return available;
	    }

	@Override
	public List<Stock> viewStock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyStock() {
		// TODO Auto-generated method stub
		
	}
}
