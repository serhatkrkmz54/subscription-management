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
                    .sOdemeMiktari(12.99)
                    .sOdemeBirimi("USD")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Eğlence")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Youtube Premium")
                    .sOdemeMiktari(11.99)
                    .sOdemeBirimi("USD")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Eğlence")
                    .build());
            staticSubscriptionRepo.save(StaticSubscription.builder()
                    .sAbonelikAdi("Amazon Prime")
                    .sOdemeMiktari(14.99)
                    .sOdemeBirimi("USD")
                    .sFrequency("AYLIK")
                    .sLogoUrl("")
                    .sKategori("Eğlence")
                    .build());
        }
    }
}
