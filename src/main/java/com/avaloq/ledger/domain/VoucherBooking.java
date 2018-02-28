package com.avaloq.ledger.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A VoucherBooking.
 */
@Entity
@Table(name = "voucher_booking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VoucherBooking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "done_date", nullable = false)
    private LocalDate doneDate;

    @Column(name = "book_date")
    private LocalDate bookDate;

    @Column(name = "value_date")
    private LocalDate valueDate;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "quantity")
    private Double quantity;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "currency_iso", nullable = false)
    private String currencyIso;

    @Column(name = "amount_base_currency")
    private Double amountBaseCurrency;

    @Column(name = "booking_text")
    private String bookingText;

    @NotNull
    @Column(name = "global_sequence_number", nullable = false)
    private Long globalSequenceNumber;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "business_use_case")
    private String businessUseCase;

    @NotNull
    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    @Column(name = "position_keeping_id")
    private String positionKeepingId;

    @NotNull
    @Column(name = "legal_entity_id", nullable = false)
    private String legalEntityId;

    @ManyToOne
    private JournalPosting journalPosting;

    @ManyToOne
    private VoucherPosition position;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public VoucherBooking doneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public VoucherBooking bookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
        return this;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public VoucherBooking valueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
        return this;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public VoucherBooking transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public VoucherBooking quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public VoucherBooking amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public VoucherBooking currencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
        return this;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Double getAmountBaseCurrency() {
        return amountBaseCurrency;
    }

    public VoucherBooking amountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
        return this;
    }

    public void setAmountBaseCurrency(Double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
    }

    public String getBookingText() {
        return bookingText;
    }

    public VoucherBooking bookingText(String bookingText) {
        this.bookingText = bookingText;
        return this;
    }

    public void setBookingText(String bookingText) {
        this.bookingText = bookingText;
    }

    public Long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public VoucherBooking globalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
        return this;
    }

    public void setGlobalSequenceNumber(Long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public VoucherBooking transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventId() {
        return eventId;
    }

    public VoucherBooking eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public VoucherBooking transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBusinessUseCase() {
        return businessUseCase;
    }

    public VoucherBooking businessUseCase(String businessUseCase) {
        this.businessUseCase = businessUseCase;
        return this;
    }

    public void setBusinessUseCase(String businessUseCase) {
        this.businessUseCase = businessUseCase;
    }

    public String getBookingId() {
        return bookingId;
    }

    public VoucherBooking bookingId(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPositionKeepingId() {
        return positionKeepingId;
    }

    public VoucherBooking positionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
        return this;
    }

    public void setPositionKeepingId(String positionKeepingId) {
        this.positionKeepingId = positionKeepingId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public VoucherBooking legalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
        return this;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public JournalPosting getJournalPosting() {
        return journalPosting;
    }

    public VoucherBooking journalPosting(JournalPosting journalPosting) {
        this.journalPosting = journalPosting;
        return this;
    }

    public void setJournalPosting(JournalPosting journalPosting) {
        this.journalPosting = journalPosting;
    }

    public VoucherPosition getPosition() {
        return position;
    }

    public VoucherBooking position(VoucherPosition voucherPosition) {
        this.position = voucherPosition;
        return this;
    }

    public void setPosition(VoucherPosition voucherPosition) {
        this.position = voucherPosition;
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
        VoucherBooking voucherBooking = (VoucherBooking) o;
        if (voucherBooking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voucherBooking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VoucherBooking{" +
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
