package com.avaloq.ledger.service;

import com.avaloq.ledger.domain.PostingMap;
import com.avaloq.ledger.domain.Voucher;

public interface JournalPostingRuleService {
    public PostingMap evalRule(Voucher voucher);
}
