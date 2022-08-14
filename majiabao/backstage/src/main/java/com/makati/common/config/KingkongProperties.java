package com.makati.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "kingkong")
@Slf4j
@Data
@Component
public class KingkongProperties {
    private String url;
    private String url58;
    private String zjur;
    private String imgPrefix;
}
