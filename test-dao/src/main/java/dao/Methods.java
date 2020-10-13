package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import model.Model;

public class Methods {

    private static SqlSession sqlSession;
    private static PeopleMapper mapper;

    public static void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        //System.out.println("Hello Session!");
        sqlSessionFactory.getConfiguration().addMapper(PeopleMapper.class);
        mapper = session.getMapper(PeopleMapper.class);
        sqlSession = session;

    }

    public static List<Model> showAll(){
        return sqlSession.selectList("selectAll");
    }

    public static Model selectPerson(int id){
        return mapper.selectPerson(id);
    }

    public static void insertPerson(Model person){
        person.setPersonId(mapper.insertPerson(person));
    }

    public static void deletePerson(int id){
        mapper.deletePerson(id);
    }

    public static void updatePerson(int id, Model person){
        mapper.updatePerson(id, person);
    }

    public static Date createDate(int year, int month, int day) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return new java.sql.Date(df.parse(month + "-" + day + "-" + year).getTime());
    }

    public static void sort(List<Model> list){
        list.sort(Comparator.comparing(Model::getDateOfBirth));
    }

    /*
    public static void main(String[] args){
        try{
            init();
            Model person = selectPerson(1);
            System.out.println(person);
            List<Model> list = showAll();
            System.out.println(list);
            sort(list);
            System.out.println(list);
            //System.out.println(createDate(1999, 11, 6));
            person = Model.builder().personId(0).lastName("Софронов").firstName("Иван").patronymic("Евгеньевич").
                    dateOfBirth(createDate(1999, 11, 6)).gender(Model.Gender.М).build();
            insertPerson(person);
            System.out.println(person);
            deletePerson(4);
            updatePerson(3, person);
        }catch(IOException | ParseException ex){
            System.err.println("Что-то пошло не так");
        }
    }

     */
}