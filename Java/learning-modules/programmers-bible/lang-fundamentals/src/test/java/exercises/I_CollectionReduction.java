//package exercises;
//
//import static org.junit.Assert.assertEquals;
//import static reduction.City.Athens;
//import static reduction.City.London;
//import static reduction.City.Tulsa;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.junit.Test;
//
//import reduction.City;
//import reduction.Person;
//
//public class I_CollectionReduction {
//
//   static Person jon = new Person(Tulsa, "Jon", 42);
//   static Person amy = new Person(Athens, "Amy", 21);
//   static Person bill = new Person(London, "Bill", 33);
//   static Person eric = new Person(Athens, "Eric", 33);
//
//   static List<Person> people = Arrays.asList(jon, amy, bill, eric);
//
//   @Test
//   public void it_groups_people_by_age() {
//      Map<Integer, List<Person>> peopleByAge = people.stream()
//    		  .collect(Collectors.groupingBy(Person::age));
//
//      List<Person> result42 = Collections.singletonList(jon);
//      List<Person> result21 = Collections.singletonList(amy);
//      List<Person> result33 = Arrays.asList(bill, eric);
//      Map<Integer, List<Person>> result = new HashMap<>();
//      result.put(42,  result42);
//      result.put(21, result21);
//      result.put(33, result33);
//      assertEquals(result, peopleByAge);
//   }
//
//   @Test
//   public void it_groups_names_by_age() {
//      Map<Integer, List<String>> nameByAge = people.stream()
//    		  .collect(Collectors.groupingBy(Person::age,
//    				  Collectors.mapping(Person::firstName, Collectors.toList())));
//
//      List<String> result42 = Collections.singletonList("Jon");
//      List<String> result21 = Collections.singletonList("Amy");
//      List<String> result33 = Arrays.asList("Bill", "Eric");
//
//      Map<Integer, List<String>> result = new HashMap<>();
//      result.put(42,  result42);
//      result.put(21, result21);
//      result.put(33, result33);
//
//      assertEquals(result, nameByAge);
//   }
//
//   @Test
//   public void it_computes_population_by_age() {
//      Map<Integer, Long> populationByAge = people.stream()
//    		  .collect(Collectors.groupingBy(Person::age, Collectors.counting()));
//
//      Map<Integer, Long> result = new HashMap<>();
//      result.put(42, 1L);
//      result.put(21, 1L);
//      result.put(33, 2L);
//
//      assertEquals(result, populationByAge);
//   }
//
//   @Test
//   public void it_computes_average_age_by_city() {
//      Map<City, Double> averageAgeByCity = people.stream()
//    		  .collect(Collectors.groupingBy(Person::city, Collectors.averagingDouble(Person::age)));
//
//      Map<City, Double> result = new HashMap<>();
//      result.put(London, 33.0);
//      result.put(Athens, 27.0);
//      result.put(Tulsa, 42.0);
//
//      assertEquals(result, averageAgeByCity);
//   }
//
//   @Test
//   public void it_gives_cities_with_more_than_one_inhabitant() {
//     Map<City, Long> collected = people.stream()
//    		  .collect(Collectors.groupingBy(Person::city, Collectors.counting()));
//
//     Set<City> populousCity = collected.entrySet().stream().filter( e -> e.getValue() > 1 )
//    		 .map(e -> e.getKey())
//    		 .collect(Collectors.toSet());
//     Set<City> result = new HashSet<>();
//     result.add(Athens);
//     assertEquals(result, populousCity);
//   }
//
//   @Test
//   public void it_computes_most_popular_age() {
//       Map<Integer, Long> collect = people.stream()
//    		  .collect(Collectors.groupingBy(Person::age, Collectors.counting()));
//
//       int mostPopularAge = collect.entrySet().stream()
//    		   .max(Map.Entry.comparingByValue())
//    		   .map(Map.Entry::getKey)
//    		   .orElse(-1);
//
//      assertEquals(33, mostPopularAge);
//   }
//
//}
