package de.hshb;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class HttpVerticle extends AbstractVerticle {

	
	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		
		router.route().handler(StaticHandler.create());
		
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}
}
