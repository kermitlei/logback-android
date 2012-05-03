package ch.qos.logback.classic.issue.lbcore243;

/**
 * Created with IntelliJ IDEA.
 * User: ceki
 * Date: 26.04.12
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Results AMD Phenom II X6 1110T processor and SSD disk
//Logback  with    immediate flush: 8356 nanos per call
//Logback  without immediate flush: 1758 nanos per call

public class PerformanceComparatorLogback {
  static Logger logbacklogger = LoggerFactory.getLogger(PerformanceComparatorLogback.class);

  public static void main(String[] args) throws JoranException, InterruptedException {
    initLogbackWithoutImmediateFlush();
    logbackParametrizedDebugCall();

    initLogbackWithImmediateFlush();
    logbackParametrizedDebugCall();
    System.out.println("###############################################");
    System.out.println("Logback  with    immediate flush: " + logbackParametrizedDebugCall() + " nanos per call");

    initLogbackWithoutImmediateFlush();
    System.out.println("Logback  without immediate flush: " + logbackParametrizedDebugCall() + " nanos per call");

    System.out.println("###############################################");
  }

  private static long logbackParametrizedDebugCall() {

    Integer j = new Integer(2);
    long start = System.nanoTime();
    for (int i = 0; i < Common.loop; i++) {
      logbacklogger.debug("SEE IF THIS IS LOGGED {}.", j);
    }
    return (System.nanoTime() - start) / Common.loop;
  }

  static String DIR_PREFIX = "src/test/java/ch/qos/logback/classic/issue/lbcore243/";


  static void configure(String file)  throws JoranException {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
      JoranConfigurator jc = new JoranConfigurator();
      jc.setContext(loggerContext);
      loggerContext.reset();
      jc.doConfigure(file);
  }


  private static void initLogbackWithoutImmediateFlush() throws JoranException {
    configure(DIR_PREFIX + "logback_without_immediateFlush.xml");
  }

  private static void initLogbackWithImmediateFlush() throws JoranException {
    configure(DIR_PREFIX + "logback_with_immediateFlush.xml");
  }
}