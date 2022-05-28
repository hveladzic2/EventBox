package ba.unsa.etf.ppis.e_ticket_booking_app.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Rezervacija {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = true)
    private Boolean eTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concertid_id", nullable = false)
    private Concert concertID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid_id", nullable = false)
    private User userID;

    @OneToOne
    @JoinColumn(name = "rezervacija_file_id", nullable = true)
    private File rezervacijaFile;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}

