package cz.malanius.escqrs.escqrsdemo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "contacts")
    private Set<ContactEntity> contacts;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "adresses")
    private Set<AddressEntity> addresses;

}

