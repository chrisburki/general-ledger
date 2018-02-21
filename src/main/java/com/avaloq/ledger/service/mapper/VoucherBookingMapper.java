package com.avaloq.ledger.service.mapper;

import com.avaloq.ledger.domain.*;
import com.avaloq.ledger.service.dto.VoucherBookingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherBooking and its DTO VoucherBookingDTO.
 */
@Mapper(componentModel = "spring", uses = {JournalPostingMapper.class, VoucherPositionMapper.class})
public interface VoucherBookingMapper extends EntityMapper<VoucherBookingDTO, VoucherBooking> {

    @Mapping(source = "journalPosting.id", target = "journalPostingId")
    @Mapping(source = "position.id", target = "positionId")
    VoucherBookingDTO toDto(VoucherBooking voucherBooking);

    @Mapping(source = "journalPostingId", target = "journalPosting")
    @Mapping(source = "positionId", target = "position")
    VoucherBooking toEntity(VoucherBookingDTO voucherBookingDTO);

    default VoucherBooking fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoucherBooking voucherBooking = new VoucherBooking();
        voucherBooking.setId(id);
        return voucherBooking;
    }
}
