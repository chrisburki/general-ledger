package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.JournalPostingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JournalPosting and its DTO JournalPostingDTO.
 */
@Mapper(componentModel = "spring", uses = {LedgerAccountMapper.class})
public interface JournalPostingMapper extends EntityMapper<JournalPostingDTO, JournalPosting> {

    @Mapping(source = "debitAccount.id", target = "debitAccountId")
    @Mapping(source = "creditAccount.id", target = "creditAccountId")
    JournalPostingDTO toDto(JournalPosting journalPosting);

    @Mapping(target = "voucherBookings", ignore = true)
    @Mapping(target = "voucherValuations", ignore = true)
    @Mapping(source = "debitAccountId", target = "debitAccount")
    @Mapping(source = "creditAccountId", target = "creditAccount")
    JournalPosting toEntity(JournalPostingDTO journalPostingDTO);

    default JournalPosting fromId(Long id) {
        if (id == null) {
            return null;
        }
        JournalPosting journalPosting = new JournalPosting();
        journalPosting.setId(id);
        return journalPosting;
    }
}
