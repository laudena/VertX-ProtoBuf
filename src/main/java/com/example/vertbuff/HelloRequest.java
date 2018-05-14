package com.example.vertbuff;


public class HelloRequest {
    private String _name;
    public HelloRequest()
    {

    }
    public HelloRequest(String name) {
        _name = name;
    }


    public String get_name() {
        return _name;
    }
    public void set_name(String name) {
        _name = name;
    }

}
