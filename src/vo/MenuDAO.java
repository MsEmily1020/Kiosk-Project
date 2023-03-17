package vo;

import java.util.ArrayList;

public class MenuDAO {
	ArrayList<MenuVO> mvo = new ArrayList<>();
	
	public void insert(MenuVO mvo) {
		this.mvo.add(mvo);
	}
	
	public ArrayList<MenuVO> select() {
		return this.mvo;
	}
}
