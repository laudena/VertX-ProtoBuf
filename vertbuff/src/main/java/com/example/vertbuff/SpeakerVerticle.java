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

	        // Send a message every second

	        Random rand = new Random();
	        Counter count = new Counter();
//	        System.out.println("1");
//
//	        //vertx.setPeriodic(2000, v ->
//            String msg = "Some news! (" + count.Next() + ")" ;
//        	HelloWorldProto.HelloRequest hr = HelloWorldProto.HelloRequest.newBuilder().setName(msg).build();
//        	System.out.println("2");
//        	byte[] hr_Message = hr.toByteArray();//toByteArray();
//        	System.out.println("3, " + hr_Message);
//        	String the_Name = HelloWorldProto.HelloRequest.parseFrom(hr_Message).getName();
//        	System.out.println("4");
//        	eb.publish( "news-feed",  hr_Message.toString() );

	    	//protocol buffer example

              System.out.println("ProtoBuff Tester1");
              com.example.vertbuff.HelloRequest helloObj = new com.example.vertbuff.HelloRequest("We Come In Peace!");
              ByteString serHelloMsg = serialize(helloObj);

              String strHelloSerialized = serHelloMsg.toStringUtf8();

              eb.publish( "news-feed",  strHelloSerialized );



              ByteString reserilaizedHelloSrt = ByteString.copyFromUtf8(strHelloSerialized);
              System.out.println(String.format("[HelloRequest], Original object            : name:%s", helloObj.get_name()));
              System.out.println(String.format("[HelloRequest], Serialize to byteString,   : %s", serHelloMsg));
              System.out.println(String.format("[HelloRequest], convert to     String, Tx  : %s", strHelloSerialized));




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

//	  public static byte[] serialize(HelloRequest hr) {
//		  HelloRequestProto.HelloRequest.Builder builder = HelloRequestProto.HelloRequest.newBuilder();
//	        builder.setName(hr.name);
//	        HelloRequestProto.HelloRequest protoHelloRequest = builder.build();
//	        return protoHelloRequest.toByteArray();
//	    }

//	    public static Order deserialize(byte[] input) throws InvalidProtocolBufferException {
//	        Order order = new Order();
//	        OrderProto.Order protoOrder = OrderProto.Order.parseFrom(input);
//	        order.address = protoOrder.getOrderAddress();
//	        order.name = protoOrder.getName();
//	        order.orderId = protoOrder.getOrderId();
//	        return order;
//	    }
    public static ByteString serialize(com.example.vertbuff.HelloRequest hello) {
        HelloRequest.Builder builder = HelloRequest.newBuilder();
        builder.setName(hello.get_name());
        HelloRequest protoHelloRequest = builder.build();
        return protoHelloRequest.toByteString();
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
