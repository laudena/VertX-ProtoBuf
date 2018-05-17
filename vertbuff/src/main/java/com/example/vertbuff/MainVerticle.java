package com.example.vertbuff;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		System.out.println("*****MainVerticle verticle started*****");
		ConfigRetriever retriever = ConfigRetriever.create(vertx);

		//InstantiateManually();
		InstantiateFromConfig(retriever);


		//multiple deployment
//	  DeploymentOptions options = new DeploymentOptions().setInstances(1);
//	  vertx.deployVerticle("com.example.vertbuff.ListenerVerticle", options);

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

	public void InstantiateManually() {
		//Manual instantiation:
		SpeakerVerticle speakerVerticle = new SpeakerVerticle();
		vertx.deployVerticle(speakerVerticle);

		ListenerVerticle listenerVerticle = new ListenerVerticle();
		vertx.deployVerticle(listenerVerticle, res -> {
			if (res.succeeded()) {
				System.out.println("Deployment id is: " + res.result());
			} else {
				System.out.println("Deployment failed!");
			}
		});
	}

	public void InstantiateFromConfig(ConfigRetriever retriever) {
		retriever.getConfig(ar -> {
			if (ar.failed()) {
				System.out.println("Failed to retrieve the configuration.");
			} else {

				JsonObject config = ar.result();
				System.out.println("Author: " + config.getString("author"));
				JsonArray verticles = new JsonArray();
				if (config.containsKey("verticles"))
				{
					verticles = config.getJsonArray("verticles");
				}

				verticles.forEach(verticle ->{
						//full class name
					System.out.println("In verticle:" +verticle.toString());
					String verticleClassName = ((JsonObject) verticle).getString("className");
					vertx.deployVerticle(verticleClassName );
				});
				System.out.println("CONFIG-->" + config.toString());



			}
		});
	}
}

