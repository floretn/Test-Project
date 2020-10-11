package dao;

import org.apache.ibatis.annotations.*;
import model.Model;

public interface PeopleMapper {

    @Select("SELECT * FROM people.people WHERE id_person = #{id}")
    Model selectPeople(int id);

    /*
    @Insert("insert into people.people (last_name, first_name, patronymic, date_of_birth, gender) " +
            "values (#{person.lastName}, #{person.firstName}, #{person.patronymic}, #{person.dateOfBirth}," +
            " #{person.gender, jdbcType=VARCHAR}::Gender)" +
            " returning id_person;")
    int insertPeople(@Param ("person") Model person);

     */

    @Select("select * from insert_in_table(#{person.lastName}, #{person.firstName}, #{person.patronymic}, #{person.dateOfBirth}, " +
            "#{person.gender, jdbcType=VARCHAR}::Gender); commit;")
    int insertPeople(@Param ("person") Model person);

    @Delete("delete from people.people where id_person = #{id}; commit;")
    void deletePeople(int id);

    @Update("update people.people set last_name = #{person.lastName}, first_name = #{person.firstName}, " +
            "patronymic = #{person.patronymic}, date_of_birth = #{person.dateOfBirth}, " +
            "gender = #{person.gender, jdbcType=VARCHAR}::Gender where id_person = #{id}; commit;")
    void updatePerson(@Param("id") int id, @Param("person") Model person);
}
