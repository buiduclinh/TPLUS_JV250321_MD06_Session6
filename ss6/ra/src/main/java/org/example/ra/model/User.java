package org.example.ra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Can't blank this")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Cant blank this")
    @Length(min = 8)
    private String password;
    @Email
    @Column(unique = true)
    @NotBlank(message = "Can't blank this")
    private String email;
}
