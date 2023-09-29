package jp.kaiz.atsassistmod.ifttt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IFTTTUtil {
    private final static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

    public static byte[] convertClassSafe(IFTTTContainer ifcb) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(ifcb);
            return compress(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IFTTTContainer convertClassSafe(byte[] bytes) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] decompressed = decompress(bytes);
            return objectMapper.readValue(decompressed, IFTTTContainer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] compress(byte[] bytes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(bytes);
        }
        return out.toByteArray();
    }

    private static byte[] decompress(byte[] compressed) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }
        return out.toByteArray();
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
