package dobrykod.dobreprogramy.majkel.databasecomparision;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        final float[] realmTimes = {sharedPref.getFloat("realmCreate", 0),sharedPref.getFloat("realmAddOnce", 0),sharedPref.getFloat("realmAddAll", 0),sharedPref.getFloat("realmDelOnce", 0),sharedPref.getFloat("realmDelAll", 0),sharedPref.getFloat("realmEditOnce", 0),sharedPref.getFloat("realmEditAll", 0),sharedPref.getFloat("realmRead", 0),sharedPref.getFloat("realmReadOne", 0)};
        final float[] couchTimes = {sharedPref.getFloat("couchCreate", 0),sharedPref.getFloat("couchAddOnce", 0),sharedPref.getFloat("couchAddAll", 0),sharedPref.getFloat("couchDelOnce", 0),sharedPref.getFloat("couchDelAll", 0),sharedPref.getFloat("couchEditOnce", 0),sharedPref.getFloat("couchEditAll", 0),sharedPref.getFloat("couchRead", 0),sharedPref.getFloat("couchReadOne", 0)};
        final float[] levelTimes = {sharedPref.getFloat("levelCreate", 0),sharedPref.getFloat("levelAddOnce", 0),sharedPref.getFloat("levelAddAll", 0),sharedPref.getFloat("levelDelOnce", 0),sharedPref.getFloat("levelDelAll", 0),sharedPref.getFloat("levelEditOnce", 0),sharedPref.getFloat("levelEditAll", 0),sharedPref.getFloat("levelRead", 0),sharedPref.getFloat("levelReadOne", 0)};
        final float[] nitriteTimes = {sharedPref.getFloat("nitriteCreate", 0),sharedPref.getFloat("nitriteAddOnce", 0),sharedPref.getFloat("nitriteAddAll", 0),sharedPref.getFloat("nitriteDelOnce", 0),sharedPref.getFloat("nitriteDelAll", 0),sharedPref.getFloat("nitriteEditOnce", 0),sharedPref.getFloat("nitriteEditAll", 0),sharedPref.getFloat("nitriteRead", 0),sharedPref.getFloat("nitriteReadOne", 0)};
        final float[] waspTimes = {sharedPref.getFloat("waspCreate", 0),sharedPref.getFloat("waspAddOnce", 0),sharedPref.getFloat("waspAddAll", 0),sharedPref.getFloat("waspDelOnce", 0),sharedPref.getFloat("waspDelAll", 0),sharedPref.getFloat("waspEditOnce", 0),sharedPref.getFloat("waspEditAll", 0),sharedPref.getFloat("waspRead", 0),sharedPref.getFloat("waspReadOne", 0)};
        //final float  realmTime1 = sharedPref.getFloat("realmTime", 0);
        // final float  couchTime1 = sharedPref.getFloat("couchTime", 0);
        // final float  levelDBTime1 = sharedPref.getFloat("levelDBTime", 0);
        // final float  nitriteTime1 = sharedPref.getFloat("nitriteTime", 0);
        // final float  waspTime1 = sharedPref.getFloat("waspTime", 0);


