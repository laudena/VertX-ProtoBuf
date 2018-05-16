package com.example.vertbuff;

import com.example.vertbuff.HelloWorldProto;
import com.example.vertbuff.HelloWorldProto.HelloRequest;
import com.example.vertbuff.HelloWorldProto.HelloReply;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import java.util.Random;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;

public class SpeakerVerticle  extends AbstractVerticle {

	  // Convenience method so you can run it in your IDE
	  public static void main(String[] args) {

	  }


	  @Override
	  public void start() throws InvalidProtocolBufferException {

//		  try{



		  String verticleID = "SpeakerVerticle";
		  System.out.println("SpeakerVerticle verticle started !!!!!!");
		  EventBus eb = vertx.eventBus();


		  //sending hello
          com.example.vertbuff.HelloRequest helloObj = new com.example.vertbuff.HelloRequest("We Come In Peace!");
          ByteString serHelloMsg = serialize(helloObj);
          String strHelloSerialized = serHelloMsg.toStringUtf8();
          eb.publish( "news-feed",  strHelloSerialized );
          System.out.println(String.format("[HelloRequest], Original object            : name:%s", helloObj.get_name()));
          System.out.println(String.format("[HelloRequest], Serialize to byteString,   : %s", serHelloMsg));
          System.out.println(String.format("[HelloRequest], convert to     String, Tx  : %s", strHelloSerialized));

          //sending person data;
		  Person p = new com.example.vertbuff.Person(10, 25,180,"a");
		  String strPerson = serializePerson(p).toStringUtf8();;
		  eb.publish( "person-feed",  strPerson );
		  System.out.println(String.format("[Person], sent person object 	: id: %s, age: %s", p.get_person_id(), p.get_age()));

		  //sending person data inside communication message;
		  Person p2 = new com.example.vertbuff.Person(20, 45,20,"major");
		  com.example.vertbuff.PersonProto.CommunicationMessage commMsg = getCommunicationMessage(p2);
		  String strCommMessage = commMsg.toByteString().toStringUtf8();
		  eb.publish( "comm-feed",  strCommMessage );
		  System.out.println(String.format("[Speaker=>Communication Message], sent person object within comm	: id: %s, age: %s", p2.get_person_id(), p2.get_age()));

		  //sending locationdata inside communication message;
		  Location loc = new com.example.vertbuff.Location(20, 12345, 99);
		  com.example.vertbuff.PersonProto.CommunicationMessage commMsgLocation = getCommunicationMessage(loc);
		  String strCommMessageLocation = commMsgLocation.toByteString().toStringUtf8();
		  eb.publish( "comm-feed",  strCommMessageLocation );
		  System.out.println(String.format("[Speaker=>Communication Message], sent location object within comm	: id: %s, x: %s, type: %s", loc.get_Location_id(), loc.get_x(), loc.get_Location_type()));


//          }
//		  catch(InvalidProtocolBufferException ioe){
//    		System.out.println("ERROR: " + ioe.toString());
//    		throw ioe;
//		  }



//	    	vertx.createHttpServer().requestHandler(req -> {
//	              req.response()
//	                .putHeader("content-type", "text/plain")
//	                .end("Hello from Vert.x!");
//	            }).listen(8880);
//	        System.out.println("HTTP server started on port 8880");
	    }

    public static ByteString serialize(com.example.vertbuff.HelloRequest hello) {
        HelloRequest.Builder builder = HelloRequest.newBuilder();
        builder.setName(hello.get_name());
        HelloRequest protoHelloRequest = builder.build();
        return protoHelloRequest.toByteString();
    }
	public static ByteString serializePerson(com.example.vertbuff.Person person) {

		com.example.vertbuff.PersonProto.PersonReport protoPerson = getPersonMessage(person);
		return protoPerson.toByteString();
	}
	public static com.example.vertbuff.PersonProto.PersonReport getPersonMessage(com.example.vertbuff.Person person) {
		com.example.vertbuff.PersonProto.PersonReport.Builder builder = com.example.vertbuff.PersonProto.PersonReport.newBuilder();
		builder.setPersonId(person.get_person_id());
		builder.setAge(person.get_age());
		builder.setClassification(person.get_classification());
		builder.setHeight((long)person.get_height());
		return builder.build();
	}
	public static com.example.vertbuff.PersonProto.LocationReport getLocationMessage(com.example.vertbuff.Location location) {
		com.example.vertbuff.PersonProto.LocationReport.Builder builder = com.example.vertbuff.PersonProto.LocationReport.newBuilder();
		builder.setLocationId(location.get_Location_id());
		builder.setX((long)location.get_x());
		builder.setLocationType(location.get_Location_type());
		return builder.build();
	}


	public static com.example.vertbuff.PersonProto.CommunicationMessage getCommunicationMessage(com.example.vertbuff.Person person) {

        com.example.vertbuff.PersonProto.CommunicationMessage.Builder commMsgBuilder = com.example.vertbuff.PersonProto.CommunicationMessage.newBuilder();
        commMsgBuilder.setPersonReport(getPersonMessage(person));
        commMsgBuilder.setMsgid(32);
        return commMsgBuilder.build();

    }
	public static com.example.vertbuff.PersonProto.CommunicationMessage getCommunicationMessage(com.example.vertbuff.Location location) {

		com.example.vertbuff.PersonProto.CommunicationMessage.Builder commMsgBuilder = com.example.vertbuff.PersonProto.CommunicationMessage.newBuilder();
		commMsgBuilder.setLocationReport(getLocationMessage(location));
		commMsgBuilder.setMsgid(32);
		return commMsgBuilder.build();

	}




	public class Counter{
	    	int counter = 0;
	    	public Counter() {}
	    	public int Next()
	    	{
	    		counter++;
	    		return counter;
	    	}
	    }


	}
