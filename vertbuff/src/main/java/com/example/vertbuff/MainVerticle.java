package com.example.vertbuff;


import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MainVerticle extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
//  public static void main(String[] args) {
//      System.out.println("main @ Main Verticle ");
//
//  }


  @Override
  public void start() throws Exception {

	  System.out.println("MainVerticle verticle started !!!!!!");
	   SpeakerVerticle speakerVerticle = new SpeakerVerticle();
	  vertx.deployVerticle(speakerVerticle);

	  ListenerVerticle listenerVerticle = new ListenerVerticle ();
	  vertx.deployVerticle(listenerVerticle, res -> {
		  if (res.succeeded()) {
			    System.out.println("Deployment id is: " + res.result());
			    vertx.undeploy(res.result(), res2 -> {
			    	  if (res2.succeeded()) {
			    	    System.out.println("Undeployed ok");
			    	  } else {
			    	    System.out.println("Undeploy failed!");
			    	  }
			    	});
			  } else {
			    System.out.println("Deployment failed!");
			  }
			});


	  //multiple deployment
	  DeploymentOptions options = new DeploymentOptions().setInstances(1);
	  vertx.deployVerticle("com.example.vertbuff.ListenerVerticle", options);

	  String verticleID = "Main1";

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



    System.out.println("Main Completed 'Start'");
  }
}
