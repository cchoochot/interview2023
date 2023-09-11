# Interview Problem 2023

A small Java program I created for an interview session.

There are improved versions implemented after I reviewed my code later, including inserting JMH logic to measure the performance

## Description

The purpose is to translate the following input string into the expected result string.

| input | expected |
|-------|----------|
| ÿaaaabbbcccccaaüüüüüüüüzzzzzz     | 1ÿ2a3b4a5c6z8ü        |
|aaaabcccccaa111001111111122222zzzzzz|1b202a314a525c6z81|
|aaaabcccccaa1110011111111kkkkkkkkkkkk22222zzzzzz|1b202a314a525c6z8112k|
_Note: not actual inputs from the interviewer_

## Getting Started

### Executing program

* Execute the program to validate the logic and produce a JMH report

## Current Performance Report Summary

```
# JMH version: 1.36
# VM version: JDK 20.0.2, OpenJDK 64-Bit Server VM, 20.0.2+9-78
# Hardware: Intel® Core™ i5-3470, DDR3 16GB
# OS: Debian 12 64-bit

Result "org.chakraphan.Main.benchProcessV1":
  611593.834 ±(99.9%) 12874.466 ops/s [Average]
  (min, avg, max) = (592353.791, 611593.834, 644412.818), stdev = 17187.044
  CI (99.9%): [598719.368, 624468.300] (assumes normal distribution)

Result "org.chakraphan.Main.benchProcessV2":
  648557.930 ±(99.9%) 35300.770 ops/s [Average]
  (min, avg, max) = (588551.702, 648557.930, 697004.412), stdev = 47125.518
  CI (99.9%): [613257.161, 683858.700] (assumes normal distribution)

Benchmark             Mode  Cnt       Score       Error  Units
Main.benchProcessV1  thrpt   25  611593.834 ± 12874.466  ops/s
Main.benchProcessV2  thrpt   25  648557.930 ± 35300.770  ops/s
```

### Comment
* The previous V2/V3 cannot handle the case when some character count in 2 or more digits
* New slightly change approach has been implemented instead and receive not that much improvement in terms of performance
* ~~The performance has been significantly improved from V1 to V2, because of the simpler sorting logic~~
* ~~V2 and V3 share the similar performance result~~

## Acknowledgments
* [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh)
