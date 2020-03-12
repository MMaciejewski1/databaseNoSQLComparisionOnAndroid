package dobrykod.dobreprogramy.majkel.databasecomparision;

import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.Storage;

import java.io.File;
import java.util.List;


public class HawkDBController {
    String T;
    HawkDBController(Context c){
        Hawk.init(c).build();


    }
    public void  fillingDB(List<SupplierDataModel> s){
        SupplierDataModel temp;
        for(int i=0;i<s.size();i++){
            temp =  s.get(i);
            Hawk.put(String.valueOf(i), temp);
        }
    }

    public float size(Context c){
        String path = c.getFilesDir() + "/testH.db";
        
        File file = new File(path);
        float file_size = Integer.parseInt(String.valueOf(file.length()/1024));
        return file_size;
    }
    public void addItem(String key,SupplierDataModel item){
        Hawk.put(key, item);
    }
    public void deleteItem(String key){
        Hawk.delete(key);
    }
    public void updateItem(String key,SupplierDataModel item){
        deleteItem(key);
        addItem(key,item);
    }
    public void readAll(String[] keys){
        for(int i=0;i<keys.length;i++)
            readItem(keys[i]);

    }
    public DataModel readItem(String key){

        return Hawk.get(key);
    }

}
