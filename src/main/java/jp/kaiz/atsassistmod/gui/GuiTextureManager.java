package jp.kaiz.atsassistmod.gui;

import net.minecraft.util.ResourceLocation;

public enum GuiTextureManager {
    IFTTTBaseLayer("textures/gui/iftttbaselayer.png"),
    IFTTTContainer0("textures/gui/iftttcontainer0.png"),
    IFTTTContainer1("textures/gui/iftttcontainer1.png"),
    ThisBaseLayer("textures/gui/thisbaselayer.png"),
    Exit("textures/gui/exit.png"),
    ExitRed("textures/gui/exitred.png"),
    AddButton("textures/gui/addbutton.png"),
    EditButton("textures/gui/editbutton.png"),
    DeleteButton("textures/gui/deletebutton.png"),
    ;

    public ResourceLocation texture;

    GuiTextureManager(String path) {
        this.texture = new ResourceLocation("atsassistmod", path);
    }
}
