package jp.kaiz.atsassistmod.ifttt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IFTTTUtil {
	private final static Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

	public static byte[] convertClass(IFTTTContainer ifcb) {
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

	public static IFTTTContainer convertClass(byte[] bytes) {
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
