package test;

import dao.Methods;
import model.Model;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        Model model = Model.builder().lastName("vnjf").firstName("vkerl").patronymic("hfuewfe").
                dateOfBirth(Methods.createDate(1999, 12,12)).gender(Model.Gender.М).build();
        Methods.init();
        Methods.insertPeople(model);
        Methods.deletePeople(10);
        System.out.println(model);
    }
}
