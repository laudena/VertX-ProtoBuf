package com.example.vertbuff;


import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import com.example.vertbuff.HelloWorldProto;
import sun.tools.jconsole.Messages;
import com.example.vertbuff.PersonProto;

import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ListenerVerticle extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
      System.out.println("ListenerVerticle verticle started !!!!!!");
  }


  @Override
  public void start() throws Exception {

	  System.out.println("ListenerVerticle verticle started !!!!!!");

	  String verticleID = "listener1";

//	    	  VertxOptions  options = new VertxOptions().setClustered(true);
//	    	  Consumer<Vertx> runner = vertx -> {
//	    	      try {
//	    	          vertx.deployVerticle(verticleID);
//	    	      } catch (Throwable t) {
//	    	        t.printStackTrace();
//	    	      }
//	    	    };
//	    	    if (options.isClustered()) {
//	    	      Vertx.clusteredVertx(options, res -> {
//	    	        if (res.succeeded()) {
//	    	          Vertx vertx = res.result();
//	    	          runner.accept(vertx);
//	    	        } else {
//	    	          res.cause().printStackTrace();
//	    	        }
//	    	      });
//	    	    }


    EventBus eb = vertx.eventBus();

    eb.consumer("news-feed", message -> {

    	try{

            ByteString reserilaizedHelloSrt = ByteString.copyFromUtf8(message.body().toString());
            System.out.println("header: " + message.headers());
            System.out.println(String.format("[Message], convert from string,   Rx  : %s", reserilaizedHelloSrt));
            com.example.vertbuff.HelloRequest newHelloObj = deserialize(reserilaizedHelloSrt);
            System.out.println(String.format("[Message], Deserialize back to object : name:%s", newHelloObj.get_name()));

            String str = message.body().toString();
            System.out.println("Received " + str);
            byte[] decoded = str.getBytes();
            String the_Name = HelloWorldProto.HelloRequest.parseFrom(decoded).getName();
            System.out.println("received decoded: " +the_Name);

    	}
    	catch(IOException ioe){
    		System.out.println("ERROR: " + ioe.toString());
    	}
    });

      eb.consumer("person-feed", message -> {

          try{

              ByteString reserilaizedHelloSrt = ByteString.copyFromUtf8(message.body().toString());
              System.out.println(String.format("[Message], convert from string,   Rx  : %s", reserilaizedHelloSrt));
              com.example.vertbuff.Person newPersonObj = deserializePerson(reserilaizedHelloSrt);
              System.out.println(String.format("[Message], Deserialize back to object : id:%s age:%s, height:%s, class:%s", newPersonObj.get_person_id(), newPersonObj.get_age(), newPersonObj.get_height(), newPersonObj.get_classification()));

//              String str = message.body().toString();
//              System.out.println("Received " + str);
//              byte[] decoded = str.getBytes();
//              String the_Name = HelloWorldProto.HelloRequest.parseFrom(decoded).getName();
//              System.out.println("received decoded: " +the_Name);

          }
          catch(IOException ioe){
              System.out.println("ERROR: " + ioe.toString());
          }

      });

      eb.consumer("comm-feed", message -> {

          try{

              ByteString reserilaizedCommSrt = ByteString.copyFromUtf8(message.body().toString());
              System.out.println(String.format("[Listener-Comm-Message], convert from string,   Rx  : %s", reserilaizedCommSrt));
              deserializeComm(reserilaizedCommSrt);
              //System.out.println(String.format("[Message], Deserialize back to object : id:%s age:%s, height:%s, class:%s", newPersonObj.get_person_id(), newPersonObj.get_age(), newPersonObj.get_height(), newPersonObj.get_classification()));


          }
          catch(IOException ioe){
              System.out.println("ERROR: " + ioe.toString());
          }

      });


    //eb.consumer("news-feed", message -> System.out.println("Received news on consumer 2: " + message.body()));

    //eb.consumer("news-feed", message -> System.out.println("Received news on consumer 3: " + message.body()));

    System.out.println("Ready!");
  }
    public static com.example.vertbuff.HelloRequest deserialize(ByteString input) throws InvalidProtocolBufferException
    {
        com.example.vertbuff.HelloRequest hello = new com.example.vertbuff.HelloRequest();
        HelloWorldProto.HelloRequest protoHelloRequest = HelloWorldProto.HelloRequest.parseFrom(input);
        hello.set_name(protoHelloRequest.getName());
        return hello;
    }
    public static com.example.vertbuff.Person deserializePerson(ByteString input) throws InvalidProtocolBufferException
    {
        com.example.vertbuff.Person person = new com.example.vertbuff.Person();
        com.example.vertbuff.PersonProto.PersonReport protoHelloRequest = com.example.vertbuff.PersonProto.PersonReport.parseFrom(input);
        person.set_person_id(protoHelloRequest.getPersonId());
        person.set_age(protoHelloRequest.getAge());
        person.set_height(protoHelloRequest.getHeight());
        person.set_classification(protoHelloRequest.getClassification());

        return person;
    }
    public static void deserializeComm(ByteString input) throws InvalidProtocolBufferException
    {

        //com.example.vertbuff.Person person = new com.example.vertbuff.Person();
        //com.example.vertbuff.PersonProto.PersonReport protoHelloRequest = com.example.vertbuff.PersonProto.PersonReport.parseFrom(input);

        com.example.vertbuff.PersonProto.CommunicationMessage commMsg =com.example.vertbuff.PersonProto.CommunicationMessage.parseFrom(input);
        if (commMsg.hasPersonReport())
        {
            //parse person
            HandlePersonMessage(commMsg.getPersonReport());
        }
        else if (commMsg.hasLocationReport())
        {
            //parse location
            HandleLocationMessage(commMsg.getLocationReport());
        }

    }

    private static void HandlePersonMessage(PersonProto.PersonReport personReport) {
        Person p = new Person();
        p.set_age(personReport.getAge());
        p.set_person_id(personReport.getPersonId());
        p.set_classification(personReport.getClassification());
        p.set_height(personReport.getHeight());
        System.out.println(String.format("[Listener-Person-Message], Deserialize back to object : id:%s age:%s, height:%s, class:%s", p.get_person_id(), p.get_age(), p.get_height(), p.get_classification()));
    }

    private static void HandleLocationMessage(PersonProto.LocationReport locationReport) {

        Location l = new Location();
        l.set_Location_id(locationReport.getLocationId());
        l.set_x(locationReport.getX());
        l.set_Location_type(locationReport.getLocationType());
        System.out.println(String.format("[Listener-Location-Message], Deserialize back to object : id:%s x:%s, type:%s", l.get_Location_id(), l.get_x(), l.get_Location_type()));
    }
}
