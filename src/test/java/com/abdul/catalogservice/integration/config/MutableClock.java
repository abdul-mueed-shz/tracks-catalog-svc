package com.abdul.catalogservice.integration.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public final class MutableClock extends Clock {
    private Instant current;

    public MutableClock(Instant current) {
        this.current = current;
    }

    public void setInstant(String instant) {
        current = Instant.parse(instant);
    }

    public void advanceTo(String instant) {
        setInstant(instant);
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.of("UTC");
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override
    public Instant instant() {
        return current;
    }
}
