package ru.sfedu.myApp.Entity;

import ru.sfedu.myApp.DataProviderXML;

public class History{
    private Pet petObj;

    private Service serObj;

    private String serResult;

    private String petStatus;

    public History(){

    }

    public Pet getPetObj() {
        return petObj;
    }

    public void setPetObj(Pet petObj) {
        this.petObj = petObj;
    }

    public Service getSerObj() {
        return serObj;
    }

    public void setSerObj(Service serObj) {
        this.serObj = serObj;
    }

    public String getSerResult() {
        return serResult;
    }

    public void setSerResult(String serResult) {
        this.serResult = serResult;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    public void addHistoryOfPet(String id) throws Exception {
        DataProviderXML dataProvider = new DataProviderXML();
        //petObj = dataProvider.getPetRecordByID(id);
    }
}
