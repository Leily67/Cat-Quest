package com.catquest.enums;

import com.catquest.entities.Item;
import com.catquest.entities.items.Armor;
import com.catquest.entities.items.Boots;
import com.catquest.entities.items.Sword;

public enum ItemEnum {
    ARMOR(new Armor()),
    BOOTS(new Boots()),
    SWORD(new Sword());

    private final Item item;

    ItemEnum(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }

    public static Item get(String name) {
        for (ItemEnum i : ItemEnum.values()) {
            if (i.name().equalsIgnoreCase(name)) {
                return i.getItem();
            }
        }
        return null;
    }
}
