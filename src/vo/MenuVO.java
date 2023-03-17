package vo;

public class MenuVO {
	String id;
	String name;
	int price;
	int cnt;
	
	public MenuVO(String id, String name, int price, int cnt) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.cnt = cnt;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public int getCnt() {
		return cnt;
	}
	
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
