package FertilityClinicPOJOs;
import java.io.Serializable;
import java.util.Objects;
public class Stock implements Serializable {

	private static final long serialVersionUID = 4060546678599412288L;
	
	    private String productID;
	    private String productName;
	    private String category;
	    private int quantity;
	    
		public Stock(String productID, String productName, String category, int quantity) {
			this.productID = productID;
			this.productName = productName;
			this.category = category;
			this.quantity = quantity;
		}

		@Override
		public int hashCode() {
			return Objects.hash(category, productID, productName, quantity);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Stock other = (Stock) obj;
			return Objects.equals(category, other.category) && Objects.equals(productID, other.productID)
					&& Objects.equals(productName, other.productName) && quantity == other.quantity;
		}

		@Override
		public String toString() {
			return "Stock [productID=" + productID + ", productName=" + productName + ", category=" + category
					+ ", quantity=" + quantity + "]";
		}

		public String getProductID() {
			return productID;
		}

		public void setProductID(String productID) {
			this.productID = productID;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
		
	    
	    
	

	

	
	

}
