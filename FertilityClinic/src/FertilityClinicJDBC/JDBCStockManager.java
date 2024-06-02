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
    public List<Stock> viewStock() {
        List<Stock> stockItems = new ArrayList<>();
        String sql = "SELECT * FROM stock";  

        try (
             PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String productName = rs.getString("productName");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiryDate");

                Stock item = new Stock(productID, productName, category, quantity, expiryDate);
                stockItems.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all stock items: " + e.getMessage());
            e.printStackTrace();
        }
        return stockItems;
    }
	

	
	@Override
	public void addStock(int managerId, String productName, String category, int quantity, Date expiryDate) {
	    try {
	        String sql = "INSERT INTO stocks (manager_id, product_name, category, quantity, expiry_date) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setInt(1, managerId);
	        stmt.setString(2, productName);
	        stmt.setString(3, category);
	        stmt.setInt(4, quantity);
	        stmt.setDate(5, new java.sql.Date(expiryDate.getTime()));

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Stock added successfully.");
	        } else {
	            System.out.println("Failed to add stock.");
	        }

	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	

	@Override
	public void updateStock(Stock stock) {
		java.sql.Date sqlExpiryDate = new java.sql.Date(stock.getExpiryDate().getTime());
	    try {
	        String sql = "UPDATE stocks SET productName=?, category=?, quantity=?, expiryDate=? WHERE productID=?";
	        PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
	        stmt.setString(1, stock.getProductName());
	        stmt.setString(2, stock.getCategory());
	        stmt.setInt(3, stock.getQuantity());
	        stmt.setDate(4, sqlExpiryDate);  // Ensure expiryDate is of type java.sql.Date
	        stmt.setInt(5, stock.getProductID());

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Stock information updated successfully.");
	        } else {
	            System.out.println("Failed to update stock information. Stock with productID " + stock.getProductName() + " not found.");
	        }

	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}
	
	public List<Stock> getListOfStockItems() {
        List<Stock> stockItems = new ArrayList<>();
        String sql = "SELECT * FROM stock ORDER BY productName";  

        try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int productID = rs.getInt("id");
                String productName = rs.getString("productName");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                Date expiryDate = rs.getDate("expiryDate");

                Stock item = new Stock(productID, productName, category, quantity, expiryDate);
                stockItems.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving stock items: " + e.getMessage());
            e.printStackTrace();
        }
        return stockItems;
    }
	
	public List<Stock> searchStockItemsByName(String name) {
        List<Stock> stockItems = new ArrayList<>();
        String sql = "SELECT * FROM stock WHERE productName LIKE ? ORDER BY productName";

        try (
             PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");  
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    Date expiryDate = rs.getDate("expiryDate");

                    Stock item = new Stock(productID, productName, category, quantity, expiryDate);
                    stockItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching stock items by name: " + e.getMessage());
            e.printStackTrace();
        }
        return stockItems;
    }
	
	public List<Stock> searchStockItemsByName(String name, boolean sortByExpiry) {
        List<Stock> stockItems = new ArrayList<>();
        String orderByClause = sortByExpiry ? "ORDER BY expiryDate, productName" : "ORDER BY productName";

        String sql = "SELECT * FROM stock WHERE productName LIKE ? " + orderByClause;

        try (
             PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    Date expiryDate = rs.getDate("expiryDate");

                    Stock item = new Stock(productID, productName, category, quantity, expiryDate);
                    stockItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching stock items by name: " + e.getMessage());
            e.printStackTrace();
        }
        return stockItems;
    }
}

