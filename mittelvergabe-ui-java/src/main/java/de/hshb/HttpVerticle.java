package de.hshb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class HttpVerticle extends AbstractVerticle implements Handler<RoutingContext> {

  @Override
  public void start() throws Exception {
    final Router router = Router.router(this.vertx);

    router.route().handler(StaticHandler.create());

    router.route("/api/*").handler(BodyHandler.create());
    router.route(HttpMethod.POST, "/api/mittelvergabe").handler(this);

    this.vertx.createHttpServer().requestHandler(router::accept).listen(8026);

  }

  @Override
  public void handle(final RoutingContext routingContext) {
    final JsonObject bodyAsJson = routingContext.getBodyAsJson();
    this.vertx.eventBus().send("mittelvergabe.calc", bodyAsJson, asyncResult -> {
      if (asyncResult.failed()) {
        routingContext.response().setStatusCode(400).putHeader("content-type", "text/plain").end();
      } else {
        routingContext.response().putHeader("content-type", "application/json")
            .end(asyncResult.result().body().toString());
      }
    });
  }
}
