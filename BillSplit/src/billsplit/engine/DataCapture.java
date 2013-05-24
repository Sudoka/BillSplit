package billsplit.engine;
import java.util.ArrayList;

public class DataCapture {
  private ArrayList<Item> itemList;
  public void DataCapture(){
    itemList = new ArrayList<Item>();
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
