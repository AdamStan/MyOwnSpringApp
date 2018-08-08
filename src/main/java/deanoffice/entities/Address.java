package deanoffice.entities;

import com.sun.org.apache.xalan.internal.xsltc.dom.AdaptiveResultTreeImpl;

public class Address {

    private int id;
    // only normal fields...
    private String city;
    private String Street;
    private String numberOfBuilding;
    private String numberOfFlat;

    public Address(String city, String street, String numberOfBuilding, String numberOfFlat) {
        this.city = city;
        Street = street;
        this.numberOfBuilding = numberOfBuilding;
        this.numberOfFlat = numberOfFlat;
    }

    public Address(String city, String numberOfBuilding) {
        this.city = city;
        this.numberOfBuilding = numberOfBuilding;
    }

    public Address(String oneString){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getNumberOfBuilding() {
        return numberOfBuilding;
    }

    public void setNumberOfBuilding(String numberOfBuilding) {
        this.numberOfBuilding = numberOfBuilding;
    }

    public String getNumberOfFlat() {
        return numberOfFlat;
    }

    public void setNumberOfFlat(String numberOfFlat) {
        this.numberOfFlat = numberOfFlat;
    }
}
