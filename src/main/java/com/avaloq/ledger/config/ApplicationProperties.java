package com.avaloq.ledger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties specific to Ledger.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */

@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String voucher_posting_url;

    public String getVoucher_posting_url() {
        return voucher_posting_url;
    }

    public void setVoucher_posting_url(String voucher_posting_url) {
        this.voucher_posting_url = voucher_posting_url;
    }
}
