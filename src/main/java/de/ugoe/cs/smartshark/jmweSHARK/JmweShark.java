package de.ugoe.cs.smartshark.jmweSHARK;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.ugoe.cs.smartshark.jmweSHARK.util.Parameter;
import de.ugoe.cs.smartshark.model.Project;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.List;

/**
*
 */
public class JmweShark {

	public static void main(String[] args) {
		Parameter param = Parameter.getInstance();
		param.init(args);

		final Datastore datastore = createDatastore();
		Query<Project> projects = datastore.createQuery(Project.class);

		for (Project project : projects) {
			System.out.println(project.getName());
		}

		setLogLevel();
	}

	private static Datastore createDatastore() {
		Morphia morphia = new Morphia();
		morphia.mapPackage("de.ugoe.cs.smartshark.refshark.model");
		Datastore datastore = null;

		try {
			if (Parameter.getInstance().getUrl().isEmpty() || Parameter.getInstance().getDbPassword().isEmpty()) {
				datastore = morphia.createDatastore(
						new MongoClient(Parameter.getInstance().getDbHostname(), Parameter.getInstance().getDbPort()),
						Parameter.getInstance().getDbName());
			} else {
				ServerAddress addr = new ServerAddress(Parameter.getInstance().getDbHostname(),
						Parameter.getInstance().getDbPort());
				List<MongoCredential> credentialsList = Lists.newArrayList();
				MongoCredential credential = MongoCredential.createCredential(Parameter.getInstance().getDbUser(),
						Parameter.getInstance().getDbAuthentication(),
						Parameter.getInstance().getDbPassword().toCharArray());
				credentialsList.add(credential);
				MongoClient client = new MongoClient(addr, credentialsList);
				datastore = morphia.createDatastore(client, Parameter.getInstance().getDbName());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			System.exit(1);
		}

		return datastore;
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
