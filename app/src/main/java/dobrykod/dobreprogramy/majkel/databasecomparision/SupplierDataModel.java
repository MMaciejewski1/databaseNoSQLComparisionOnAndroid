package dobrykod.dobreprogramy.majkel.databasecomparision;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SupplierDataModel extends RealmObject implements Serializable {
    @PrimaryKey
    int id;
    String companyName;
    String dbaName;
    String address;
    String city;
    String state;
    String zip;
    String phone;
    String productCategoryName;
    String competitiveBid;

    private static int m_Counter = 0;
    public SupplierDataModel(){
        this.id = m_Counter++;
        this.companyName = "companyName";
        this.dbaName = "dbaName";
        this.address = "address";
        this.city = "city";
        this.state = "state";
        this.zip = "zip";
        this.phone =" phone";
        this.productCategoryName = "productCategoryName";
        this.competitiveBid = "competitiveBid";

    }

    public SupplierDataModel(String companyName, String dbaName, String address, String city, String state, String zip,  String phone, String productCategoryName, String competitiveBid) {
        this.id = m_Counter++;
        this.companyName = companyName;
        this.dbaName = dbaName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.productCategoryName = productCategoryName;
        this.competitiveBid = competitiveBid;
    }
    public SupplierDataModel(String id,String companyName, String dbaName, String address, String city, String state, String zip,  String phone, String productCategoryName, String competitiveBid) {
        this.id = Integer.valueOf(id);
        this.companyName = companyName;
        this.dbaName = dbaName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.productCategoryName = productCategoryName;
        this.competitiveBid = competitiveBid;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDbaName() {
        return dbaName;
    }

    public void setDbaName(String dbaName) {
        this.dbaName = dbaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getCompetitiveBid() {
        return competitiveBid;
    }

    public void setCompetitiveBid(String competitiveBid) {
        this.competitiveBid = competitiveBid;
    }
}
