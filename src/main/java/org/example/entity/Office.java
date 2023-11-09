package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Office {

    private int id;
    private List<List<Integer>> listRoutesSignor = new ArrayList<>();
    private List<List<Integer>> listRoutesMiddle = new ArrayList<>();
    private List<List<Integer>> listRoutesJunior = new ArrayList<>();

    private int[] listDisFromOfficeToHighTask;
    private int[] listDisFromOfficeToMiddleTask;
    private int[] listDisFromOfficeToLowTask;


    public Office(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<List<Integer>> getListRoutesSignor() {
        return listRoutesSignor;
    }

    public void setListRoutesSignor(List<List<Integer>> listRoutesSignor) {
        this.listRoutesSignor = listRoutesSignor;
    }

    public List<List<Integer>> getListRoutesMiddle() {
        return listRoutesMiddle;
    }

    public void setListRoutesMiddle(List<List<Integer>> listRoutesMiddle) {
        this.listRoutesMiddle = listRoutesMiddle;
    }

    public List<List<Integer>> getListRoutesJunior() {
        return listRoutesJunior;
    }

    public void setListRoutesJunior(List<List<Integer>> listRoutesJunior) {
        this.listRoutesJunior = listRoutesJunior;
    }

    public int[] getListDisFromOfficeToHighTask() {
        return listDisFromOfficeToHighTask;
    }

    public void addListDisFromOfficeToHighTask(int[] distance) {
        this.listDisFromOfficeToHighTask = distance;
    }

    public int[] getListDisFromOfficeToMediumTask() {
        return listDisFromOfficeToMiddleTask;
    }

    public void addListDisFromOfficeToMiddleTask(int[] distance) {
        this.listDisFromOfficeToMiddleTask = distance;
    }

    public int[] getListDisFromOfficeToLowTask() {
        return listDisFromOfficeToLowTask;
    }

    public void addListDisFromOfficeToLowTask(int[] distance) {
        this.listDisFromOfficeToLowTask = distance;
    }
}
