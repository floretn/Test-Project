package dao.model;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@Builder
public class Model implements Serializable {

    private int personId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Date dateOfBirth;
    private model.Model.Gender gender;

    public enum Gender {
        М,
        Ж
    }
}
