package com.superops.movie.theatre.auditorium;

import com.superops.movie.theatre.Theatre;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "auditorium")
public class Auditorium {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "audi_number", nullable = false)
    private Long audiNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;



    public Auditorium() {
//
    }

    public Auditorium(UUID id) {
        this.id = id;
    }

    public void theatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAudiNumber() {
        return audiNumber;
    }

    public void setAudiNumber(Long screenNumber) {
        this.audiNumber = screenNumber;
    }

}
