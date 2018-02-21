package com.avaloq.ledger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A JournalPosting.
 */
@Entity
@Table(name = "journal_posting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JournalPosting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "book_date", nullable = false)
    private LocalDate bookDate;

    @NotNull
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "amount_currency")
    private Double amountCurrency;

    @Column(name = "booking_text")
    private String bookingText;

    @NotNull
    @Column(name = "global_sequence_number", nullable = false)
    private Long globalSequenceNumber;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @OneToMany(mappedBy = "journalPosting")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VoucherBooking> voucherBookings = new HashSet<>();

    @OneToMany(mappedBy = "journalPosting")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VoucherValuation> voucherValuations = new HashSet<>();

    @ManyToOne
    private LedgerAccount debitAccount;

    @ManyToOne
    private LedgerAccount creditAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public JournalPosting bookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
        return this;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public JournalPosting documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public JournalPosting amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public JournalPosting currencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
        return this;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountCurrency() {
        return amountCurrency;
    }

    public JournalPosting amountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
        return this;
    }

    public void setAmountCurrency(Double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public String getBookingText() {
        return bookingText;
    }

    public JournalPosting bookingText(String bookingText) {
        this.bookingText = bookingText;
        return this;
    }

    public void setBookingText(String bookingText) {
        this.bookingText = bookingText;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public JournalPosting globalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
        return this;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public JournalPosting legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public Set<VoucherBooking> getVoucherBookings() {
        return voucherBookings;
    }

    public JournalPosting voucherBookings(Set<VoucherBooking> voucherBookings) {
        this.voucherBookings = voucherBookings;
        return this;
    }

    public JournalPosting addVoucherBooking(VoucherBooking voucherBooking) {
        this.voucherBookings.add(voucherBooking);
        voucherBooking.setJournalPosting(this);
        return this;
    }

    public JournalPosting removeVoucherBooking(VoucherBooking voucherBooking) {
        this.voucherBookings.remove(voucherBooking);
        voucherBooking.setJournalPosting(null);
        return this;
    }

    public void setVoucherBookings(Set<VoucherBooking> voucherBookings) {
        this.voucherBookings = voucherBookings;
    }

    public Set<VoucherValuation> getVoucherValuations() {
        return voucherValuations;
    }

    public JournalPosting voucherValuations(Set<VoucherValuation> voucherValuations) {
        this.voucherValuations = voucherValuations;
        return this;
    }

    public JournalPosting addVoucherValuation(VoucherValuation voucherValuation) {
        this.voucherValuations.add(voucherValuation);
        voucherValuation.setJournalPosting(this);
        return this;
    }

    public JournalPosting removeVoucherValuation(VoucherValuation voucherValuation) {
        this.voucherValuations.remove(voucherValuation);
        voucherValuation.setJournalPosting(null);
        return this;
    }

    public void setVoucherValuations(Set<VoucherValuation> voucherValuations) {
        this.voucherValuations = voucherValuations;
    }

    public LedgerAccount getDebitAccount() {
        return debitAccount;
    }

    public JournalPosting debitAccount(LedgerAccount ledgerAccount) {
        this.debitAccount = ledgerAccount;
        return this;
    }

    public void setDebitAccount(LedgerAccount ledgerAccount) {
        this.debitAccount = ledgerAccount;
    }

    public LedgerAccount getCreditAccount() {
        return creditAccount;
    }

    public JournalPosting creditAccount(LedgerAccount ledgerAccount) {
        this.creditAccount = ledgerAccount;
        return this;
    }

    public void setCreditAccount(LedgerAccount ledgerAccount) {
        this.creditAccount = ledgerAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JournalPosting journalPosting = (JournalPosting) o;
        if (journalPosting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalPosting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalPosting{" +
            "id=" + getId() +
            ", bookDate='" + getBookDate() + "'" +
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
