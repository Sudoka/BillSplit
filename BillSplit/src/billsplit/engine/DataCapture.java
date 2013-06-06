package billsplit.engine;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataCapture {
  private ArrayList<Item> itemList;
  public DataCapture(){
    itemList = new ArrayList<Item>();
  }
  
  public DataCapture(String itemListString){
    itemList = new ArrayList<Item>();
    createItemList(itemListString);
  }
  
  //parse itemListString
  private void createItemList(String itemListString){
    StringTokenizer st = new StringTokenizer(itemListString, ".00"); 
	while(st.hasMoreTokens()) { 
	  String item = st.nextToken(); 
	  String name = "item";
	  String cost = "0";
	  StringTokenizer st2 = new StringTokenizer(item, "$"); 
		while(st2.hasMoreTokens()) { 
			name = st2.nextToken();
			cost = st2.nextToken();
		}
	  Item newItem = new Item(name,Double.parseDouble(cost));
	  itemList.add(newItem);
	}
  }
  
  public ArrayList<Item> getItemList(){
    return itemList;
  }
  public void setItemList(ArrayList<Item> newItemList){
	itemList = newItemList;
    return;
  }
  public void addItem(Item item){
	itemList.add(item);
    return;
  }
}
