package com.avaloq.ledger.web.rest.vm;

import java.util.Date;

public class JournalPostingGenerateVM {

    private String status;

    private Long count;

    private Date refDate;

    private String chartOfAccountKey;

    public JournalPostingGenerateVM(String status, Long count, Date refDate, String chartOfAccountKey) {
        this.status = status;
        this.count = count;
        this.refDate = refDate;
        this.chartOfAccountKey = chartOfAccountKey;
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

    public Date getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    public String getChartOfAccountKey() {
        return chartOfAccountKey;
    }

    public void setChartOfAccountKey(String chartOfAccountKey) {
        this.chartOfAccountKey = chartOfAccountKey;
    }

    @Override
    public String toString() {
        return "JournalPostingGenerateVM{" +
            "status='" + status + '\'' +
            ", count=" + count +
            ", refDate=" + refDate +
            ", chartOfAccountKey='" + chartOfAccountKey + '\'' +
            '}';
    }
}
