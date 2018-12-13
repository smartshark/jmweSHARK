package de.ugoe.cs.smartshark.jmweSHARK;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.ugoe.cs.smartshark.jmweSHARK.util.Parameter;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
*
 */
public class JmweShark {
  public static int STORED_REFACTORINGS;

  public static void main(String[] args) {
    Parameter param = Parameter.getInstance();
    param.init(args);

    setLogLevel();


   
  }

  private static void setLogLevel() {
    Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    String level = Parameter.getInstance().getDebugLevel();

    switch (level) {
      case "INFO":
        root.setLevel(Level.INFO);
        break;
      case "DEBUG":
        root.setLevel(Level.DEBUG);
        break;
      case "WARNING":
        root.setLevel(Level.WARN);
        break;
      case "ERROR":
        root.setLevel(Level.ERROR);
        break;
      default:
        root.setLevel(Level.DEBUG);
        break;
    }
  }

}


