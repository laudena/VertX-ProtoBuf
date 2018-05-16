package com.example.vertbuff;

public class Location {
    private int _Location_id;
    private double _x;
    private int _Location_type;
    public Location()
    {

    }
    public Location(int Location_id, long x, int Location_type)
    {
        _Location_id = Location_id;
        _Location_type = Location_type;
        _x = x;
    }

    public int get_Location_id() {
        return _Location_id;
    }

    public void set_Location_id(int _Location_id) {
        this._Location_id = _Location_id;
    }

    public double get_x() {
        return _x;
    }

    public void set_x(double _x) {
        this._x = _x;
    }

    public int get_Location_type() {
        return _Location_type;
    }

    public void set_Location_type(int _Location_type) {
        this._Location_type = _Location_type;
    }
}
