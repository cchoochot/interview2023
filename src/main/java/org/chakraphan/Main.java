package org.chakraphan;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
public class Main {

    // [ BEGIN jmh ]

    private static final String JMH_TEST_STRING = "ÿaaaabbbcccccaaüüüüüüüüzzzzzz";

    @Setup
    public static void jmhSetup() {
        // does nothing
    }

    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void benchProcessV1() {
        processV1(JMH_TEST_STRING);
    }

    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void benchProcessV2() {
        processV2(JMH_TEST_STRING);
    }

    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void benchProcessV3() {
        processV3(JMH_TEST_STRING);
    }

    // [ END jmh]

    public static void main(String[] args) throws IOException {
        execute();

        org.openjdk.jmh.Main.main(args);
    }

    /**
     * Verify the inputs with the expected results
     */
    private static void execute() {
        final String testString1 = JMH_TEST_STRING;
        final String testResult1 = "1ÿ2a3b4a5c6z8ü";

        if (!testResult1.equals(processV1(testString1))) throw new AssertionError("processV1 failed test1");
        if (!testResult1.equals(processV2(testString1))) throw new AssertionError("processV2 failed test1");
        if (!testResult1.equals(processV3(testString1))) throw new AssertionError("processV3 failed test1");


        String testString2 = "aaaabcccccaa111001111111122222zzzzzz";
        String testResult2 = "1b202a314a525c6z81";

        if (!testResult2.equals(processV1(testString2)))
            throw new AssertionError("processV1 failed test2: " + processV1(testString2));
        if (!testResult2.equals(processV2(testString2)))
            throw new AssertionError("processV2 failed test2: " + processV2(testString2));
        if (!testResult2.equals(processV3(testString2)))
            throw new AssertionError("processV3 failed test2: " + processV3(testString2));
    }

    /**
     * This is version that was submitted to the interviewer.
     * There's no time to review or improve, but it's just work.
     *
     * @param input input string
     * @return the processed output string
     */
    private static String processV1(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }

        List<AbstractMap.SimpleImmutableEntry<Character, Integer>> list = new ArrayList<>();

        char ch = input.charAt(0);
        int count = 1;
        for (int i = 1, length = input.length(); i < length; i++) {
            char thisChar = input.charAt(i);
            if (ch == thisChar) {
                // increase counter
                count++;
            } else {
                list.add(new AbstractMap.SimpleImmutableEntry<>(ch, count));

                // reset counter
                count = 1;
                ch = thisChar;
            }
        }

        list.add(new AbstractMap.SimpleImmutableEntry<>(ch, count));

        // 1b2a4a5c6z
        return convertResultListToString(list);
    }

    /**
     * Sort the result list based on entry count and character.
     *
     * @param list the list of character-count entries to be processed
     * @return the result string
     */
    private static String convertResultListToString(List<AbstractMap.SimpleImmutableEntry<Character, Integer>> list) {
        final Comparator<AbstractMap.SimpleImmutableEntry<Character, Integer>> comparatorCount = Comparator.comparingInt(AbstractMap.SimpleImmutableEntry::getValue);
        return list.stream()
                .sorted(comparatorCount.thenComparing(Map.Entry.comparingByKey())) // sort by count first
                .map(entry -> "" + entry.getValue() + entry.getKey()) // then sort by character
                .collect(Collectors.joining());
    }

    /**
     * This is an improved version after I got back to review my logic later.
     * It uses a bit simpler logic on sorting so the performance should be better.
     *
     * @param input the input string
     * @return the result string
     */
    private static String processV2(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }

        List<String> list = new ArrayList<>();

        char ch = input.charAt(0);
        int count = 1;

        for (int i = 1, length = input.length(); i < length; i++) {
            char thisChar = input.charAt(i);
            if (ch == thisChar) {
                // increase counter
                count++;
            } else {
                list.add("" + count + ch);

                // reset counter
                count = 1;

                ch = thisChar;
            }
        }


        list.add("" + count + ch);
        return list.stream().sorted().collect(Collectors.joining());
    }

    /**
     * This is on top of v2 but implemented with code point.
     *
     * @param input the input string
     * @return the result string
     */
    private static String processV3(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }

        final List<String> list = new ArrayList<>();

        int codePoint = input.codePointAt(0);
        int count = 1;

        int idx;
        int currentCodePoint;
        int length = input.length();
        for (idx = 1; idx < length; idx++) {
            currentCodePoint = input.codePointAt(idx);

            if (codePoint == currentCodePoint) {
                count++;
                continue;
            }

            list.add("" + count + input.charAt(idx - 1));

            count = 1;
            codePoint = currentCodePoint;
        }

        list.add("" + count + input.charAt(idx - 1));

        return list.stream().sorted()
                .collect(Collectors.joining());
    }
}