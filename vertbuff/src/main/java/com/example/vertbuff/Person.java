package com.example.vertbuff;

public class Person {
    private int _person_id;
    private int _age;
    private double _height;
    private String _classification;
    public Person()
    {

    }
    public Person(int person_id, int age, long height, String classification)
    {
        _person_id=  person_id;
        _age = age;
        _height = height;
        _classification =classification;
    }

    public int get_person_id() {
        return _person_id;
    }

    public void set_person_id(int _person_id) {
        this._person_id = _person_id;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public double get_height() {
        return _height;
    }

    public void set_height(double _height) {
        this._height = _height;
    }

    public String get_classification() {
        return _classification;
    }

    public void set_classification(String _classification) {
        this._classification = _classification;
    }
}
