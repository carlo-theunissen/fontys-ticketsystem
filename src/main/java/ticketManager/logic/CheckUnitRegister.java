package ticketManager.logic;

import model.CheckUnitModel;

import java.util.ArrayList;

public class CheckUnitRegister {

    private final ArrayList<CheckUnitModel> checkUnits;

    public CheckUnitRegister() {
        checkUnits = new ArrayList<CheckUnitModel>();
    }

    public void registerUnit(CheckUnitModel unit){
        checkUnits.add(unit);
    }
    public CheckUnitModel[] getUnits(){
        return (CheckUnitModel[]) checkUnits.toArray();
    }
}
