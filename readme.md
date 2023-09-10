# Interview Problem 2023

A small Java program I created for an interview session.

There are improved versions implemented after I reviewed my code later, including inserting JMH logic to measure the performance

## Description

The purpose is to translate the following input string into the expected result string.

| input | expected |
|-------|----------|
| ÿaaaabbbcccccaaüüüüüüüüzzzzzz     | 1ÿ2a3b4a5c6z8ü        |
|aaaabcccccaa111001111111122222zzzzzz|1b202a314a525c6z81|
* not actual inputs from the interviewer

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
  1248911.188 ±(99.9%) 59214.245 ops/s [Average]
  (min, avg, max) = (1112683.471, 1248911.188, 1328218.527), stdev = 79049.323
  CI (99.9%): [1189696.943, 1308125.433] (assumes normal distribution)
  
Result "org.chakraphan.Main.benchProcessV2":
  2189911.237 ±(99.9%) 15254.003 ops/s [Average]
  (min, avg, max) = (2150772.722, 2189911.237, 2217124.401), stdev = 20363.658
  CI (99.9%): [2174657.233, 2205165.240] (assumes normal distribution)

Result "org.chakraphan.Main.benchProcessV3":
  2199803.241 ±(99.9%) 42582.934 ops/s [Average]
  (min, avg, max) = (2100390.657, 2199803.241, 2282709.765), stdev = 56846.999
  CI (99.9%): [2157220.307, 2242386.175] (assumes normal distribution)

Benchmark             Mode  Cnt        Score       Error  Units
Main.benchProcessV1  thrpt   25  1248911.188 ± 59214.245  ops/s
Main.benchProcessV2  thrpt   25  2189911.237 ± 15254.003  ops/s
Main.benchProcessV3  thrpt   25  2199803.241 ± 42582.934  ops/s
```

### Comment
* The performance has been significantly improved from V1 to V2, because of the simpler sorting logic
* V2 and V3 share the similar performance result

## Acknowledgments
* [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh)
