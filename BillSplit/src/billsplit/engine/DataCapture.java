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
	  
	//bail out if itemListString is null or empty
	if(itemListString == null)
	  return;
	
	if(itemListString.equals(""))
	  return;
	
	//Assumptions about format:
	//ItemOnPaper $XX.00
	
	//check if there is at least one item
	if(!itemListString.contains(".00"))
	  return;
	
    StringTokenizer st = new StringTokenizer(itemListString, ".00"); 
    
	while(st != null && st.hasMoreTokens()){ 
	
	  String item = st.nextToken(); 
	  String name = "item";//initialize to a default
	  String cost = "0";

	  StringTokenizer st2 = new StringTokenizer(item, "$"); 
	  while(st2 != null && st2.hasMoreTokens()){ 
		name = st2.nextToken();
	    cost = st2.nextToken();
	  }
	  
	  try{
	    double parsedCost = Double.parseDouble(cost);
	    Item newItem = new Item(name, parsedCost);
	    itemList.add(newItem);
	  }catch(NumberFormatException nfe){
	    //do not add mal-formatted item
	  }
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
