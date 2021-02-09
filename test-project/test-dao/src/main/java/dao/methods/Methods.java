package dao.methods;

import dao.mapper.PeopleMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import model.Model;

public class Methods implements Serializable {

    private static volatile SqlSession sqlSession;
    private static volatile PeopleMapper mapper;

    public Methods() throws IOException {
        if(mapper == null) {
            init();
        }
    }

    private static void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        //System.out.println("Hello Session!");
        sqlSessionFactory.getConfiguration().addMapper(PeopleMapper.class);
        mapper = session.getMapper(PeopleMapper.class);
        sqlSession = session;

    }

    public List<Model> showAll(){
        return sqlSession.selectList("selectAll");
    }

    public Model selectPerson(int id){
        return mapper.selectPerson(id);
    }

    public void insertPerson(Model person){
        mapper.insertPerson(person);
    }

    public void deletePerson(int id){
        mapper.deletePerson(id);
    }

    public void updatePerson(int id, Model person){
        mapper.updatePerson(id, person);
    }

    public Date createDate(int year, int month, int day) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return new java.sql.Date(df.parse(month + "-" + day + "-" + year).getTime());
    }

    public void sort(List<Model> list){
        list.sort(Comparator.comparing(Model::getDateOfBirth));
    }

    /*
    public static void main(String[] args){
        try{
            Methods methods = new Methods();
            Model person = methods.selectPerson(1);
            System.out.println(person);
            List<Model> list = methods.showAll();
            System.out.println(list);
            methods.sort(list);
            System.out.println(list);
            //System.out.println(createDate(1999, 11, 6));
            person = Model.builder().lastName("Софронов").firstName("Иван").patronymic("Евгеньевич").
                    dateOfBirth(methods.createDate(1999, 11, 6)).gender(model.Model.Gender.М).build();
            methods.insertPerson(person);
            System.out.println(person);
            methods.deletePerson(4);
            methods.updatePerson(3, person);
        }catch(IOException | ParseException ex){
            System.err.println("Что-то пошло не так");
        }
    }
    */
}