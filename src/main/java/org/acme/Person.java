package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Person extends PanacheEntity {
    public String name;
    public LocalDate birth;
    @Enumerated(EnumType.STRING)
    public Status status;

    public static Person findByName(String name){
        return find("name", name).firstResult();
    }

    public static List<Person> findAlive(){
        return list("status", Status.Alive);
    }

    public static long deleteUser(String name){
        return delete("name", name);
    }
}

