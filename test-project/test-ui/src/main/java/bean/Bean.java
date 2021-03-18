package bean;

import dao.init.Initial;
import dao.mapper.PeopleMapper;
import lombok.Data;
import model.Model;
import org.apache.ibatis.session.SqlSession;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@ManagedBean(name="bean")
@ViewScoped
@Data
public class Bean implements Serializable {

    private final Model.Gender genderM = Model.Gender.M;
    private final Model.Gender genderW = Model.Gender.W;
    private String msg;
    private List<Model> models;
    private Model modelForUpdate;
    private Model modelForInsert;
    private Model model;
    private HelpForGender helpForInsert = new HelpForGender();
    private HelpForGender helpForUpdate = new HelpForGender();

    @PostConstruct
    private void init() {
        try (SqlSession sqlSession = Initial.SQL_SESSION_FACTORY.openSession()) {
            models = sqlSession.getMapper(PeopleMapper.class).selectAll();
            modelForUpdate = Model.builder().lastName("").firstName("").patronymic("").build();
            modelForInsert = Model.builder().lastName("").firstName("").patronymic("").build();
            helpForUpdate.setGender(Model.Gender.M);
            helpForInsert.setGender(Model.Gender.M);
        } catch (Exception e) {
            msg = "Can't connect to the Database:(";
            e.printStackTrace();
            return;
        }
        msg = "Work...";
    }

    public void deletePerson(Model person){
        try (SqlSession sqlSession = Initial.SQL_SESSION_FACTORY.openSession()) {
            sqlSession.getMapper(PeopleMapper.class).deletePerson(person.getPersonId());
        }
        models.remove(person);
    }

    public void sort(){
        models.sort(Comparator.comparing(Model::getDateOfBirth));
    }

    public void upsertPerson(Model person){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Изменения приняты! Обновите таблицу..."));
        if(person == modelForInsert){
            insertPerson();
        }else{
            updatePerson();
        }
    }

    public void updatePerson(){
        modelForUpdate.setGender(helpForUpdate.getGender());
        try (SqlSession sqlSession = Initial.SQL_SESSION_FACTORY.openSession()) {
            sqlSession.getMapper(PeopleMapper.class).updatePerson(modelForUpdate.getPersonId(), modelForUpdate);
        }
        models.set(models.indexOf(model), modelForUpdate);
    }

    public void insertPerson(){
        modelForInsert.setGender(helpForInsert.getGender());
        models.add(modelForInsert);
        try (SqlSession sqlSession = Initial.SQL_SESSION_FACTORY.openSession()) {
            sqlSession.getMapper(PeopleMapper.class).insertPerson(modelForInsert);
        }
        prepareForInsert();
    }

    public void prepareForInsert(){
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
        helpForUpdate.setGender(person.getGender());
        model = person;
    }
}