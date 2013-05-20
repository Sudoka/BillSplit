package com.example.billsplit;
import java.util.ArrayList;


public class DataCapture {
  public class Item{
    private float cost;
    private String name;
    
    public float getCost(){
      return cost;
    }
    
    public String getName(){
      return name;
    }
  }
  
  private ArrayList<Item> itemList;
  public void DataCapture(){
    itemList = new ArrayList<Item>();
  }
  
  public ArrayList<Item> getItemList(){
    return itemList;
  }
  public void setItemList(ArrayList<Item> newItemList){
    return;
  }
  public boolean addItem(Item item, boolean update){
    return false;
  }
  
  public boolean removeItem(int itemId){
    return false;
  }
  public boolean editItem(int itemId){
    return false;
  }
}
