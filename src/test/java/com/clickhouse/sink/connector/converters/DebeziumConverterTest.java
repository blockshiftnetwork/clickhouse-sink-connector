package com.clickhouse.sink.connector.converters;

import com.altinity.clickhouse.sink.connector.converters.DebeziumConverter;
import com.altinity.clickhouse.sink.connector.metadata.DataTypeRange;
import com.clickhouse.client.data.BinaryStreamUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static com.altinity.clickhouse.sink.connector.metadata.DataTypeRange.CLICKHOUSE_MAX_SUPPORTED_DATE32;
import static com.altinity.clickhouse.sink.connector.metadata.DataTypeRange.CLICKHOUSE_MIN_SUPPORTED_DATE32;

public class DebeziumConverterTest {

    @Test
    public void testMicroTimeConverter() {

        Object timeInMicroSeconds = 3723000000L;
        String formattedTime = DebeziumConverter.MicroTimeConverter.convert(timeInMicroSeconds);

        //Assert.assertTrue(formattedTime.equalsIgnoreCase("20:02:03"));
    }

    @Test
    public void testTimestampConverter() {

        Object timestampEpoch = 1640995260000L;
        String formattedTimestamp = DebeziumConverter.TimestampConverter.convert(timestampEpoch, false);

        //Assert.assertTrue(formattedTimestamp.equalsIgnoreCase("2021-12-31 19:01:00"));
    }

    @Test
    public void testTimestampConverterMinRange() {

        Object timestampEpoch = -2166681362000L;
        String formattedTimestamp = DebeziumConverter.TimestampConverter.convert(timestampEpoch, false);

        Assert.assertTrue(formattedTimestamp.equalsIgnoreCase("1925-01-01 00:00:00"));
    }

    @Test
    public void testTimestampConverterMaxRange() {

        Object timestampEpoch = 4807440238000L;
        String formattedTimestamp = DebeziumConverter.TimestampConverter.convert(timestampEpoch, false);

        System.out.println("DateTimeRagnge MIN " + DataTypeRange.CLICKHOUSE_MIN_SUPPORTED_DATETIME.toString());

        System.out.println("DATETIME 64" + BinaryStreamUtils.DATETIME64_MIN);
        Assert.assertTrue(formattedTimestamp.equalsIgnoreCase("2122-05-05 16:03:58"));
    }

    @Test
    public void testDateConverter() {

        Integer date = 3652;
        java.sql.Date formattedDate = DebeziumConverter.DateConverter.convert(date);

        //Assert.assertTrue(formattedDate.toString().equalsIgnoreCase("1979-12-31"));
    }

    @Test
    public void testDateConverterMinRange() {

        Integer date = -144450000;
        java.sql.Date formattedDate = DebeziumConverter.DateConverter.convert(date);

        Assert.assertTrue(formattedDate.toString().equalsIgnoreCase(CLICKHOUSE_MIN_SUPPORTED_DATE32.toString()));
    }
    @Test
    public void testDateConverterMaxRange() {

        Integer date = 450000;
        java.sql.Date formattedDate = DebeziumConverter.DateConverter.convert(date);

        Assert.assertTrue(formattedDate.toString().equalsIgnoreCase(CLICKHOUSE_MAX_SUPPORTED_DATE32.toString()));
    }

    @Test
    public void testDateConverterWithinRange() {

        // Epoch (days)
        Long date = 1444571000L;
        java.sql.Date formattedDate = DebeziumConverter.DateConverter.convert(date);
        Assert.assertTrue(formattedDate.toString().equalsIgnoreCase(""));
    }

    @Test
    public void testZonedTimestampConverter() {

        String formattedTimestamp = DebeziumConverter.ZonedTimestampConverter.convert("2021-12-31T19:01:00Z");

        Assert.assertTrue(formattedTimestamp.equalsIgnoreCase("2021-12-31 19:01:00"));
    }

    @Test
    public void testMicroTimestampConverter() {

        String convertedString = DebeziumConverter.MicroTimestampConverter.convert(-248313600000000L);
        Assert.assertTrue(convertedString.equalsIgnoreCase("1962-02-18 00:00:00"));
    }
}