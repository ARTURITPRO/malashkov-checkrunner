//package listener;
//
//import edu.clevertec.check.util.ConnectionManager;
//import edu.clevertec.check.util.SettingsUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//@WebListener
//@Slf4j
//public class ApplicationInitializer implements ServletContextListener {
//    private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);
//    private static final String AUTORUN_DB_SQL = "db.autorundbsql";
//    private static final String TRUE = "true";
//    private static final String FALSE = "false";
//    private static final String SCHEMA_SQL_FILENAME = "jdbsTask10/schema.sql";
//    private static final String DATA_SQL_FILENAME = "jdbsTask10/data.sql";
//    private static final String DATA_SQL = "data.sql is running";
//    private static final String SCHEMA_SQL = "schema.sql is running";
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        executeSchemaSqlFile();
//        executeDataSqlFile();
//    }
//
//    public void executeSchemaSqlFile() {
//        try (Connection connection = ConnectionManager.get()) {
//            String propertiesConnectionDbType = SettingsUtil.get(AUTORUN_DB_SQL);
//
//            if (TRUE.equals(propertiesConnectionDbType)) {
//                String dataSql = getFileWitchUtil(SCHEMA_SQL_FILENAME);
//                Statement statDataSql = connection.createStatement();
//                for (String sql : dataSql.split("------------------------------------------------------------")) {
//                    LOG.info(SCHEMA_SQL, sql);
//                    try {
//                        boolean b = statDataSql.execute(sql);
//                    } catch (SQLException e) {
//                        LOG.error(e.getMessage());
//                    }
//                }
//            }
//        } catch (SQLException | IOException e) {
//            LOG.error(e.getMessage());
//        }
//    }
//
//    private String getFileWitchUtil(String filename) throws IOException {
//        ClassLoader classLoader = getClass().getClassLoader();
//        return IOUtils.toString(classLoader.getResourceAsStream(filename), "UTF-8");
//    }
//
//    public void executeDataSqlFile() {
//        try (Connection connection = ConnectionManager.get()) {
//            String propertiesConnectionDbType = SettingsUtil.get(AUTORUN_DB_SQL);
//
//            if (TRUE.equals(propertiesConnectionDbType)) {
//                String dataSql = getFileWitchUtil(DATA_SQL_FILENAME);
//                Statement statDataSql = connection.createStatement();
//
//                for (String sql : dataSql.split("------------------------------------------------------------")) {
//                    LOG.info(DATA_SQL, sql);
//                    try {
//                        statDataSql.execute(sql);
//                    } catch (SQLException e) {
//                        LOG.error(e.getMessage());
//                    }
//                }
//            }
//        } catch (SQLException | IOException e) {
//            LOG.error(e.getMessage());
//        }
//    }
//}
//
//
//
//
//
//
