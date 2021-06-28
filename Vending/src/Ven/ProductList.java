package Ven;

public class ProductList {

	private int itemCode;
	private String name;
	private int price;
	private int itemQty;
	
	public ProductList(String name, int price, int itemQty) {
		super();
		this.name = name;
		this.price = price;
		this.itemQty = itemQty;
	}

	public ProductList(int itemCode, String name, int price, int itemQty) {
		this.itemCode = itemCode;
		this.name = name;
		this.price = price;
		this.itemQty = itemQty;
	}

	public ProductList(int itemCode, int itemQty) {
		this.itemCode = itemCode;
		this.itemQty = itemQty;
	}

	public ProductList(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public ProductList(int itemCode) {
		this.itemCode = itemCode;
	}

	public int getItemcode() {
		return itemCode;
	}

	public void setItemcode(int itemcode) {
		this.itemCode = itemcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	@Override
	public String toString() {
		return "ProductList [itemCode=" + itemCode + ", name=" + name + ", price=" + price + ", itemQty=" + itemQty
				+ "]";
	}

}