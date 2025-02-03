package org.abonehatirlatici.neoduyorum.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class StaticSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String sAbonelikAdi;
    private Double sOdemeMiktari;
    private String sOdemeBirimi;
    private String sFrequency;
    private String sLogoUrl;
    private String sKategori;

}
