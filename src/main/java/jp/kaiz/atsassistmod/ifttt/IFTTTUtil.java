package jp.kaiz.atsassistmod.ifttt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class IFTTTUtil {
    private final static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

    public static byte[] convertClassSafe(IFTTTContainer ifcb) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(ifcb);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IFTTTContainer convertClassSafe(byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(bytes, IFTTTContainer.class);
        } catch (IOException e) {
            debugDeserializeFailure(objectMapper, bytes, e);
            e.printStackTrace();
        }
        return null;
    }

    private static void debugDeserializeFailure(ObjectMapper objectMapper, byte[] bytes, IOException exception) {
        System.out.println("[ATSAssist][IFTTT-DEBUG] deserialize failed: " + exception.getClass().getName() + " / " + exception.getMessage());
        if (exception instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) exception;
            System.out.println("[ATSAssist][IFTTT-DEBUG] path: " + jsonMappingException.getPathReference());
        }
        System.out.println("[ATSAssist][IFTTT-DEBUG] payload bytes: " + (bytes == null ? "null" : bytes.length));
        if (bytes == null) {
            return;
        }

        try {
            String json = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("[ATSAssist][IFTTT-DEBUG] payload json: " + json);
            JsonNode root = objectMapper.readTree(bytes);
            if (root != null && root.has("@class")) {
                String className = root.get("@class").asText();
                System.out.println("[ATSAssist][IFTTT-DEBUG] @class: " + className);
                try {
                    Class<?> targetClass = Class.forName(className);
                    debugClassInfo(objectMapper, targetClass);
                } catch (ClassNotFoundException classNotFoundException) {
                    System.out.println("[ATSAssist][IFTTT-DEBUG] @class not found: " + classNotFoundException.getMessage());
                }
            }
        } catch (IOException parseException) {
            System.out.println("[ATSAssist][IFTTT-DEBUG] could not parse payload as json: " + parseException.getMessage());
        }

        debugClassInfo(objectMapper, IFTTTContainer.class);
    }

    private static void debugClassInfo(ObjectMapper objectMapper, Class<?> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            AtomicReference<Throwable> cause = new AtomicReference<>();
            boolean canDeserialize = objectMapper.canDeserialize(javaType, cause);
            System.out.println("[ATSAssist][IFTTT-DEBUG] canDeserialize " + clazz.getName() + ": " + canDeserialize);
            if (cause.get() != null) {
                System.out.println("[ATSAssist][IFTTT-DEBUG] canDeserialize cause for " + clazz.getName() + ":");
                cause.get().printStackTrace();
            }

            BeanDescription description = objectMapper.getDeserializationConfig().introspect(javaType);
            for (BeanPropertyDefinition property : description.findProperties()) {
                String typeName;
                try {
                    typeName = property.getPrimaryType().toCanonical();
                } catch (Exception ignored) {
                    typeName = "<unknown>";
                }
                System.out.println(
                        "[ATSAssist][IFTTT-DEBUG] property "
                                + clazz.getSimpleName()
                                + "."
                                + property.getName()
                                + " type="
                                + typeName
                                + " getter="
                                + property.hasGetter()
                                + " setter="
                                + property.hasSetter()
                                + " field="
                                + property.hasField());
            }
        } catch (Exception e) {
            System.out.println("[ATSAssist][IFTTT-DEBUG] debugClassInfo failed for " + clazz.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static byte[] convertClass(IFTTTContainer ifcb) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ifcb);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static IFTTTContainer convertClass(byte[] bytes) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (IFTTTContainer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String listToString(List<IFTTTContainer> ifcbList) {
        return GSON.toJson(ifcbList.stream().map(IFTTTUtil::convertClass).filter(Objects::nonNull).collect(Collectors.toList()), new TypeToken<List<byte[]>>() {
        }.getType());
    }

    public static List<IFTTTContainer> listFromJson(String json) {
        List<byte[]> iftttJsonList = GSON.fromJson(json, new TypeToken<List<byte[]>>() {
        }.getType());
        return iftttJsonList.stream().map(IFTTTUtil::convertClass).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
