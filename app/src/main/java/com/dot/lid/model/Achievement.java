package com.dot.lid.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Achievement {
    @PrimaryKey
    @NonNull
    private String name;
    private int mainCategory;
    private int subCategory;
    private boolean hasBronze;
    private boolean hasSilver;
    private boolean hasGold;
    private boolean hasPlatinum;

    public Achievement(@NonNull String name, int mainCategory, int subCategory, boolean hasBronze, boolean hasSilver, boolean hasGold, boolean hasPlatinum) {
        this.name = name;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.hasBronze = hasBronze;
        this.hasSilver = hasSilver;
        this.hasGold = hasGold;
        this.hasPlatinum = hasPlatinum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public boolean isHasBronze() {
        return hasBronze;
    }

    public void setHasBronze(boolean hasBronze) {
        this.hasBronze = hasBronze;
    }

    public boolean isHasSilver() {
        return hasSilver;
    }

    public void setHasSilver(boolean hasSilver) {
        this.hasSilver = hasSilver;
    }

    public boolean isHasGold() {
        return hasGold;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public boolean isHasPlatinum() {
        return hasPlatinum;
    }

    public void setHasPlatinum(boolean hasPlatinum) {
        this.hasPlatinum = hasPlatinum;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "name='" + name + '\'' +
                ", mainCategory=" + mainCategory +
                ", subCategory=" + subCategory +
                ", hasBronze=" + hasBronze +
                ", hasSilver=" + hasSilver +
                ", hasGold=" + hasGold +
                ", hasPlatinum=" + hasPlatinum +
                '}';
    }
}
