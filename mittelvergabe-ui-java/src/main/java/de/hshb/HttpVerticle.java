package de.hshb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class HttpVerticle extends AbstractVerticle implements Handler<RoutingContext> {

  private static final String CALC_ADDRESS = "mittelvergabe.calc";

  @Override
  public void start() throws Exception {
    final Router router = Router.router(this.vertx);

    // json api
    router.route("/api/*").handler(BodyHandler.create());
    router.route(HttpMethod.POST, "/api/mittelvergabe").handler(this);

    // websocket
    final BridgeOptions bridgeOptions = new BridgeOptions()
        .addInboundPermitted(new PermittedOptions().setAddress(CALC_ADDRESS));

    final SockJSHandler sockJSHandler = SockJSHandler.create(this.vertx).bridge(bridgeOptions);
    router.route("/eventbus/*").handler(sockJSHandler);

    // for html, js, css
    router.route().handler(StaticHandler.create());

    this.vertx.createHttpServer().requestHandler(router::accept).listen(8080);

  }

  @Override
  public void handle(final RoutingContext routingContext) {
    final JsonObject bodyAsJson = routingContext.getBodyAsJson();
    this.vertx.eventBus().send(CALC_ADDRESS, bodyAsJson, asyncResult -> {
      if (asyncResult.failed()) {
        routingContext.response().setStatusCode(400).putHeader("content-type", "text/plain").end();
      } else {
        routingContext.response().putHeader("content-type", "application/json")
            .end(asyncResult.result().body().toString());
      }
    });
  }
}
