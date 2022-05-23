package ba.unsa.etf.ppis.e_ticket_booking_app.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Concert {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID concertID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String musician;

    @Column(nullable = false)
    private String place;

    @Column(nullable = true)
    private LocalDateTime concertDate;

    @Column(nullable = true)
    private Integer numberOfTickets;

    @OneToOne
    @JoinColumn(name = "concert_file_id", nullable = true)
    private File concertFile;

    @OneToMany(mappedBy = "concertID")
    private Set<Ticket> concertIDTickets;

    @CreatedDate
    @Column(nullable = true, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = true)
    private OffsetDateTime lastUpdated;

}
