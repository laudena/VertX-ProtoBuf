package com.example.vertbuff;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {

	  ConfigRetriever retriever = ConfigRetriever.create(vertx);

	  retriever.getConfig(ar -> {
          if (ar.failed()) {
              System.out.println("Failed to retrieve the configuration.");
          } else {

          	JsonObject config = ar.result();
              System.out.println("CONFIG-->" + config.toString());
			  System.out.println("CONFIG--> arnon:" + config.getString("arnon"));
          }
      });


	  System.out.println("*****MainVerticle verticle started*****");
	   SpeakerVerticle speakerVerticle = new SpeakerVerticle();
	  vertx.deployVerticle(speakerVerticle);

	  ListenerVerticle listenerVerticle = new ListenerVerticle ();
	  vertx.deployVerticle(listenerVerticle, res -> {
		  if (res.succeeded()) {
			    System.out.println("Deployment id is: " + res.result());
//			    vertx.undeploy(res.result(), res2 -> {
//			    	  if (res2.succeeded()) {
//			    	    System.out.println("Undeployed ok");
//			    	  } else {
//			    	    System.out.println("Undeploy failed!");
//			    	  }
//			    	});
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
