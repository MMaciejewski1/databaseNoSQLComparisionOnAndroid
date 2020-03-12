package dobrykod.dobreprogramy.majkel.databasecomparision;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDBController {
    ArrayList<SupplierDataModel> dataModels;

      public Realm RealmDBController(Context c){
      Realm.init(c);
       Realm realm = Realm.getDefaultInstance();

          if(!realm.isEmpty()){
              dataModels= new ArrayList<>();
              RealmResults<SupplierDataModel> data = realm.where(SupplierDataModel.class).findAll();
              for(int i=0;i<data.size();i++){
                  dataModels.add(data.get(i));
              }
          }


       return realm;
    }
public void fillingDB(Realm realm, Context c, List<SupplierDataModel> s){
    if(realm.isEmpty()){
        realm.beginTransaction();
        dataModels= new ArrayList<>();
        for(int i=0;i<s.size();i++){
            dataModels.add(s.get(i));
            realm.copyToRealm(dataModels.get(i));
        }
        realm.commitTransaction();
    }
}
public void addItem(Realm realm, Context c, SupplierDataModel item){
    dataModels.add(item);
    realm.beginTransaction();
    realm.copyToRealm(item);
    realm.commitTransaction();
}
public void deleteItem(Realm realm,Context c,int num){
    realm.beginTransaction();
    final RealmResults<SupplierDataModel> data = realm.where(SupplierDataModel.class).findAll();
    data.deleteFromRealm(num);
    realm.commitTransaction();
}
public void updateItem(Realm realm, Context c, final int num, final SupplierDataModel item){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SupplierDataModel temp = dataModels.get(num);
                temp = item;
                realm.copyToRealmOrUpdate(temp);
            }
        });
}
public ArrayList<SupplierDataModel> readAll(Realm realm, Context c){
    RealmResults<SupplierDataModel> result = realm.where(SupplierDataModel.class).findAll();
    ArrayList<SupplierDataModel> temp = new ArrayList<>();
    for(int i=0;i<result.size();i++){
        temp.add(result.get(i));
    }
    return temp;
}
public SupplierDataModel readItem(Realm realm,Context c,String num){
    SupplierDataModel data = realm.where(SupplierDataModel.class).equalTo("companyName", num).findFirst();
return data;
}

public float size(Realm realm){
    String path = realm.getConfiguration().getPath();
    File file = new File(path);
    float file_size = Integer.parseInt(String.valueOf(file.length()/1024));
    return file_size;
}
}
