package dao.mapper;

import org.apache.ibatis.annotations.*;
import model.Model;

import java.util.List;

public interface PeopleMapper {

    List<Model> selectAll();

    @SelectKey(statement = "SELECT LASTVAL()", keyProperty = "person.personId", before = false, resultType = Integer.class)
    void insertPerson(@Param ("person") Model person);

    void deletePerson(int id);

    void updatePerson(@Param("id") int id, @Param("person") Model person);
}
