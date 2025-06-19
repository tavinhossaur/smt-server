package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.lang.management.ManagementFactory;
import java.time.Duration;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.HealthCheckResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServerService {

    public HealthCheckResponseDTO healthCheck() {
        Duration duration = Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());
        return new HealthCheckResponseDTO(
            true, 
            DurationFormatUtils.formatDuration(duration.toMillis(), "d'd' H'h' m'm' s's'")
        );
    }

}
