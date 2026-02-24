package exercises;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;


/**
 * This set of exercises covers simple stream pipelines,
 * including intermediate operations and basic collectors.
 *
 * Some of these exercises use a BufferedReader variable
 * named "reader" that the test has set up for you.
 */
public class D_SimpleStreams {
    /**
     * Given a list of words, create an output list that contains
     * only the odd-length words, converted to upper case.
     */
    @Test
    public void d1_upcaseOddLengthWords() {
        List<String> input = new ArrayList<>();
        Collections.addAll(input, "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        List<String> result = input
        		.stream().filter( e -> (e.length() &1) == 1)
        		.map(String::toUpperCase)
        		.collect(Collectors.toList());

        List<String> resultantList = new ArrayList<>();
        Collections.addAll(resultantList, "BRAVO", "CHARLIE", "DELTA", "FOXTROT");

        assertEquals(resultantList, result);
    }

    /**
     * Take the third through fifth words of the list, extract the
     * second letter from each, and join them, separated by commas,
     * into a single string. Watch for off-by-one errors.
     */
    @Test
    public void d2_joinStreamRange() {
        List<String> input = new ArrayList<>();
        Collections.addAll(input, "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");

        String result = input.subList(2, 5).stream().map(e -> e.charAt(1))
        		.map(e -> e.toString())
        		.collect(Collectors.joining(","));

        assertEquals("h,e,c", result);
    }

    /**
     * Count the number of lines in the text file. (Remember to
     * use the BufferedReader named "reader" that has already been
     * opened for you.)
     *
     * @throws IOException
     */
    @Test
    public void d3_countLinesInFile() throws IOException {
        long count = 0; 
        
        Path path = Paths.get("SonnetI.txt");
        count = Files.lines(path).count();
        
        assertEquals(14, count);
    }
   
    /**
     * Find the length of the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void d4_findLengthOfLongestLine() throws IOException {
        int longestLength = 0; 

        Path path = Paths.get("SonnetI.txt");
        longestLength = Files.lines(path).mapToInt(String::length).max().getAsInt(); //Sure that it will not throw exception
        			
        assertEquals(53, longestLength);
    }


    /**
     * Find the longest line in the text file.
     *
     * @throws IOException
     */
    @Test
    public void d5_findLongestLine() throws IOException {
        String longest = null;
        Path path = Paths.get("SonnetI.txt");
        longest = Files.lines(path)
        		.max(Comparator.comparingInt(String::length))
        		.get();    //Sure that it will not throw exception
        assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
    }

    /**
     * Select the longest words from the input list. That is, select the words
     * whose lengths are equal to the maximum word length.
     */
    @Test
    public void d6_selectLongestWords() {
        List<String> input = new ArrayList<>();
        Collections.addAll(input, "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        int maxLength = input.stream().mapToInt(String::length)
        		.max().orElse(-1);

        List<String> result = input.stream().filter(e -> e.length() == maxLength)
        			.collect(Collectors.toList());
        
        List<String> resultant = new ArrayList<>();
        Collections.addAll(resultant,"charlie", "foxtrot");
        assertEquals(resultant, result);
    }

    /**
     * Select the list of words from the input list whose length is greater than
     * the word's position in the list (starting from zero) .
     */
    @Test
    public void d7_selectByLengthAndPosition() {
        List<String> input = new ArrayList<>();
        Collections.addAll(input, "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel");

        List<String> result = IntStream.range(0, input.size())
        		.filter(i -> input.get(i).length() > i)
        		.mapToObj(i -> input.get(i))
        		.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        List<String> resultant = new ArrayList<>();
        Collections.addAll(resultant, "alfa", "bravo", "charlie", "delta", "foxtrot");
        assertEquals(resultant, result);
    }

}