//tworzenie listy z csv
        List<SupplierDataModel> testD = new ArrayList<>();
        if(realmTimes[0]==0){
            try {
                InputStream s = getResources().openRawResource(R.raw.data);
                Reader targetReader = new InputStreamReader(s);
                CSVReader reader = new CSVReader(targetReader);
                String[] nextLine;
                reader.readNext();
                while ((nextLine = reader.readNext()) != null) {
                    testD.add(new SupplierDataModel(nextLine[2],nextLine[3],nextLine[4],nextLine[6],nextLine[7],nextLine[8],nextLine[10],nextLine[12],nextLine[13]));
                }
            } catch (IOException e) {
            }
        }

        ////// Tworzenie bazy
        setContentView(R.layout.activity_main);

        long startTime = SystemClock.elapsedRealtime();
        RealmDBController realmC = new RealmDBController();
        Realm realm=  realmC.RealmDBController(this);
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        final float    realmTime = (float) (elapsedMilliSeconds );

        startTime = SystemClock.elapsedRealtime();
        CouchBaseDBController couchC = new CouchBaseDBController(this);
        endTime = SystemClock.elapsedRealtime();
        elapsedMilliSeconds = endTime - startTime;
        final float  couchTime = (float) (elapsedMilliSeconds );

        startTime = SystemClock.elapsedRealtime();
        LevelDBController levelDBC = new LevelDBController(this);
        endTime = SystemClock.elapsedRealtime();
        elapsedMilliSeconds = endTime - startTime;
        final float   levelDBTime = (float) (elapsedMilliSeconds );

        startTime = SystemClock.elapsedRealtime();
        NitriteDBController nitriteC = new NitriteDBController(this);
        endTime = SystemClock.elapsedRealtime();
        elapsedMilliSeconds = endTime - startTime;
        final float    nitriteTime = (float) (elapsedMilliSeconds );

        startTime = SystemClock.elapsedRealtime();
        WaspDBController waspC = new WaspDBController(this);
        endTime = SystemClock.elapsedRealtime();
        elapsedMilliSeconds = endTime - startTime;
        final float waspTime = (float) (elapsedMilliSeconds );

        final ArrayList<BarEntry> createTimes = new ArrayList<>();
        final ArrayList<BarEntry> addTimes = new ArrayList<>();
        final ArrayList<BarEntry> updateTimes = new ArrayList<>();
        final ArrayList<BarEntry> deleteTimes = new ArrayList<>();
        final ArrayList<BarEntry> addOneTimes = new ArrayList<>();
        final ArrayList<BarEntry> readTimes = new ArrayList<>();
        final ArrayList<BarEntry> readOneTimes = new ArrayList<>();
        final ArrayList<BarEntry> size = new ArrayList<>();

        //final float  realmSizeTime = realmC.size(realm);
        //final float  couchSizeTime = couchC.size(this);
        //final float  levelDBSizeTime = levelDBC.size(this);
        //final float  nitriteSizeTime = nitriteC.size(this);
        //final float  waspSizeTime = waspC.size(this);

        final float  realmAddTime;
        final float  couchAddTime;
        final float  levelDBAddTime;
        final float  nitriteAddTime;
        final float  waspAddTime;
        if(realmTimes[2]==0){
            //////dodawanie
            startTime = SystemClock.elapsedRealtime();
            realmC.fillingDB(realm,this,testD);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            realmAddTime = (float) (elapsedMilliSeconds );
            editor.putFloat("realmAddAll",realmAddTime);

            startTime = SystemClock.elapsedRealtime();
            couchC.fillingDB(testD);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            couchAddTime = (float) (elapsedMilliSeconds );
            editor.putFloat("couchAddAll",couchAddTime);

            startTime = SystemClock.elapsedRealtime();
            levelDBC.fillingDB(testD);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            levelDBAddTime  = (float) (elapsedMilliSeconds );
            editor.putFloat("levelAddAll",levelDBAddTime);

            startTime = SystemClock.elapsedRealtime();
            nitriteC.fillingDB(testD);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            nitriteAddTime = (float) (elapsedMilliSeconds );
            editor.putFloat("nitriteAddAll",nitriteAddTime);

            startTime = SystemClock.elapsedRealtime();
            waspC.fillingDB(testD);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            waspAddTime = (float) (elapsedMilliSeconds );
            editor.putFloat("waspAddAll",waspAddTime);
            editor.commit();
        }
        else {
            realmAddTime = 0;
            couchAddTime= 0;
            levelDBAddTime= 0;
            nitriteAddTime= 0;
            waspAddTime  = 0;
        }



