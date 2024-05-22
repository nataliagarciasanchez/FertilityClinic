package FertilityClinicJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public List<Stock> viewStock(int id) {
		ArrayList<Stock> stocks = new ArrayList<>();
        try {
            String sql = "SELECT p.product_id, p.product_name, p.category, p.quantity, p.expiry_date " +
                         "FROM products p " +
                         "JOIN managers_products mp ON p.product_id = mp.product_id " +
                         "WHERE mp.manager_id = ?";
            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer productID = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiry_date");

                Stock stock = new Stock(productID, productName, category, quantity, expiryDate);
                stocks.add(stock);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
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
