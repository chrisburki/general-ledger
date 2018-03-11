package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.enumeration.BalanceDateType;
import org.springframework.stereotype.Service;

@Service
public class DateTypeMapper {
    public BalanceDateType toDateType(String dateType) {
        switch (dateType) {
            case "DONE":
                return BalanceDateType.DONE;
            case "BOOK":
                return BalanceDateType.BOOK;
            case "VALUE":
                return BalanceDateType.VALUE;
            case "TRANSACTION":
                return BalanceDateType.TRANSACTION;
            default:
                return null;
        }
    }
}
