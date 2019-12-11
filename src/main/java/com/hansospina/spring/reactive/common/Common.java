package com.hansospina.spring.reactive.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class Common {

  private static DecimalFormat df = new DecimalFormat();
  private static ObjectMapper mapper = new ObjectMapper();
  private static SimpleDateFormat dateFormat = new SimpleDateFormat();

  static {
    df.setRoundingMode(RoundingMode.CEILING);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
  }

  public static String getUUID() {
    return UUID.randomUUID().toString();
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
      .map(FeatureDescriptor::getName)
      .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
      .toArray(String[]::new);
  }

  public static void copyProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
  }

  public static String convertObjectToJSON(Object object) throws JsonProcessingException {
    return mapper.writeValueAsString(object);
  }

  public static Map<String, Object> convertJSONToMap(String json) throws IOException {
    return mapper.readValue(json, new TypeReference<Map<String, Object>>() {
    });
  }

  public static Map<String, Object> convertObjectToMap(Object object) throws IOException {
    return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {
    });
  }

  public static ObjectNode convertJSONToObjectNode(String json) throws IOException {
    return mapper.readTree(json).deepCopy();
  }

  public static String convertMapToJSON(Map<String, Object> map) throws JsonProcessingException {
    return convertObjectToJSON(map);
  }

  public static <T> T convertMapToPOJO(Map<String, Object> map, Class<T> type) {
    return mapper.convertValue(map, type);
  }

  public static <T> T convertJSONToPOJO(String json, Class<T> type) throws IOException {
    return convertMapToPOJO(Common.convertJSONToMap(json), type);
  }

  public static <T> List<T> convertMapArrayToPOJOArray(List<Map<String, Object>> objects, Class<T> type) {
    return mapper.convertValue(objects, TypeFactory.defaultInstance().constructCollectionType(List.class, type));
  }

  public static <T> List<T> convertJSONArrayToPOJOArray(String json, Class<T> type) throws IOException {
    return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, type));
  }

}
