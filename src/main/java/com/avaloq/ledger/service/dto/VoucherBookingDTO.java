package com.avaloq.ledger.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the VoucherBooking entity.
 */
public class VoucherBookingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate doneDate;

    private LocalDate bookDate;

    private LocalDate valueDate;

    private LocalDate transactionDate;

    private Double quantity;

    @NotNull
    private Double amount;

    @NotNull
    private String currencyIso;

    private Double amountBaseCurrency;

    private String bookingText;

    @NotNull
    private Long globalSequenceNumber;

    private String transactionId;

    private String eventId;

    private String transactionType;

    private String businessUseCase;

    @NotNull
    private String bookingId;

    private String positionKeepingId;

    @NotNull
    private String legalEntityId;

    private Long journalPostingId;

    private Long positionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountBaseCurrency() {
        return amountBaseCurrency;
    }

    public void setAmountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
    }

    public String getBookingText() {
        return bookingText;
    }

    public void setBookingText(String bookingText) {
        this.bookingText = bookingText;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBusinessUseCase() {
        return businessUseCase;
    }

    public void setBusinessUseCase(String businessUseCase) {
        this.businessUseCase = businessUseCase;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPositionKeepingId() {
        return positionKeepingId;
    }

    public void setPositionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Long getJournalPostingId() {
        return journalPostingId;
    }

    public void setJournalPostingId(Long journalPostingId) {
        this.journalPostingId = journalPostingId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long voucherPositionId) {
        this.positionId = voucherPositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VoucherBookingDTO voucherBookingDTO = (VoucherBookingDTO) o;
        if(voucherBookingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherBookingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherBookingDTO{" +
            "id=" + getId() +
            ", doneDate='" + getDoneDate() + "'" +
            ", bookDate='" + getBookDate() + "'" +
            ", valueDate='" + getValueDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", quantity=" + getQuantity() +
            ", amount=" + getAmount() +
            ", currencyIso='" + getCurrencyIso() + "'" +
            ", amountBaseCurrency=" + getAmountBaseCurrency() +
            ", bookingText='" + getBookingText() + "'" +
            ", globalSequenceNumber=" + getGlobalSequenceNumber() +
            ", transactionId='" + getTransactionId() + "'" +
            ", eventId='" + getEventId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", businessUseCase='" + getBusinessUseCase() + "'" +
            ", bookingId='" + getBookingId() + "'" +
            ", positionKeepingId='" + getPositionKeepingId() + "'" +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
