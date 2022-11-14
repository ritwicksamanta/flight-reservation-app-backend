package com.rx.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.rx.security.shared.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(unique = true,nullable = false)
    @NotBlank(message = "Email is Required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[a-zA-Z0-9@$!%*?&]{10,}$",
        message = "Password must contain one upper case, one lower case, one digit, one special character and length should be 10")
    private String password;


    @NotBlank(message = "Name field cannot be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]*$",message = "Name can contain letter and spaces only")
    private String firstName;

    @NotBlank(message = "Name field cannot be empty")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Name can contain letter and spaces only")
    private String lastName;

    @NotBlank(message = "Mobile Number cannot be blank")
    @Pattern(regexp = "^[\\d]{10,11}$",message = "Phone Number must be at least of 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address field cannot be empty")
    private String address;


    private String roles; //ROLE_USER,ROLE_ADMIN
    @PrePersist
    public void prePersist(){
        if(roles==null)
            this.roles=UserRoles.ROLE_USER.name();
    }
}

/**
 * firstName!:string; //contains only letter and spaces
 *   lastName:string; //contains only letter and spaces
 *   email:string; //valid email format && user id
 *   password:string; //one lowercase,uppercase,digit,special character, length -> 10
 *   address:string;
 *   phoneNumber:string;
 */