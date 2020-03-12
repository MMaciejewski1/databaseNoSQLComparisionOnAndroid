package dobrykod.dobreprogramy.majkel.databasecomparision;

import android.content.Context;

import com.github.hf.leveldb.Iterator;
import com.github.hf.leveldb.LevelDB;
import com.github.hf.leveldb.exception.LevelDBException;
import com.orhanobut.hawk.Hawk;

import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LevelDBController {
    String T;
    LevelDB levelDB;
    LevelDBController(Context c)  {
        try {
            levelDB = LevelDB.open(String.valueOf(c.getFilesDir()), LevelDB.configure().createIfMissing(true));
        } catch (LevelDBException e) {
            e.printStackTrace();
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


    public void  fillingDB(List<SupplierDataModel> s)  {
        SupplierDataModel temp;
        for(int i=0;i<s.size();i++){
            temp =  s.get(i);
            byte[] data = SerializationUtils.serialize(temp);
            try {
                levelDB.put(String.valueOf(temp.id).getBytes() ,data);
            } catch (LevelDBException e) {
                e.printStackTrace();
            }
        }
    }

    public float size(Context c){
        String path = levelDB.getPath();
        return folderSize(path);
    }
    public void addItem(int key,SupplierDataModel item){
        SupplierDataModel  temp = item;
        byte[] data = SerializationUtils.serialize(temp);
        try {
            levelDB.put(String.valueOf(key).getBytes() ,data);
        } catch (LevelDBException e) {
            e.printStackTrace();
        }

    }
    public void deleteItem(String key){
        try {
            levelDB.del(key.getBytes());
        } catch (LevelDBException e) {
            e.printStackTrace();
        }
    }
    public void updateItem(String key,SupplierDataModel item){
        deleteItem(key);
        addItem(Integer.valueOf(key),item);
    }



    public ArrayList<SupplierDataModel> readAll(){
        ArrayList<SupplierDataModel> temp = new ArrayList<>();
        try (Iterator iterator = levelDB.iterator()) {

            for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                byte[] value = iterator.value();
            temp.add((SupplierDataModel) SerializationUtils.deserialize((value)));
            }

        }
     catch (LevelDBException e) {
        e.printStackTrace();
    }
        return temp;
    }
    public byte[] readItem(String key){
        byte[] bytes = null;
        String l = "1";
        try {
           bytes = levelDB.get(l.getBytes());
        } catch (LevelDBException e) {
            e.printStackTrace();
        }

        return bytes;
    }

}
