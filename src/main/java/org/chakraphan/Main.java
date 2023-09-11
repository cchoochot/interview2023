package org.chakraphan;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // [ BEGIN jmh ]

    private static final String JMH_TEST_STRING = "aaaabcccccaa1110011111111kkkkkkkkkkkk22222zzzzzz99999111222333";

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

    // [ END jmh]

    public static void main(String[] args) throws IOException {
        final Main app = new Main();
        app.execute();

        org.openjdk.jmh.Main.main(args);
    }

    /**
     * Verify the inputs with the expected results
     */
    private void execute() {
        final String passedLogPrefix = "Passed testing string ";

        final String testString1 = "ÿaaaabbbcccccaaüüüüüüüüzzzzzz";
        final String expectedResult1 = "1ÿ2a3b4a5c6z8ü";
        assertTest(expectedResult1, testString1, this::processV1, "processV1 failed test1");
        assertTest(expectedResult1, testString1, this::processV2, "processV2 failed test1");
        logger.info(() -> passedLogPrefix + testString1);


        final String testString2 = "aaaabcccccaa111001111111122222zzzzzz";
        final String expectedResult2 = "1b202a314a525c6z81";
        assertTest(expectedResult2, testString2, this::processV1, "processV1 failed test2");
        assertTest(expectedResult2, testString2, this::processV2, "processV2 failed test2");
        logger.info(() -> passedLogPrefix + testString2);


        // test input with some characters over 10 in a row
        final String testString3 = "aaaabcccccaa1110011111111kkkkkkkkkkkk22222zzzzzz";
        final String expectedResult3 = "1b202a314a525c6z8112k";
        assertTest(expectedResult3, testString3, this::processV1, "processV1 failed test3");
        assertTest(expectedResult3, testString3, this::processV2, "processV2 failed test3");
        logger.info(() -> passedLogPrefix + testString3);
    }

    private void assertTest(String expected, String input, UnaryOperator<String> func, String failedMessage) {
        final String result = func.apply(input);
        if (!expected.equals(result)) {
            throw new AssertionError(failedMessage);
        }
    }

    /**
     * This is version that was submitted to the interviewer.
     * There's no time to review or improve, but it's just work.
     *
     * @param input input string
     * @return the processed output string
     */
    private String processV1(String input) {
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

        return convertResultListToString(list);
    }

    /**
     * Sort the result list based on entry count and character.
     *
     * @param list the list of character-count entries to be processed
     * @return the result string
     */
    private String convertResultListToString(List<AbstractMap.SimpleImmutableEntry<Character, Integer>> list) {
        final Comparator<AbstractMap.SimpleImmutableEntry<Character, Integer>> comparatorCount = Comparator.comparingInt(AbstractMap.SimpleImmutableEntry::getValue);
        return list.stream()
                .sorted(comparatorCount.thenComparing(Entry.comparingByKey())) // sort by count first
                .map(entry -> "" + entry.getValue() + entry.getKey()) // then sort by character
                .collect(Collectors.joining());
    }

    private String processV2(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }

        // set initial ArrayList size to be the same as input string for the worst case
        final int length = input.length();
        List<AbstractMap.SimpleImmutableEntry<Character, Integer>> list = new ArrayList<>(length);

        char ch = input.charAt(0);
        int count = 1;
        for (int i = 1; i < length; i++) {
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

        return convertResultListToStringV2(list);
    }

    private String convertResultListToStringV2(List<AbstractMap.SimpleImmutableEntry<Character, Integer>> list) {
        return list.stream()
                .sorted(Entry.<Character, Integer>comparingByValue().thenComparing(Entry.comparingByKey())) // update comparable usage
                .map(entry -> "" + entry.getValue() + entry.getKey())
                .collect(Collectors.joining());
    }
}