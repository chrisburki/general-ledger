package com.avaloq.ledger.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.avaloq.ledger.domain.enumeration.BalanceDateType;

/**
 * A DTO for the JournalPosting entity.
 */
public class JournalPostingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate bookDate;

    @NotNull
    private BalanceDateType bookDateType;

    @NotNull
    private String documentNumber;

    @NotNull
    private Double amount;

    private String currencyIso;

    private Double amountCurrency;

    private String bookingText;

    @NotNull
    private Long globalSequenceNumber;

    @NotNull
    private String legalEntityId;

    private Long debitAccountId;

    private Long creditAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public BalanceDateType getBookDateType() {
        return bookDateType;
    }

    public void setBookDateType(BalanceDateType bookDateType) {
        this.bookDateType = bookDateType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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

    public Double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
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

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Long ledgerAccountId) {
        this.debitAccountId = ledgerAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Long ledgerAccountId) {
        this.creditAccountId = ledgerAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JournalPostingDTO journalPostingDTO = (JournalPostingDTO) o;
        if(journalPostingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalPostingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalPostingDTO{" +
            "id=" + getId() +
            ", bookDate='" + getBookDate() + "'" +
            ", bookDateType='" + getBookDateType() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", amount=" + getAmount() +
            ", currencyIso='" + getCurrencyIso() + "'" +
            ", amountCurrency=" + getAmountCurrency() +
            ", bookingText='" + getBookingText() + "'" +
            ", globalSequenceNumber=" + getGlobalSequenceNumber() +
            ", legalEntityId='" + getLegalEntityId() + "'" +
            "}";
    }
}
