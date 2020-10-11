package model;

import lombok.*;

import java.sql.Date;

@Data
@Builder
public class Model {

    private int personId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Date dateOfBirth;
    private Gender gender;

    public enum Gender {
        М,
        Ж
    }
}
