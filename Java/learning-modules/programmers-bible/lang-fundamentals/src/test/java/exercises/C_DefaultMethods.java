//package exercises;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.junit.Test;
//
///**
// * This set of exercises covers new default methods on
// * the Collections and related APIs.
// */
//public class C_DefaultMethods {
//
//    /**
//     * Given a list of StringBuilders, modify each StringBuilder
//     * in-place by appending the string "new" to each one.
//     */
//    @Test
//    public void c01_appendNew() {
//        List<StringBuilder> sbList = new ArrayList<StringBuilder>();
//		Collections.addAll( sbList,
//            new StringBuilder("alfa"),
//            new StringBuilder("bravo"),
//            new StringBuilder("charlie"));
//
//		sbList.forEach( e -> e.append("new"));
//
//        List<StringBuilder> newList = new ArrayList<StringBuilder>();
//        		Collections.addAll( newList,
//                    new StringBuilder("alfanew"),
//                    new StringBuilder("bravonew"),
//                    new StringBuilder("charlienew"));
//        boolean equalityCheck = equalityCheck(sbList, newList);
//        assertTrue(equalityCheck);
//    }
//
//    private boolean equalityCheck(List<StringBuilder> list1, List<StringBuilder> list2) {
//    	if(null == list1 || null == list2)
//    		return false;
//    	else if( list1.size() != list2.size())
//    		return false;
//    	else {
//    		for(int i = 0; i < list1.size(); ++i) {
//    			if(!list1.get(i).toString().equals((list2.get(i).toString()))) {
//    				return false;
//    			}
//    		}
//    	}
//    	return true;
//   }
//    /**
//     * Remove the words that have odd lengths from the list.
//     */
//    @Test
//    public void c02_removeOddLengthWords() {
//        List<String> list = new ArrayList<>(Arrays.asList(
//            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot"));
//
//        list.removeIf(e -> e.length()%2 != 0);
//        List<String> result = new ArrayList<>();
//        Collections.addAll(result, "alfa", "echo");
//        assertEquals(result, list);
//    }
//
//    /**
//     * Replace every word in the list with its upper case equivalent.
//     */
//    @Test
//    public void c03_upcaseAllWords() {
//        List<String> list = Arrays.asList(
//            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");
//
//        list.replaceAll(String::toUpperCase);;
//
//        List<String> result = new ArrayList<>();
//        Collections.addAll(result, "ALFA", "BRAVO", "CHARLIE", "DELTA", "ECHO", "FOXTROT");
//
//        assertEquals(result, list);
//    }
//
//    /**
//     * Given a map whose keys are Integers and whose values are StringBuilders,
//     * append to each StringBuilder the string representation of its corresponding
//     * Integer key. This should mutate each StringBuilder value in-place.
//     */
//    @Test
//    public void c04_appendToMapValues() {
//        Map<Integer, StringBuilder> map = new TreeMap<>();
//        map.put(1, new StringBuilder("alfa"));
//        map.put(2, new StringBuilder("bravo"));
//        map.put(3, new StringBuilder("charlie"));
//
//        map.forEach((k, v) -> v.append(k));
//
//        assertEquals(3, map.size());
//        assertTrue(map.values().stream().allMatch(x -> x instanceof StringBuilder));
//        assertEquals("alfa1",    map.get(1).toString());
//        assertEquals("bravo2",   map.get(2).toString());
//        assertEquals("charlie3", map.get(3).toString());
//    }
//
//    /**
//     * Given a map whose keys are Integers and whose values are Strings,
//     * append to each String the string representation of its corresponding
//     * Integer key.
//     */
//    @Test
//    public void c05_replaceMapValues() {
//        Map<Integer, String> map = new TreeMap<>();
//        map.put(1, "alfa");
//        map.put(2, "bravo");
//        map.put(3, "charlie");
//
//        map.replaceAll((k, v) -> v+k);
//
//        Map<Integer, String> resultantmap = new TreeMap<>();
//        resultantmap.put(1, "alfa1");
//        resultantmap.put(2, "bravo2");
//        resultantmap.put(3, "charlie3");
//
//        assertEquals(resultantmap, map);
//    }
//
//    /**
//     * Given a list of words, populate a map whose keys are the lengths of
//     * each word, and whose values are list of words with that length.
//     */
//    @Test
//    public void c06_mapOfListOfStringsByLength() {
//        List<String> list = new ArrayList<>();
//        Collections.addAll( list,
//            "aardvark", "bison", "capybara",
//            "alligator", "bushbaby", "chimpanzee",
//            "avocet", "bustard", "capuchin");
//        Map<Integer, List<String>> result = new TreeMap<>();
//
//        list.forEach(s -> result.computeIfAbsent(s.length(), key -> new ArrayList<String>()).add(s));
//
//        List<String> length_8 = new ArrayList<>();
//        Collections.addAll(length_8, "aardvark", "capybara", "bushbaby", "capuchin");
//
//        Map<Integer, List<String>> stringLenMap = new TreeMap<>();
//        stringLenMap.put(5, Collections.singletonList("bison"));
//        stringLenMap.put(6, Collections.singletonList("avocet"));
//        stringLenMap.put(7, Collections.singletonList("bustard"));
//        stringLenMap.put(8, length_8);
//        stringLenMap.put(9, Collections.singletonList("alligator"));
//        stringLenMap.put(10, Collections.singletonList("chimpanzee"));
//        assertEquals(stringLenMap, result);
//    }
//
//    /**
//     * Given a list of words, populate a map whose keys are the initial characters of
//     * each word, and whose values are the concatenation of the words with that
//     * initial character. When concatenating the words, they should be
//     * separated by a colon (':').
//     */
//    @Test
//    public void c07_mapOfStringByInitialCharacter() {
//    	List<String> list = new ArrayList<>();
//        Collections.addAll( list,
//            "aardvark", "bison", "capybara",
//            "alligator", "bushbaby", "chimpanzee",
//            "avocet", "bustard", "capuchin");
//        Map<Character, String> result = new TreeMap<>();
//
//        list.forEach(s -> result.merge(s.charAt(0), s, (s1, s2) -> String.join(":", s1 , s2)));
//
//        Map<Character, String> resultMap = new TreeMap<>();
//        resultMap.put('a', "aardvark:alligator:avocet");
//        resultMap.put('b', "bison:bushbaby:bustard");
//        resultMap.put('c', "capybara:chimpanzee:capuchin");
//        assertEquals(resultMap, result);
//    }
//
//    /**
//     * For some reason the provided map doesn't have mappings for all the keys. This
//     * is a problem, because if we call get() on a key that isn't present, it returns
//     * null, and we need to add checks to protect against NullPointerException.
//     * Write code to ensure that all missing keys are mapped to the empty string.
//     */
//    @Test
//    public void c08_mapWithMissingValues() {
//        List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
//        Map<String, String> map = new HashMap<>();
//        map.put("a", "alfa");
//        map.put("b", "bravo");
//        map.put("c", "charlie");
//        map.put("d", "delta");
//
//        keys.forEach( key -> map.putIfAbsent(key, ""));
//
//        Map<String, String> resultmap = new HashMap<>();
//        resultmap.put("a", "alfa");
//        resultmap.put("b", "bravo");
//        resultmap.put("c", "charlie");
//        resultmap.put("d", "delta");
//        resultmap.put("e", "");
//        resultmap.put("f", "");
//        resultmap.put("g", "");
//
//        assertEquals(resultmap, map);
//    }
//
//
//    /**
//     * In the previous example, we added map entries that had a default value.
//     * We've now determined that's incorrect, and we want to undo that. This
//     * time, we want to remove the entry if the value is the empty string.
//     */
//    @Test
//    public void c09_mapRemoveEntriesWithEmptyValues() {
//        List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
//        Map<String, String> map = new HashMap<>();
//        map.put("a", "alfa");
//        map.put("b", "bravo");
//        map.put("c", "charlie");
//        map.put("d", "delta");
//        map.put("e", "");
//        map.put("f", "");
//        map.put("g", "");
//
//        keys.forEach(key -> map.remove(key, ""));
//
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("a", "alfa");
//        resultMap.put("b", "bravo");
//        resultMap.put("c", "charlie");
//        resultMap.put("d", "delta");
//
//        assertEquals(resultMap, map);
//    }
//
//    /**
//     * We need another way to deal with the problem of the previous example.
//     * Instead of removing entries whose value is the empty string, we want
//     * to replace the empty-string values with a value that's the key itself.
//     * Write the code to do that.
//     */
//    @Test
//    public void c10_mapReplaceEmptyValues() {
//    	 List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
//         Map<String, String> map = new HashMap<>();
//         map.put("a", "alfa");
//         map.put("b", "bravo");
//         map.put("c", "charlie");
//         map.put("d", "delta");
//         map.put("e", "");
//         map.put("f", "");
//         map.put("g", "");
//
//         keys.forEach(key -> map.replace(key, "", key));
//
//         Map<String, String> resultMap = new HashMap<>();
//         resultMap.put("a", "alfa");
//         resultMap.put("b", "bravo");
//         resultMap.put("c", "charlie");
//         resultMap.put("d", "delta");
//         resultMap.put("e", "e");
//         resultMap.put("f", "f");
//         resultMap.put("g", "g");
//         assertEquals(resultMap, map);
//    }
//
//    /**
//     * We are still dealing with a map with missing entries. For entries that
//     * are present, we want to convert the value to upper case; and for keys
//     * that are not present, we want to add an entry where the value is the
//     * same as the key.
//     */
//    @Test
//    public void c11_computeWithMissingEntries() {
//        List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
//        Map<String, String> map = new HashMap<>();
//        map.put("a", "alfa");
//        map.put("b", "bravo");
//        map.put("c", "charlie");
//        map.put("d", "delta");
//
//        keys.forEach(s -> map.compute(s,
//        			(k, v) -> {
//    					return null == v? k:v.toUpperCase();
//        			}
//        	));
//
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("a", "ALFA");
//        resultMap.put("b", "BRAVO");
//        resultMap.put("c", "CHARLIE");
//        resultMap.put("d", "DELTA");
//        resultMap.put("e", "e");
//        resultMap.put("f", "f");
//        resultMap.put("g", "g");
//
//        assertEquals(resultMap, map);
//    }
//
//    /**
//     * The map now has several entries, some with valid values, and some
//     * with values that are the empty string. This time, we want to convert
//     * the non-empty values to upper case, but we want to remove the entries
//     * for which the values are the empty string.
//     */
//    @Test
//    public void c12_computeAndRemoveSomeEntries() {
//        List<String> keys = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
//        Map<String, String> map = new HashMap<>();
//        map.put("a", "alfa");
//        map.put("b", "bravo");
//        map.put("c", "charlie");
//        map.put("d", "delta");
//        map.put("e", "");
//        map.put("f", "");
//        map.put("g", "");
//
//        keys.forEach(key -> map.compute(key, (k,v) -> {
//        			return v.isEmpty() ? null : v.toUpperCase();
//        		}));
//
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("a", "ALFA");
//        resultMap.put("b", "BRAVO");
//        resultMap.put("c", "CHARLIE");
//        resultMap.put("d", "DELTA");
//
//        assertEquals(resultMap, map);
//    }
//
//}