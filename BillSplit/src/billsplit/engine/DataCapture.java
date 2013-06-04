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
    StringTokenizer st = new StringTokenizer(itemListString, " = "); 
	while(st.hasMoreTokens()) { 
	  String name = st.nextToken(); 
	  String costString = st.nextToken();
	  costString = costString.replace("$", "");//replace the $ sign
	  Item newItem = new Item(name,Double.parseDouble(costString));
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
