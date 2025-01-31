package org.abonehatirlatici.neoduyorum.config;

import lombok.RequiredArgsConstructor;
import org.abonehatirlatici.neoduyorum.entity.StaticSubscription;
import org.abonehatirlatici.neoduyorum.repo.StaticSubscriptionRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StaticSubscriptionRepo staticSubscriptionRepo;

    @Override
    public void run(String... args) {
        if (staticSubscriptionRepo.count() == 0) {
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Netflix")
                    .sOdemeMiktari(149.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Temel Paket")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Netflix")
                    .sOdemeMiktari(229.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Standart Paket")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Netflix")
                    .sOdemeMiktari(299.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Premium Paket")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Youtube Premium")
                    .sOdemeMiktari(79.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Bireysel Üyelik")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Youtube Premium")
                    .sOdemeMiktari(159.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Aile Planı")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Youtube Premium")
                    .sOdemeMiktari(52.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Öğrenci Paketi")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Amazon Prime")
                    .sOdemeMiktari(39.00)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Üyelik")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Spotify Premium")
                    .sOdemeMiktari(59.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Bireysel")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Spotify Premium")
                    .sOdemeMiktari(32.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Öğrenci")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Spotify Premium")
                    .sOdemeMiktari(79.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Duo")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Spotify Premium")
                    .sOdemeMiktari(99.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Aile")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Apple Music")
                    .sOdemeMiktari(19.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Öğrenci")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Apple Music")
                    .sOdemeMiktari(39.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Bireysel")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Apple Music")
                    .sOdemeMiktari(59.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Aile")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Disney+")
                    .sOdemeMiktari(164.90)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Aylık Üyelik")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Disney+")
                    .sOdemeMiktari(1649.00)
                    .sOdemeBirimi("TRY")
                    .sFrequency("YILLIK")
                    .sLogoUrl("")
                    .sKategori("Yıllık Üyelik")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Disney+")
                    .sOdemeMiktari(134.99)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Ekstra Üye")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Tabii")
                    .sOdemeMiktari(99.00)
                    .sOdemeBirimi("TRY")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Premium")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("ChatGPT")
                    .sOdemeMiktari(20.00)
                    .sOdemeBirimi("USD")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Plus")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("ChatGPT")
                    .sOdemeMiktari(200.00)
                    .sOdemeBirimi("USD")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Pro")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("ChatGPT")
                    .sOdemeMiktari(25.00)
                    .sOdemeBirimi("USD")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Team")
                    .build());
        }
    }
}
