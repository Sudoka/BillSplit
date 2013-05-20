package com.example.billsplit;
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