//////aktualizowanie
        final float waspUpdateTime,nitriteUpdateTime,levelDBUpdateTime,couchUpdateTime,realmUpdateTime ;

        if(realmTimes[2]==0) {
            startTime = SystemClock.elapsedRealtime();
            realmC.updateItem(realm, this, 1, testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            realmUpdateTime = (float) (elapsedMilliSeconds);


            startTime = SystemClock.elapsedRealtime();
            try {
                couchC.updateItem(1, testD.get(0));
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            couchUpdateTime = (float) (elapsedMilliSeconds);

            startTime = SystemClock.elapsedRealtime();
            levelDBC.updateItem(String.valueOf(testD.get(2).id), testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            levelDBUpdateTime = (float) (elapsedMilliSeconds);


            startTime = SystemClock.elapsedRealtime();
            nitriteC.updateItem("1", testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            nitriteUpdateTime = (float) (elapsedMilliSeconds);

            startTime = SystemClock.elapsedRealtime();
            waspC.updateItem("1", testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            waspUpdateTime = (float) (elapsedMilliSeconds);
        }
        else {waspUpdateTime=0;nitriteUpdateTime=0;levelDBUpdateTime=0;couchUpdateTime=0;realmUpdateTime=0;}

        if (realmTimes[6] == 0) {
            editor.putFloat("realmEditAll", realmUpdateTime);
            editor.putFloat("couchEditAll", couchUpdateTime);
            editor.putFloat("levelEditAll", levelDBUpdateTime);
            editor.putFloat("nitriteEditAll", nitriteUpdateTime);
            editor.putFloat("waspEditAll", waspUpdateTime);
            editor.commit();
            updateTimes.add(new BarEntry(1, realmUpdateTime));
            updateTimes.add(new BarEntry(2, couchUpdateTime));
            updateTimes.add(new BarEntry(3, levelDBUpdateTime));
            updateTimes.add(new BarEntry(4, nitriteUpdateTime));
            updateTimes.add(new BarEntry(5, waspUpdateTime));
        } else {
            updateTimes.add(new BarEntry(1, realmTimes[6]));
            updateTimes.add(new BarEntry(2, couchTimes[6]));
            updateTimes.add(new BarEntry(3, levelTimes[6]));
            updateTimes.add(new BarEntry(4, nitriteTimes[6]));
            updateTimes.add(new BarEntry(5, waspTimes[6]));
        }

        //odczyt calosci
        final float   levelDBReadTime,couchReadTime,realmReadTime,waspReadTime,nitriteReadTime;
        if(realmTimes[7]==0) {
            startTime = SystemClock.elapsedRealtime();
            nitriteC.readAll();
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            nitriteReadTime = (float) (elapsedMilliSeconds);

            startTime = SystemClock.elapsedRealtime();
            List<SupplierDataModel> b = waspC.readAll();
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            waspReadTime = (float) (elapsedMilliSeconds);

            startTime = SystemClock.elapsedRealtime();
            ArrayList<SupplierDataModel> c = realmC.readAll(realm, this);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            realmReadTime = (float) (elapsedMilliSeconds / 10);

            startTime = SystemClock.elapsedRealtime();
            ArrayList<SupplierDataModel> d = couchC.readAll(this);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            couchReadTime = (float) (elapsedMilliSeconds);

            startTime = SystemClock.elapsedRealtime();
            ArrayList<SupplierDataModel> e = levelDBC.readAll();
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            levelDBReadTime = (float) (elapsedMilliSeconds);
        }

        else{ levelDBReadTime=0;couchReadTime=0;realmReadTime=0;waspReadTime=0;nitriteReadTime=0;}
//////dodawanie
        //   if(realmTimes[0]==0){
        //    editor.putFloat("realmCreate", 1);
        //    editor.putFloat("couchCreate", 2);
        //    editor.putFloat("levelCreate", 3);
        //     editor.putFloat("nitriteCreate", 4);
        //     editor.putFloat("waspCreate", 5);
        //     editor.commit();
        //     size.add(new BarEntry(1, realmTime));
        //      size.add(new BarEntry(2, couchTime));
        //      size.add(new BarEntry( 3, levelDBTime));
        //      size.add(new BarEntry( 4, nitriteTime));
        //      size.add(new BarEntry( 5, waspTime));
        //  }
        //   else {
        //       size.add(new BarEntry(1, realmSizeTime));
        //       size.add(new BarEntry(2, couchSizeTime));
        //       size.add(new BarEntry(3, levelDBSizeTime));
        //       size.add(new BarEntry(4, nitriteSizeTime));
        //       size.add(new BarEntry(5, waspSizeTime));
        //   }

        final float realmReadOneTime,couchReadOneTime,levelDBReadOneTime,nitriteReadOneTime, waspReadOneTime ;
        if(realmTimes[8]==0){
            //////czytanie pojedyncze
            startTime = SystemClock.elapsedRealtime();
            realmC.readItem(realm,this,(testD.get(1).companyName));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            realmReadOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("realmReadOne",realmReadOneTime);

            startTime = SystemClock.elapsedRealtime();
            couchC.readItem(testD.get(0).id);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            couchReadOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("couchReadOne",couchReadOneTime);

            startTime = SystemClock.elapsedRealtime();
            levelDBC.readItem(String.valueOf(testD.get(1).id));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            levelDBReadOneTime  = (float) (elapsedMilliSeconds );
            editor.putFloat("levelReadOne",levelDBReadOneTime);

            startTime = SystemClock.elapsedRealtime();
            nitriteC.readItem(String.valueOf(testD.get(0).id));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            nitriteReadOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("nitriteReadOne",nitriteReadOneTime);

            startTime = SystemClock.elapsedRealtime();
            waspC.readItem(String.valueOf(testD.get(0).id));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            waspReadOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("waspReadOne",waspReadOneTime);
            editor.commit();
        }
        else {
            realmReadOneTime = 0;
            couchReadOneTime= 0;
            levelDBReadOneTime= 0;
            nitriteReadOneTime= 0;
            waspReadOneTime  = 0;
        }

        if(realmTimes[8]==0){
            editor.putFloat("realmReadOne", realmReadOneTime);
            editor.putFloat("couchReadOne", couchReadOneTime);
            editor.putFloat("levelReadOne", levelDBReadOneTime);
            editor.putFloat("nitriteReadOne", nitriteReadOneTime);
            editor.putFloat("waspReadOne", waspReadOneTime);
            editor.commit();
            readOneTimes.add(new BarEntry(1, realmReadOneTime));
            readOneTimes.add(new BarEntry(2, couchReadOneTime));
            readOneTimes.add(new BarEntry( 3, levelDBReadOneTime));
            readOneTimes.add(new BarEntry( 4, nitriteReadOneTime));
            readOneTimes.add(new BarEntry( 5, waspReadOneTime));
        }
        else {
            readOneTimes.add(new BarEntry(1, realmTimes[8]));
            readOneTimes.add(new BarEntry(2, couchTimes[8]));
            readOneTimes.add(new BarEntry( 3, levelTimes[8]));
            readOneTimes.add(new BarEntry( 4, nitriteTimes[8]));
            readOneTimes.add(new BarEntry( 5, waspTimes[8]));
        }


        final float realmAddOneTime,couchAddOneTime,levelDBAddOneTime,nitriteAddOneTime, waspAddOneTime ;
        if(realmTimes[1]==0){
            //////czytanie pojedyncze
            startTime = SystemClock.elapsedRealtime();
            realmC.addItem(realm,this,new SupplierDataModel());
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            realmAddOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("realmAddOnce",realmAddOneTime);

            startTime = SystemClock.elapsedRealtime();
            couchC.addItem(testD.size()+1,testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            couchAddOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("couchAddOnce",couchAddOneTime);

            startTime = SystemClock.elapsedRealtime();
            levelDBC.addItem(testD.size()+1,(testD.get(0)));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            levelDBAddOneTime  = (float) (elapsedMilliSeconds );
            editor.putFloat("levelAddOnce",levelDBAddOneTime);

            startTime = SystemClock.elapsedRealtime();
            nitriteC.addItem(testD.size()+1,testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            nitriteAddOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("nitriteAddOnce",nitriteAddOneTime);

            startTime = SystemClock.elapsedRealtime();
            waspC.addItem(testD.size()+1,testD.get(0));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            waspAddOneTime = (float) (elapsedMilliSeconds );
            editor.putFloat("waspAddOnce",waspAddOneTime);
            editor.commit();
        }
        else {
            realmAddOneTime = 0;
            couchAddOneTime= 0;
            levelDBAddOneTime= 0;
            nitriteAddOneTime= 0;
            waspAddOneTime  = 0;
        }

        if(realmTimes[8]==0){
            editor.putFloat("realmAddOnce", realmAddOneTime);
            editor.putFloat("couchAddOnce", couchAddOneTime);
            editor.putFloat("levelAddOnce", levelDBAddOneTime);
            editor.putFloat("nitriteAddOnce", nitriteAddOneTime);
            editor.putFloat("waspAddOnce", waspAddOneTime);
            editor.commit();
            addOneTimes.add(new BarEntry(1, realmAddOneTime));
            addOneTimes.add(new BarEntry(2, couchAddOneTime));
            addOneTimes.add(new BarEntry( 3, levelDBAddOneTime));
            addOneTimes.add(new BarEntry( 4, nitriteAddOneTime));
            addOneTimes.add(new BarEntry( 5, waspAddOneTime));
        }
        else {
            addOneTimes.add(new BarEntry(1, realmTimes[1]));
            addOneTimes.add(new BarEntry(2, couchTimes[1]));
            addOneTimes.add(new BarEntry( 3, levelTimes[1]));
            addOneTimes.add(new BarEntry( 4, nitriteTimes[1]));
            addOneTimes.add(new BarEntry( 5, waspTimes[8]));
        }

        if(realmTimes[7]==0){
            editor.putFloat("realmRead", realmReadTime);
            editor.putFloat("couchRead", couchReadTime);
            editor.putFloat("levelRead", levelDBReadTime);
            editor.putFloat("nitriteRead", nitriteReadTime);
            editor.putFloat("waspRead", waspReadTime);
            editor.commit();
            readTimes.add(new BarEntry(1, realmReadTime));
            readTimes.add(new BarEntry(2, couchReadTime));
            readTimes.add(new BarEntry( 3, levelDBReadTime));
            readTimes.add(new BarEntry( 4, nitriteReadTime));
            readTimes.add(new BarEntry( 5, waspReadTime));
        }
        else {
            readTimes.add(new BarEntry(1, realmTimes[7]));
            readTimes.add(new BarEntry(2, couchTimes[7]));
            readTimes.add(new BarEntry( 3, levelTimes[7]));
            readTimes.add(new BarEntry( 4, nitriteTimes[7]));
            readTimes.add(new BarEntry( 5, waspTimes[7]));
        }

        if(realmTimes[0]==0){
            editor.putFloat("realmCreate", realmTime);
            editor.putFloat("couchCreate", couchTime);
            editor.putFloat("levelCreate", levelDBTime);
            editor.putFloat("nitriteCreate", nitriteTime);
            editor.putFloat("waspCreate", waspTime);
            editor.commit();
            createTimes.add(new BarEntry(1, realmTime));
            createTimes.add(new BarEntry(2, couchTime));
            createTimes.add(new BarEntry( 3, levelDBTime));
            createTimes.add(new BarEntry( 4, nitriteTime));
            createTimes.add(new BarEntry( 5, waspTime));
        }
        else {
            createTimes.add(new BarEntry(1, realmTimes[0]));
            createTimes.add(new BarEntry(2, couchTimes[0]));
            createTimes.add(new BarEntry( 3, levelTimes[0]));
            createTimes.add(new BarEntry( 4, nitriteTimes[0]));
            createTimes.add(new BarEntry( 5, waspTimes[0]));
        }

        if(realmTimes[2]==0){
            editor.putFloat("realmAddAll", realmAddTime);
            editor.putFloat("couchAddAll", couchAddTime);
            editor.putFloat("levelAddAll", levelDBAddTime);
            editor.putFloat("nitriteAddAll", nitriteAddTime);
            editor.putFloat("waspAddAll", waspAddTime);
            editor.commit();
            addTimes.add(new BarEntry(1, realmAddTime));
            addTimes.add(new BarEntry(2, couchAddTime));
            addTimes.add(new BarEntry( 3, levelDBAddTime));
            addTimes.add(new BarEntry( 4, nitriteAddTime));
            addTimes.add(new BarEntry( 5, waspAddTime));
        }
        else {
            addTimes.add(new BarEntry(1, realmTimes[2]));
            addTimes.add(new BarEntry(2, couchTimes[2]));
            addTimes.add(new BarEntry( 3, levelTimes[2]));
            addTimes.add(new BarEntry( 4, nitriteTimes[2]));
            addTimes.add(new BarEntry( 5, waspTimes[2]));
        }

        BarDataSet addOneDataset = new BarDataSet(addOneTimes, "w milisekundach");
        addOneDataset .setDrawValues(true);
        ArrayList<IBarDataSet> AddOneDataSets = new ArrayList<>();
        AddOneDataSets.add(addOneDataset);
        final  BarData addOneData = new BarData(AddOneDataSets);

        BarDataSet readOneDataset = new BarDataSet(readOneTimes, "w milisekundach");
        readOneDataset .setDrawValues(true);
        ArrayList<IBarDataSet> readOneDataSets = new ArrayList<>();
        readOneDataSets.add(readOneDataset);
        final  BarData readOneData = new BarData(readOneDataSets);


        BarDataSet addDataset = new BarDataSet(addTimes, "w milisekundach");
        addDataset .setDrawValues(true);
        ArrayList<IBarDataSet> AddDataSets = new ArrayList<>();
        AddDataSets.add(addDataset);
        final  BarData addData = new BarData(AddDataSets);

        BarDataSet createDataset = new BarDataSet(createTimes, "w milisekundach");
        createDataset .setDrawValues(true);
        ArrayList<IBarDataSet> createDataSets = new ArrayList<>();
        createDataSets.add(createDataset);
        final  BarData createData = new BarData(createDataSets);

        BarDataSet updateDataset = new BarDataSet(updateTimes, "w milisekundach");
        updateDataset .setDrawValues(true);
        ArrayList<IBarDataSet> updateDataSets = new ArrayList<>();
        updateDataSets.add(updateDataset);
        final  BarData updateData = new BarData(updateDataSets);

        //usuwanie
        final float  waspdeleteTime,nitritedeleteTime,levelDBdeleteTime,couchdeleteTime, realmdeleteTime;
        if(realmTimes[4]==0){
            startTime = SystemClock.elapsedRealtime();
            realmC.deleteItem(realm,this,1);
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            realmdeleteTime = (float) (elapsedMilliSeconds );

            startTime = SystemClock.elapsedRealtime();
            try {
                couchC.deleteItem(1);
            } catch (CouchbaseLiteException z) {
                z.printStackTrace();
            }
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            couchdeleteTime = (float) (elapsedMilliSeconds );

            startTime = SystemClock.elapsedRealtime();
            levelDBC.deleteItem(String.valueOf(testD.get(0).id));
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            levelDBdeleteTime = (float) (elapsedMilliSeconds );

            startTime = SystemClock.elapsedRealtime();
            nitriteC.deleteItem("1");
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            nitritedeleteTime = (float) (elapsedMilliSeconds );

            startTime = SystemClock.elapsedRealtime();
            waspC.deleteItem("");
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds = endTime - startTime;
            waspdeleteTime = (float) (elapsedMilliSeconds );
        }
        else { waspdeleteTime=0;nitritedeleteTime=0;levelDBdeleteTime=0;couchdeleteTime=0; realmdeleteTime=0;}

        if(realmTimes[4]==0){
            editor.putFloat("realmDelAll", realmdeleteTime);
            editor.putFloat("couchDelAll", couchdeleteTime);
            editor.putFloat("levelDelAll", levelDBdeleteTime);
            editor.putFloat("nitriteDelAll", nitritedeleteTime);
            editor.putFloat("waspDelAll", waspdeleteTime);
            editor.commit();
            deleteTimes.add(new BarEntry(1, realmdeleteTime));
            deleteTimes.add(new BarEntry(2, couchdeleteTime));
            deleteTimes.add(new BarEntry( 3, levelDBdeleteTime));
            deleteTimes.add(new BarEntry( 4, nitritedeleteTime));
            deleteTimes.add(new BarEntry( 5, waspdeleteTime));
        }
        else {
            deleteTimes.add(new BarEntry(1, realmTimes[4]));
            deleteTimes.add(new BarEntry(2, couchTimes[4]));
            deleteTimes.add(new BarEntry(3, levelTimes[4]));
            deleteTimes.add(new BarEntry(4, nitriteTimes[4]));
            deleteTimes.add(new BarEntry(5, waspTimes[4]));
        }

        BarDataSet deleteDataset = new BarDataSet(deleteTimes, "w milisekundach");
        createDataset .setDrawValues(true);
        ArrayList<IBarDataSet> deleteDataSets = new ArrayList<>();
        deleteDataSets.add(deleteDataset);
        final  BarData deleteData = new BarData(deleteDataSets);

        BarDataSet readDataset = new BarDataSet(readTimes, "w milisekundach");
        createDataset .setDrawValues(true);
        ArrayList<IBarDataSet> readDataSets = new ArrayList<>();
        readDataSets.add(readDataset);
        final  BarData readData = new BarData(readDataSets);

        BarDataSet sizeDataset = new BarDataSet(size, "w bajtach");
        sizeDataset .setDrawValues(true);
        ArrayList<IBarDataSet> sizeDataSets = new ArrayList<>();
        sizeDataSets.add(sizeDataset);
        final  BarData sizeData = new BarData(sizeDataSets);

        final ArrayList<String> labels = new ArrayList<>();
        labels.add("realm");
        labels.add("couch");
        labels.add("levelDB");
        labels.add("nitrite");
        labels.add("wasp");

        final String[] quarters = new String[] { "","realm", "couchbase", "levelDB", "nitrite" , "wasp" };

        final BarChart chart = findViewById(R.id.chart);
        //setContentView(chart);
        chart.setFitBars(true);
        chart.setDrawValueAboveBar(true);
        chart.setFitBars(true);
        chart.setDrawBarShadow(true);
        //  chart.setData(addData);

        chart.animateXY(3000, 3000);

        addDataset.setColors(ColorTemplate.COLORFUL_COLORS);
        createDataset.setColors(ColorTemplate.MATERIAL_COLORS);
        readDataset.setColors(ColorTemplate.JOYFUL_COLORS);
        updateDataset.setColors(ColorTemplate.LIBERTY_COLORS);
        deleteDataset.setColors(ColorTemplate.MATERIAL_COLORS);
        addOneDataset.setColors(ColorTemplate.MATERIAL_COLORS);
        readOneDataset.setColors(ColorTemplate.LIBERTY_COLORS);
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        chart.getDescription().setEnabled(false);
        chart.invalidate();

        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Tworzenie Bazy"){
                    setTitle("Tworzenie Bazy");
                    chart.setData(createData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }

        });

        Button addOneButton = findViewById(R.id.sizeButton);
        addOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Dodanie jednego elementu do Bazy"){
                    setTitle("Dodanie jednego elementu do Bazy");
                    chart.setData(addOneData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }

        });
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Dodawanie do Bazy"){
                    setTitle("Dodawanie do Bazy");
                    chart.setData(addData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }
        });

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Edycja Bazy"){
                    setTitle("Edycja Bazy");
                    chart.setData(updateData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }
        });


        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Usuniecie z Bazy"){
                    setTitle("Usuniecie z Bazy");
                    chart.setData(deleteData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }
        });

        Button readButton = findViewById(R.id.readButton);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Czytanie z Bazy"){
                    setTitle("Czytanie z Bazy");
                    chart.setData(readData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }
        });

        Button delOneButton = findViewById(R.id.readOnebutton);
        delOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTitle()!="Czytanie jednego elementu z Bazy"){
                    setTitle("Czytanie jednego elementu z Bazy");
                    chart.setData(readOneData);
                    chart.animateXY(3000, 3000);
                    chart.invalidate();
                }
            }

        });




    }
}