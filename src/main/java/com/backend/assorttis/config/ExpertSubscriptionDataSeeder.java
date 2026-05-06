package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertSubscription;
import com.backend.assorttis.entities.Plan;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertSubscriptionRepository;
import com.backend.assorttis.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(10)
public class ExpertSubscriptionDataSeeder implements CommandLineRunner {

    private final ExpertSubscriptionRepository expertSubscriptionRepository;
    private final ExpertRepository expertRepository;
    private final PlanRepository planRepository;
    private final AtomicLong subscriptionIdCounter = new AtomicLong(100);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (expertSubscriptionRepository.count() == 0) {
            Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
            Plan plan = planRepository.findAll().stream().findFirst().orElse(null);

            if (expert != null) {
                ExpertSubscription subscription = new ExpertSubscription()
                        .setId(subscriptionIdCounter.getAndIncrement())
                        .setExpert(expert)
                        .setPlan(plan)
                        .setStartDate(LocalDate.now().minusDays(10))
                        .setEndDate(LocalDate.now().plusDays(355))
                        .setStatus("active")
                        .setAutoRenew(true)
                        .setTrialEndsAt(Instant.now().plusSeconds(86400 * 4)) // Trial ends in 4 days
                        .setCancelledAt(null)
                        .setNextBillingDate(LocalDate.now().plusDays(20))
                        .setCreatedAt(Instant.now());

                expertSubscriptionRepository.save(subscription);
            }
        }
    }
}
