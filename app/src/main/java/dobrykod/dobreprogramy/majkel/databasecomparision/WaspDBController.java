package dobrykod.dobreprogramy.majkel.databasecomparision;

import android.content.Context;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspFactory;
import net.rehacktive.waspdb.WaspHash;

import java.io.File;
import java.util.List;

public class WaspDBController{
  String T;
  WaspDb db;
  WaspHash table;

  WaspDBController(Context c)
  {
    String path = String.valueOf(c.getFilesDir());
    String databaseName = "myDbWasp";
    String password = "passw0rd";
    this.db = WaspFactory.openOrCreateDatabase(path,databaseName,password);
    this.table = db.openOrCreateHash("table");
  }

  public void  fillingDB(List<SupplierDataModel> s){
    {SupplierDataModel temp;
      for(int i=0;i<s.size();i++){
        temp =  s.get(i);
        this.table.put(String.valueOf(i),temp);
      }
    }
  }


  public float folderSize (String directory)
  {
    File curDir = new File(directory);
    long length = 0;
    for(File f : curDir.listFiles())
    {
      if(f.isDirectory())
      {
        for ( File child : f.listFiles())
        {
          length = length + child.length();
        }

        System.out.println("Directory: " + f.getName() + " " + length + "kb");
      }
      else
      {
        length = f.length();
        System.out.println("File: " + f.getName() + " " + length + "kb");
      }
      length = 0;
    }
    return length;
  }

  public float size(Context c){

    return folderSize(String.valueOf(c.getFilesDir()));
  }


  public void addItem(int key, SupplierDataModel item){
    this.table.put(key, item);
  }

  public void deleteItem(String key){
    this.table.remove(key);
  }

  public void updateItem(String key,SupplierDataModel item){
    deleteItem(key);
    addItem(Integer.valueOf(key),item);
  }

  public DataModel readItem(String key){

    return this.table.get(key);
  }
  public List<SupplierDataModel> readAll(){
    List<SupplierDataModel> list= this.table.getAllValues();
    return list;
  }
}


