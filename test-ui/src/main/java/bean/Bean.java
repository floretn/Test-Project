package bean;

import dao.methods.Methods;
import model.Model;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name="bean")
@ViewScoped
public class Bean implements Serializable {

    private final Model.Gender genderM = Model.Gender.М;
    private final Model.Gender genderW = Model.Gender.Ж;
    private Model.Gender genderForUpdate = genderM;
    private Model.Gender genderForInsert = genderM;
    private String msg;
    private List<Model> models;
    private Model modelForUpdate;
    private Model modelForInsert;
    private Model model;
    private Date dateForUpdate = new Date();
    private Date dateForInsert = new Date();
    private String check = "";
    private Methods methods;

    public Model.Gender getGenderForUpdate() {
        return genderForUpdate;
    }

    public void setGenderForUpdate(Model.Gender genderForUpdate) {
        this.genderForUpdate = genderForUpdate;
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

    public Model getModelForUpdate() {
        return modelForUpdate;
    }

    public void setModelForUpdate(Model modelForUpdate) {
        this.modelForUpdate = modelForUpdate;
    }

    public Model getModelForInsert() {
        return modelForInsert;
    }

    public void setModelForInsert(Model modelForInsert) {
        this.modelForInsert = modelForInsert;
    }

    public Date getDateForUpdate() {
        return dateForUpdate;
    }

    public void setDateForUpdate(Date dateForUpdate) {
        this.dateForUpdate = dateForUpdate;
    }

    public Model.Gender getGenderForInsert() {
        return genderForInsert;
    }

    public void setGenderForInsert(Model.Gender genderForInsert) {
        this.genderForInsert = genderForInsert;
    }

    public Date getDateForInsert() {
        return dateForInsert;
    }

    public void setDateForInsert(Date dateForInsert) {
        this.dateForInsert = dateForInsert;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    @PostConstruct
    private void init() {
        try {
            methods = new Methods();
            models = methods.showAll();
            modelForUpdate = Model.builder().lastName("").firstName("").patronymic("").build();
            modelForInsert = Model.builder().lastName("").firstName("").patronymic("").build();
        } catch (Exception e) {
            msg = "Не удалось подключиться к БД:(";
            return;
        }
        msg = "Work...";
    }

    public void deletePerson(Model person){
        methods.deletePerson(person.getPersonId());
        models.remove(person);
    }

    public void sort(){
        methods.sort(models);
    }

    public void updatePerson(){
        modelForUpdate.setDateOfBirth(new java.sql.Date(dateForUpdate.getTime()));
        modelForUpdate.setGender(genderForUpdate);
        methods.updatePerson(modelForUpdate.getPersonId(), modelForUpdate);
        models.set(models.indexOf(model), modelForUpdate);
    }

    public void insertPerson(){
        if(modelForInsert.getLastName() != null && modelForInsert.getFirstName() != null) {
            modelForInsert.setDateOfBirth(new java.sql.Date(dateForInsert.getTime()));
            modelForInsert.setGender(genderForInsert);
            models.add(modelForInsert);
            methods.insertPerson(modelForInsert);
            check = modelForInsert.getLastName() + " " + modelForInsert.getFirstName() + " успешно добавлен!";
        }else{
            check = "Фамилия и Имя обязательны для заполнения!";
        }
    }

    public void prepareForInsert(){
        dateForInsert = new Date();
        modelForInsert = Model.builder().lastName("").firstName("").patronymic("").build();
        genderForInsert = genderM;
    }

    public void prepareForUpdate(Model person){
        modelForUpdate.setPersonId(person.getPersonId());
        modelForUpdate.setLastName("" + person.getLastName());
        modelForUpdate.setFirstName("" + person.getFirstName());
        modelForUpdate.setPatronymic("" + person.getPatronymic());
        modelForUpdate.setDateOfBirth(person.getDateOfBirth());
        modelForUpdate.setGender(person.getGender());
        dateForUpdate = new Date(person.getDateOfBirth().getTime());
        genderForUpdate = person.getGender();
        model = person;
    }
}