package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.LedgerAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LedgerAccount and its DTO LedgerAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {ChartOfAccountsMapper.class})
public interface LedgerAccountMapper extends EntityMapper<LedgerAccountDTO, LedgerAccount> {

    @Mapping(source = "chartOfAccounts.id", target = "chartOfAccountsId")
    @Mapping(source = "upperAccount.id", target = "upperAccountId")
    LedgerAccountDTO toDto(LedgerAccount ledgerAccount);

    @Mapping(source = "chartOfAccountsId", target = "chartOfAccounts")
    @Mapping(source = "upperAccountId", target = "upperAccount")
    LedgerAccount toEntity(LedgerAccountDTO ledgerAccountDTO);

    default LedgerAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        LedgerAccount ledgerAccount = new LedgerAccount();
        ledgerAccount.setId(id);
        return ledgerAccount;
    }
}
