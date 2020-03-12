package dobrykod.dobreprogramy.majkel.databasecomparision;

import android.content.Context;
import android.os.Environment;

import com.orhanobut.hawk.Hawk;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.dizitart.no2.Document.createDocument;
import static org.dizitart.no2.filters.Filters.eq;

public class NitriteDBController {
    Nitrite db;
    NitriteCollection collection;

    public NitriteDBController(Context c){

        this.db = Nitrite.builder()
                .compressed()
                .filePath(c.getFilesDir() + "/testN.db")
                .openOrCreate("user", "password");
        this.collection= this.db.getCollection("test");
    }


    public float size(Context c){
        String path = c.getFilesDir() + "/testN.db";
        File file = new File(path);
        float file_size = file.length()/1024;
        return file_size;
    }
    public void  fillingDB(List<SupplierDataModel> s){
        for(int i=0;i<s.size();i++){
        Document doc = createDocument("id", i)
                .put("companyName", s.get(i).companyName)
                .put("dbaName", s.get(i).dbaName)
                .put("address", s.get(i).address)
                .put("city", s.get(i).city)
                .put("state", s.get(i).state)
                .put("zip", s.get(i).zip)
        .put("phone", s.get(i).phone)
        .put("productCategoryName", s.get(i).productCategoryName)
        .put("competitiveBid",s.get(i).competitiveBid);
            this.collection.insert(doc);
    }
    }
    public void addItem(int key,SupplierDataModel SupplierDataModel){
        Document doc = createDocument("id", SupplierDataModel.id)
                .put("companyName", SupplierDataModel.companyName)
                .put("dbaName", SupplierDataModel.dbaName)
                .put("address", SupplierDataModel.address)
                .put("city", SupplierDataModel.city)
                .put("state", SupplierDataModel.state)
                .put("zip", SupplierDataModel.zip)
                .put("phone", SupplierDataModel.phone)
                .put("productCategoryName", SupplierDataModel.productCategoryName)
                .put("competitiveBid",SupplierDataModel.competitiveBid);
        this.collection.insert(doc);
    }
    public void deleteItem(String key){
        this.collection.remove(eq("id",  key));
    }

    public void updateItem(String key,SupplierDataModel SupplierDataModel){
        this.collection.update(eq("id", key), createDocument("id", SupplierDataModel.id)
                .put("companyName", SupplierDataModel.companyName)
                .put("dbaName", SupplierDataModel.dbaName)
                .put("address", SupplierDataModel.address)
                .put("city", SupplierDataModel.city)
                .put("state", SupplierDataModel.state)
                .put("zip", SupplierDataModel.zip)
                .put("phone", SupplierDataModel.phone)
                .put("productCategoryName", SupplierDataModel.productCategoryName)
                .put("competitiveBid",SupplierDataModel.competitiveBid));
    }
    public ArrayList<SupplierDataModel> readAll(){
            ArrayList<Cursor> temp = new ArrayList<>();
            ArrayList<SupplierDataModel> temp2 = new ArrayList<>();
            for(int i=0;i<collection.size();i++){
                temp.add(collection.find(eq("id", i)));

            }
            for (Cursor document : temp) {
                for (Document doc : document) {
                    temp2.add(new SupplierDataModel(doc.get("id").toString(),doc.get("companyName").toString(),doc.get("dbaName").toString(),doc.get("address").toString(),doc.get("city").toString(),doc.get("state").toString(),doc.get("zip").toString(),doc.get("phone").toString(),doc.get("productCategoryName").toString(),doc.get("competitiveBid").toString()));

                }
            }

return temp2;
    }
    public Document readItem(String key){

        return collection.find(eq("id", key)).firstOrDefault();
    }

}
