/**
 * Copyright neusta GmbH 2015. All Rights Reserved
 * Project: mittelvergabe-ui-java
 * 
 * auto generated header
 *
 * $Source:   $
 * $HeadURL:  $
 * $Revision: 1 $
 *
 * $Author: sliedtke@neusta.de$
 * $Created On: 13.05.2016 - 12:05:36 $
 *
 */
package de.hshb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

/**
 * @author sliedtke@neusta.de
 * @since 13.05.2016
 */
public class CalcMain extends AbstractVerticle {

  public static void main(final String[] args) {
    Launcher.main(new String[] { "run", CalcMain.class.getName(), "-cluster" });
  }

  /**
   * {@inheritDoc}
   * 
   * @author sliedtke
   * @since 13.05.2016
   */
  @Override
  public void start() throws Exception {

    this.vertx.deployVerticle(CalcVerticle.class.getName());

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        CalcMain.this.vertx.close();
      }
    });
  }
}
