package com.mobbill.hitcounter.util.connection;

import com.datastax.driver.core.Session;
import com.mobbill.hitcounter.config.CassandraConfig;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: andyw
 * Date: 25/03/13
 * Time: 14:16
 */
public class CassandraConnector {

    private static final Logger LOG = Logger.getLogger(CassandraConnector.class.getName());

    public final String CASSANDRA_CLUSTER_NODE;
    public final String CASSANDRA_PORT;
    public final String CASSANDRA_KEYSPACE;
    public final String CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL;
    public final String CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE;
    public final String CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL;
    public final String CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE;
    public final String CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL;
    public final String CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE;
    public final String CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL;
    public final String CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE;

    private CassandraDriver cassandraDriver;
    private Connection cassandraConn = null;

    private static CassandraConnector instance;

    public static CassandraConnector getInstance(){
        if(instance != null){
            return instance;
        }
        throw new RuntimeException("Cassandra Connector hasn't been intilialized");
    }

    public static  CassandraConnector initInstance(CassandraConfig config){
        if(instance == null){
            synchronized (CassandraConnector.class){
                if(instance == null){
                    instance = new CassandraConnector(
                            config.CASSANDRA_CLUSTER_NODE,
                            config.CASSANDRA_PORT,
                            config.CASSANDRA_KEYSPACE,
                            config.CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL,
                            config.CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE,
                            config.CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL,
                            config.CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE,
                            config.CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL,
                            config.CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE,
                            config.CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL,
                            config.CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE
                            );
                }
            }
        }
        return instance;

    }

    private CassandraConnector(
            String CASSANDRA_CLUSTER_NODE,
            String CASSANDRA_PORT,
            String CASSANDRA_KEYSPACE,
            String CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL,
            String CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE,
            String CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL,
            String CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE,
            String CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL,
            String CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE,
            String CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL,
            String CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE) {


        this.CASSANDRA_CLUSTER_NODE = CASSANDRA_CLUSTER_NODE;
        this.CASSANDRA_PORT = CASSANDRA_PORT;
        this.CASSANDRA_KEYSPACE = CASSANDRA_KEYSPACE;
        this.CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL = CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL;
        this.CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE = CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE;
        this.CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL = CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL;
        this.CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE = CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE;
        this.CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL = CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL;
        this.CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE = CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE;
        this.CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL = CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL;
        this.CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE = CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE;

        LOG.log(Level.INFO, "Connection schedule completed");

    }





    public Session getCassandraSession() {
        this.initializeCassandra();
        return this.cassandraDriver.getSession();

    }

    private void initializeCassandra() {
        if (this.cassandraDriver == null || !this.cassandraDriver.isSessionAvailable()) {
            synchronized (this) {
                if (cassandraDriver == null) {
                    this.cassandraDriver = new CassandraDriver(CASSANDRA_CLUSTER_NODE, Integer.parseInt(CASSANDRA_PORT));
                }
                if (!this.cassandraDriver.isSessionAvailable()) {
                    this.cassandraDriver.connect();

                    this.cassandraDriver.getSession().execute("USE " + CASSANDRA_KEYSPACE + ";");
                }

            }
        }
    }

    private void setCassandraParameters() {
        cassandraDriver.setCoreConnectiosPerHostLocal(Integer.parseInt(CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL));
        cassandraDriver.setCoreConnectiosPerHostRemote(Integer.parseInt(CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE));
        cassandraDriver.setMaxConnectionsPerHostLocal(Integer.parseInt(CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL));
        cassandraDriver.setMaxConnectionsPerHostRemote(Integer.parseInt(CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE));
        cassandraDriver.setMinSimultaneousRequestsPerConnectionThresholdLocal(Integer.parseInt(CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL));
        cassandraDriver.setMinSimultaneousRequestsPerConnectionThresholdRemote(Integer.parseInt(CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE));
        cassandraDriver.setMaxSimultaneousRequestsPerConnectionThresholdLocal(Integer.parseInt(CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL));
        cassandraDriver.setMaxSimultaneousRequestsPerConnectionThresholdRemote(Integer.parseInt(CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE));
    }
}
