package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.config.ApplicationProperties;
import com.avaloq.ledger.domain.PostingMap;
import com.avaloq.ledger.domain.Voucher;
import com.avaloq.ledger.service.JournalPostingRuleService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class JournalPostingRuleServiceImpl implements JournalPostingRuleService {

    private final ApplicationProperties applicationProperties;

    public JournalPostingRuleServiceImpl(
        ApplicationProperties applicationProperties
    ) {
        this.applicationProperties = applicationProperties;
    }

    public PostingMap evalRule(Voucher voucher) {

        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();

        PostingMap postingMap = restTemplate.getForObject(
            applicationProperties.getVoucher_posting_url(), PostingMap.class);

      return postingMap;
    }
}
