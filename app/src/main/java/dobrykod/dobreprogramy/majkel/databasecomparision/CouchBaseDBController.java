package dobrykod.dobreprogramy.majkel.databasecomparision;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.Expression;
import com.couchbase.lite.Meta;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Ordering;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CouchBaseDBController {

    Database database;

    CouchBaseDBController(Context c) {
        database(c);
    }

    protected void database(Context c){

        DatabaseConfiguration config = new DatabaseConfiguration(c);
        try {
            database = new Database("mydb", config);
        } catch (CouchbaseLiteException e) {
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

    public float size(Context c){

        return folderSize(database.getPath());
    }

    public void  fillingDB( List<SupplierDataModel> s){

        MutableDocument newTask = new MutableDocument();
        for(int i=0;i<s.size();i++){
            newTask.setInt("id",i);
            newTask.setString("companyName", s.get(i).companyName);
            newTask.setString("dbaName", s.get(i).dbaName);
            newTask.setString("address", s.get(i).address);
            newTask.setString("city", s.get(i).city);
            newTask.setString("state", s.get(i).state);
            newTask.setString("zip", s.get(i).zip);
            newTask.setString("phone", s.get(i).phone);
            newTask.setString("productCategoryName", s.get(i).productCategoryName);
            newTask.setString("competitiveBid", String.valueOf(s.get(i).competitiveBid));
        try {
            database.save(newTask);
        } catch (CouchbaseLiteException e) {
            com.couchbase.lite.internal.support.Log.e(TAG, e.toString());
        }
        }
    }
    public void addItem(int key,SupplierDataModel s){

        int i=0;
        MutableDocument newTask = new MutableDocument();
        newTask.setString("companyName", s.companyName);
        newTask.setString("dbaName", s.dbaName);
        newTask.setString("address", s.address);
        newTask.setString("city", s.city);
        newTask.setString("state", s.state);
        newTask.setString("zip", s.zip);
        newTask.setString("phone", s.phone);
        newTask.setString("productCategoryName", s.productCategoryName);
        newTask.setString("competitiveBid", String.valueOf(s.competitiveBid));
        try {
            database.save(newTask);
        } catch (CouchbaseLiteException e) {
            com.couchbase.lite.internal.support.Log.e(TAG, e.toString());
        }
    }
    public void deleteItem(int key) throws CouchbaseLiteException {

        Query query = QueryBuilder.select(SelectResult.expression(Meta.id)).from(DataSource.database(database)).where(Expression.property("id").equalTo(Expression.intValue(key)));
        ResultSet resultSet = query.execute();
        for(Result result : resultSet) {
            String id = result.getString(0);
            Document document = database.getDocument(id);
            if (document != null) {
                database.delete(document);
            }
       }
       database.delete();
    }
    public void updateItem(int key,SupplierDataModel item) throws CouchbaseLiteException {
        Query query = QueryBuilder.select(SelectResult.expression(Meta.id)).from(DataSource.database(database)).where(Expression.property("id").equalTo(Expression.intValue(key)));
        ResultSet resultSet = null;
        try {
            resultSet = query.execute();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for(Result result : resultSet) {
            String id = result.getString(0);
            Document document = database.getDocument(id);
            MutableDocument mutableDocument = document.toMutable();
            mutableDocument.setString("companyName", "competitiveBid");
            mutableDocument.setString("dbaName", "competitiveBid");
            mutableDocument.setString("address", "competitiveBid");
            mutableDocument.setString("city", "competitiveBid");
            mutableDocument.setString("state", "competitiveBid");
            mutableDocument.setString("zip", "competitiveBid");
            mutableDocument.setString("phone", "competitiveBid");
            mutableDocument.setString("productCategoryName", "competitiveBid");
            mutableDocument.setString("competitiveBid", "competitiveBid");
            if (document != null) {
                database.save(mutableDocument);
            }
        }
    }
    public ArrayList<SupplierDataModel> readAll(Context c){
        database(c);
        Query query2 = QueryBuilder
                .select(SelectResult.expression(Meta.id),
                        SelectResult.all())
                .from(DataSource.database(database));
        ResultSet resultSet2 = null;
        SupplierDataModel document = null;
        ArrayList<SupplierDataModel> temp = new ArrayList<>();

        try {
            resultSet2 = query2.execute();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for(Result result : resultSet2) {
            String id = result.getString(0);

            document = new SupplierDataModel(
            result.getString("companyName"),
            result.getString("dbaName"),
            result.getString("address"),
            result.getString("city"),
            result.getString("state"),
            result.getString("zip"),
            result.getString("phone"),
            result.getString("productCategoryName"),
            result.getString("competitiveBid"));
            temp.add(document);
        }

return temp;
    }
    public Document readItem(int key){
        Query query2 = QueryBuilder.select(SelectResult.expression(Meta.id)).from(DataSource.database(database)).where(Expression.property("id").equalTo(Expression.intValue(key)));
        ResultSet resultSet2 = null;
        Document document = null;
        document = database.getDocument(String.valueOf(key));
        try {
            resultSet2 = query2.execute();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        for(Result result : resultSet2) {
            String id = result.getString(0);
            document = database.getDocument(id);
        }
        return document;
    }

}

