package com.example.billsplit;
import java.util.ArrayList;

/*OCR TEST 
 */
import org.apache.http.HttpHost;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.params.ClientPNames;

import org.apache.http.client.params.CookiePolicy;

import org.apache.http.conn.params.ConnRoutePNames;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;

import org.apache.http.params.HttpConnectionParams;

import org.apache.http.params.HttpParams;

import org.apache.http.util.EntityUtils;
/*
 */

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
