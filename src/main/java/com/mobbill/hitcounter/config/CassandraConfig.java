package com.mobbill.hitcounter.config;

/**
 * Created by antoniop on 22/04/15.
 */
public class CassandraConfig {


    public String  CASSANDRA_CLUSTER_NODE                                           = "localhost";
    public String  CASSANDRA_PORT                                                   = "9042";
    public String  CASSANDRA_KEYSPACE                                               = "mobbill_keyspace";
    public String  CASSANDRA_CORE_CONNECTIONS_PER_HOST_LOCAL                        = "2";
    public String  CASSANDRA_CORE_CONNECTIONS_PER_HOST_REMOTE                       = "1";
    public String  CASSANDRA_MAX_CONNECTIONS_PER_HOST_LOCAL                         = "8";
    public String  CASSANDRA_MAX_CONNECTIONS_PER_HOST_REMOTE                        = "2";
    public String  CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL    = "25";
    public String  CASSANDRA_MIN_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE   = "25";
    public String  CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_LOCAL    = "128";
    public String  CASSANDRA_MAX_SIMULTANEOUS_REQ_PER_CONNECTION_THRESHOLD_REMOTE   = "128";


    public CassandraConfig(boolean isLive){
        if(isLive){
         this.CASSANDRA_CLUSTER_NODE = "10.0.1.141";
        }
    }
}
