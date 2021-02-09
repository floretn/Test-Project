package bean;

import dao.methods.Methods;
import model.Model;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name="bean")
@ViewScoped
public class Bean implements Serializable {

    private final Model.Gender genderM = Model.Gender.М;
    private final Model.Gender genderW = Model.Gender.Ж;
    private String msg;
    private List<Model> models;
    private Model modelForUpdate;
    private Model modelForInsert;
    private Model model;
    private Methods methods;
    private HelpForDateAndGender helpForInsert = new HelpForDateAndGender();
    private HelpForDateAndGender helpForUpdate = new HelpForDateAndGender();

    public Model.Gender getGenderM() {
        return genderM;
    }

    public Model.Gender getGenderW() {
        return genderW;
    }

    public String getMsg() {
        return msg;
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

    public HelpForDateAndGender getHelpForInsert() {
        return helpForInsert;
    }

    public void setHelpForInsert(HelpForDateAndGender helpForInsert) {
        this.helpForInsert = helpForInsert;
    }

    public HelpForDateAndGender getHelpForUpdate() {
        return helpForUpdate;
    }

    public void setHelpForUpdate(HelpForDateAndGender helpForUpdate) {
        this.helpForUpdate = helpForUpdate;
    }

    @PostConstruct
    private void init() {
        try {
            methods = new Methods();
            models = methods.showAll();
            modelForUpdate = Model.builder().lastName("").firstName("").patronymic("").build();
            modelForInsert = Model.builder().lastName("").firstName("").patronymic("").build();
            helpForUpdate.setGender(Model.Gender.М);
            helpForInsert.setGender(Model.Gender.М);
            helpForUpdate.setDate(new Date());
            helpForInsert.setDate(new Date());
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

    public void upsertPerson(Model person){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Изменения приняты! \nОбновите таблицу..."));
        if(person == modelForInsert){
            insertPerson();
        }else{
            updatePerson();
        }
    }

    public void updatePerson(){
        modelForUpdate.setDateOfBirth(new java.sql.Date(helpForUpdate.getDate().getTime()));
        modelForUpdate.setGender(helpForUpdate.getGender());
        methods.updatePerson(modelForUpdate.getPersonId(), modelForUpdate);
        models.set(models.indexOf(model), modelForUpdate);
    }

    public void insertPerson(){
        modelForInsert.setDateOfBirth(new java.sql.Date(helpForInsert.getDate().getTime()));
        modelForInsert.setGender(helpForInsert.getGender());
        models.add(modelForInsert);
        methods.insertPerson(modelForInsert);
        prepareForInsert();
    }

    public void prepareForInsert(){
        helpForInsert.setDate(new Date());
        helpForInsert.setGender(helpForInsert.getGenderM());
        modelForInsert = Model.builder().lastName("").firstName("").patronymic("").build();
    }

    public void prepareForUpdate(Model person){
        modelForUpdate.setPersonId(person.getPersonId());
        modelForUpdate.setLastName("" + person.getLastName());
        modelForUpdate.setFirstName("" + person.getFirstName());
        modelForUpdate.setPatronymic("" + person.getPatronymic());
        modelForUpdate.setDateOfBirth(new java.sql.Date(person.getDateOfBirth().getTime()));
        modelForUpdate.setGender(person.getGender());
        helpForUpdate.setDate(new Date(person.getDateOfBirth().getTime()));
        helpForUpdate.setGender(person.getGender());
        model = person;
    }
}