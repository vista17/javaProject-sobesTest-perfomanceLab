package ru.perfomanceLabs.tasks.task3.objects;

import java.util.ArrayList;
import java.util.List;

public class Test {
    long id;
    String title;
    String value;
    List<Test> tests;

    public Test(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public boolean addTest(Test test) {
        if (tests == null)
            tests = new ArrayList<>();
        return tests.add(test);
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", tests=" + tests +
                '}';
    }
}
