package cs.bham.ac.uk.assignment3;

import java.io.Serializable;

/**
 * Created by sandy on 12/02/2018.
 */

public class FoodProduct implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Integer getApiID() {
        return apiID;
    }

    public void setApiID(Integer apiID) {
        this.apiID = apiID;
    }

    private Integer apiID;

    public FoodProduct(String name, Integer apiID){
        this.name = name;
        this.apiID = apiID;
    }

    public String toString(){
        return this.name;
    }
}
