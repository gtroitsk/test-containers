package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Person extends PanacheEntity {
    public String name;
    public LocalDate birth;
    public Status status;

    public static Uni<Person> findByName(String name){
        return find("name", name).firstResult();
    }

    public static Uni<List<Person>> findAlive(){
        return list("status", Status.Alive);
    }

    public static long deleteUser(String name){
        return delete("name", name);
    }
}

