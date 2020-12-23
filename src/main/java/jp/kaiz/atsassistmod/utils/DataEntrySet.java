package jp.kaiz.atsassistmod.utils;

import jp.ngt.rtm.modelpack.state.DataEntry;

public class DataEntrySet {
    public final String key;
    public final DataEntry<?> value;

    public DataEntrySet(String key, DataEntry<?> value) {
        this.key = key;
        this.value = value;
    }
}
