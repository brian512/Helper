package cn.edu.wit.withelper.activity.order;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderData {

	public ArrayList<OrderData> children;
	public ArrayList<String> selection;

	public String name;

	public OrderData() {
		children = new ArrayList<OrderData>();
		selection = new ArrayList<String>();
	}

	public OrderData(String name) {
		this();
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	// generate some random amount of child objects (1..10)
//	private void generateChildren() {
//		Random rand = new Random();
//		for (int i = 0; i < rand.nextInt(9) + 1; i++) {
//			OrderData cat = new OrderData("Child " + i);
//			this.children.add(cat);
//		}
//	}

	public static ArrayList<OrderData> getOrderDatas() {
		ArrayList<OrderData> orderDatas = new ArrayList<OrderData>();
		
		OrderData cat0 = new OrderData("今日推荐");
//		cat0.generateChildren();
		cat0.children.add(new OrderData("地三鲜"));
		cat0.children.add(new OrderData("土豆回锅肉"));
		cat0.children.add(new OrderData("三鲜汤"));
		orderDatas.add(cat0);
		
		OrderData cat1 = new OrderData("小炒-素菜");
//		cat1.generateChildren();
		cat1.children.add(new OrderData("清炒小白菜"));
		cat1.children.add(new OrderData("地三鲜"));
		cat1.children.add(new OrderData("油淋茄子"));
		cat1.children.add(new OrderData("手撕包菜"));
		cat1.children.add(new OrderData("耗油生菜"));
		orderDatas.add(cat1);
		
		OrderData cat2 = new OrderData("小炒-荤菜");
//		cat2.generateChildren();
		cat2.children.add(new OrderData("土豆回锅肉"));
		cat2.children.add(new OrderData("土豆回锅肉"));
		cat2.children.add(new OrderData("土豆回锅肉"));
		cat2.children.add(new OrderData("土豆回锅肉"));
		cat2.children.add(new OrderData("土豆回锅肉"));

		orderDatas.add(cat2);
		
		OrderData cat3 = new OrderData("汤类");
//		cat3.generateChildren();
		cat3.children.add(new OrderData("番茄鸡蛋汤"));
		cat3.children.add(new OrderData("皮蛋瘦肉汤"));
		cat3.children.add(new OrderData("三鲜汤"));
		cat3.children.add(new OrderData("排骨萝卜汤"));
		orderDatas.add(cat3);
		
		return orderDatas;
	}

	public static OrderData getOrderDataByName(String name) {
		ArrayList<OrderData> collection = OrderData.getOrderDatas();
		for (Iterator<OrderData> iterator = collection.iterator(); iterator.hasNext();) {
			OrderData cat = (OrderData) iterator.next();
			if (cat.name.equals(name)) {
				return cat;
			}

		}
		return null;
	}
}
