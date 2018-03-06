package com.avaloq.ledger.web.rest.vm;

import java.time.LocalDate;

public class JournalPostingGenerateVM {

    private String status;

    private Long count;

    private LocalDate refDate;

    private String chartOfAccountsKey;

    public JournalPostingGenerateVM(String status, Long count, LocalDate refDate, String chartOfAccountsKey) {
        this.status = status;
        this.count = count;
        this.refDate = refDate;
        this.chartOfAccountsKey = chartOfAccountsKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LocalDate getRefDate() {
        return refDate;
    }

    public void setRefDate(LocalDate refDate) {
        this.refDate = refDate;
    }

    public String getChartOfAccountKey() {
        return chartOfAccountsKey;
    }

    public void setChartOfAccountKey(String chartOfAccountKey) {
        this.chartOfAccountsKey = chartOfAccountKey;
    }

    @Override
    public String toString() {
        return "JournalPostingGenerateVM{" +
            "status='" + status + '\'' +
            ", count=" + count +
            ", refDate=" + refDate +
            ", chartOfAccountKey='" + chartOfAccountsKey + '\'' +
            '}';
    }
}
