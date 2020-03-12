package dobrykod.dobreprogramy.majkel.databasecomparision;


import io.realm.RealmObject;
public class DataModel extends RealmObject {

 int id;
    String date;
    String item;
    boolean tick;
    String priority;


public DataModel(){}
    public DataModel(int id,String date, String item, boolean tick,String priority ) {
    this.id=id;
        this.date=date;
        this.item=item;
        this.tick=tick;
this.priority=priority;

    }


    public String getDate() {
        return date;
    }
    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }


    public boolean getTick() {
        return tick;
    }

    public String getPriority() {
        return priority;
    }


}
