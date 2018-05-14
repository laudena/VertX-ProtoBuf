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
//    	     All your I/O operations


            ByteString reserilaizedHelloSrt = ByteString.copyFromUtf8(message.body().toString());
            System.out.println(String.format("[Message], convert from string,   Rx  : %s", reserilaizedHelloSrt));
            com.example.vertbuff.HelloRequest newHelloObj = deserialize(reserilaizedHelloSrt);
            System.out.println(String.format("[Message], Deserialize back to object : name:%s", newHelloObj.get_name()));


    	String str = message.body().toString();
    	System.out.println("Received " + str);
    	byte[] decoded = str.getBytes();
    	//byte[] decoded = Base64.getDecoder().decode(str);

    	String the_Name = HelloWorldProto.HelloRequest.parseFrom(decoded).getName();

    	//com.google.protobuf.ByteString bstr = com.google.protobuf.ByteString.copyFrom(str.getBytes());
    	//HelloWorldProto.HelloRequest hrProto = HelloWorldProto.HelloRequest.parseFrom(decoded);
    	System.out.println("received decoded: " +the_Name);

//    	System.out.println("Received news on consumer 1: ") +
//    		HelloRequest.ParseFromString(	 message.body().toString() ).toString());


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
}
