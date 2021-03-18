package model;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class Model implements Serializable {

    private int personId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Date dateOfBirth;
    private Gender gender;

    public enum Gender {
        M,
        W
    }
}
