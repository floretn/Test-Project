package bean;

import com.sun.org.apache.xpath.internal.operations.Mod;
import dao.Methods;
import model.Model;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="bean")
@ViewScoped
public class Bean implements Serializable {

    private String msg;
    private List<Model> models;
    private Model model = Model.builder().build();

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

    @PostConstruct
    private void init() {
        try {
            Methods.init();
        } catch (IOException e) {
            msg = "Не удалось подключиться к БД:(";
            return;
        }
        msg = "Успешная инициализация!";
        models = Methods.showAll();
    }

    public void deletePerson(Model person){
        Methods.deletePeople(person.getPersonId());
        models.remove(person);
    }

    public void sort(){
        Methods.sort(models);
    }

    public void updatePerson(int id, Model model){
        Methods.updatePeople(id, model);
    }
}