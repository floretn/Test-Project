package dao.mapper;

import org.apache.ibatis.annotations.*;
import model.Model;

public interface PeopleMapper {

    @Select("SELECT * FROM people.people WHERE id_person = #{id}")
    Model selectPerson(int id);

    @Insert("insert into people.people (last_name, first_name, patronymic, date_of_birth, gender) " +
            "values (#{person.lastName}, #{person.firstName}, #{person.patronymic}, #{person.dateOfBirth}," +
            " #{person.gender, jdbcType=VARCHAR}::Gender); commit;")
    @SelectKey(statement = "SELECT LASTVAL()", keyProperty = "person.personId", before = false, resultType = Integer.class)
    void insertPerson(@Param ("person") Model person);

    @Delete("delete from people.people where id_person = #{id}; commit;")
    void deletePerson(int id);

    @Update("update people.people set last_name = #{person.lastName}, first_name = #{person.firstName}, " +
            "patronymic = #{person.patronymic}, date_of_birth = #{person.dateOfBirth}, " +
            "gender = #{person.gender, jdbcType=VARCHAR}::Gender where id_person = #{id}; commit;")
    void updatePerson(@Param("id") int id, @Param("person") Model person);
}
