package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;

    @OneToOne(optional=false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="CONTACT_ID", unique=true, nullable=false, updatable=false)
    private Contact contact;

    private Timestamp date;

    public User() {
        this.date = new Timestamp(new Date().getTime());
    }

    public User(int id, String name, Contact contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.date = new Timestamp(new Date().getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
