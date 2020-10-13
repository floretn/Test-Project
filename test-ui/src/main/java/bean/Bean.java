package bean;

import dao.Methods;
import model.Model;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name="bean")
@ViewScoped
public class Bean implements Serializable {

    private final Model.Gender genderM = Model.Gender.М;
    private final Model.Gender genderW = Model.Gender.Ж;
    private Model.Gender gender = genderM;
    private String msg;
    private List<Model> models;
    private Model model = Model.builder().build();
    private Date date = new Date();;
    private boolean checkGender = true;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCheckGender() {
        return checkGender;
    }

    public void setCheckGender(boolean checkGender) {
        this.checkGender = checkGender;
    }

    @PostConstruct
    private void init() {
        try {
            Methods.init();
        } catch (IOException e) {
            msg = "Не удалось подключиться к БД:(";
            return;
        }
        msg = "";
        models = Methods.showAll();
    }

    public void deletePerson(Model person){
        Methods.deletePerson(person.getPersonId());
        models.remove(person);
    }

    public void sort(){
        Methods.sort(models);
    }

    public void updatePerson(Model person){
        changeDateAndGender(person);
        Methods.updatePerson(person.getPersonId(), person);
        date = new Date();
    }

    public void insertPerson(){
        changeDateAndGender(model);
        models.add(model);
        Methods.insertPerson(model);
        model = Model.builder().build();
    }

    public void prepareForUpdate(Model person){
        date = new Date(person.getDateOfBirth().getTime());
        gender = person.getGender();
        //checkGender = person.getGender() == Model.Gender.М;
    }

    public void changeDateAndGender(Model person){
        person.setDateOfBirth(new java.sql.Date(date.getTime()));
        person.setGender(gender);
        gender = genderM;
        /*
        if (checkGender) {
            person.setGender(Model.Gender.М);
        }else{
            person.setGender(Model.Gender.Ж);
        }
        checkGender = true;
        */
    }
}