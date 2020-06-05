package jp.kaiz.atsassistmod.network;

public enum PacketCause {
    PLAYER(0), ENTITY_ID(1);

    public int id;

    PacketCause(int id) {
        this.id = id;
    }
}
