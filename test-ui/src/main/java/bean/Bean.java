package bean;

import dao.Methods;
import model.Model;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@ManagedBean(name="bean")
@ViewScoped
public class Bean {

    private String msg;

    private Model model;

    private Random random;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @PostConstruct
    private void init() {
        msg = "Начинаю инициализацию";
        try {
            Methods.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        random = new Random();
        int i = random.nextInt();
        msg = msg + i;
    }
}