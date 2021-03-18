package bean;

import model.Model;

public class HelpForGender {
    private final Model.Gender genderM = Model.Gender.M;
    private final Model.Gender genderW = Model.Gender.W;
    private Model.Gender gender;


    public Model.Gender getGender() {
        return gender;
    }

    public void setGender(Model.Gender gender) {
        this.gender = gender;
    }

    public Model.Gender getGenderM() {
        return genderM;
    }

    public Model.Gender getGenderW() {
        return genderW;
    }
}
