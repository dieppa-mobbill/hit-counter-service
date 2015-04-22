package com.mobbill.hitcounter.util.connection;

import com.datastax.driver.core.*;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by antoniop on 15/08/14.
 */
public class CassandraDriver {

    private static final Logger LOG = Logger.getLogger(CassandraDriver.class.getName());

    private String                  node;

    private int                     port;

    private Cluster                 cluster;

    private CassandraSessionWrapper session;

    private PoolingOptions          poolingOptions;

    public CassandraDriver(final String node, final int port){
        this.node           = node;
        this.port           = port;
        this.poolingOptions = new PoolingOptions();
    }

    /**
     * Default connections 2
     */
    public void setCoreConnectiosPerHostLocal(int connections){
        this.poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, connections);
    }

    /**
     * Default connections 1
     */
    public void setCoreConnectiosPerHostRemote(int connections){
        this.poolingOptions.setCoreConnectionsPerHost(HostDistance.REMOTE, connections);
    }

    /**
     * Default connections 8
     */
    public void setMaxConnectionsPerHostLocal(int connections){
        this.poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, connections);
    }

    /**
     * Default connections 2
     */
    public void setMaxConnectionsPerHostRemote(int connections){
        this.poolingOptions.setMaxConnectionsPerHost(HostDistance.REMOTE, connections);
    }

    /**
     * Default connections 25
     */
    public void setMinSimultaneousRequestsPerConnectionThresholdLocal(int connections){
        this.poolingOptions.setMinSimultaneousRequestsPerConnectionThreshold(HostDistance.LOCAL, connections);
    }

    /**
     * Default connections 25
     */
    public void setMinSimultaneousRequestsPerConnectionThresholdRemote(int connections){
        this.poolingOptions.setMinSimultaneousRequestsPerConnectionThreshold(HostDistance.REMOTE, connections);
    }

    /**
     * Default connections 128
     */
    public void setMaxSimultaneousRequestsPerConnectionThresholdLocal(int connections){
        this.poolingOptions.setMaxSimultaneousRequestsPerConnectionThreshold(HostDistance.LOCAL, connections);
    }

    /**
     * Default connections 128
     */
    public void setMaxSimultaneousRequestsPerConnectionThresholdRemote(int connections){
        this.poolingOptions.setMaxSimultaneousRequestsPerConnectionThreshold(HostDistance.REMOTE, connections);
    }


    public void connect(){
        try{
            if(this.cluster == null || session == null || !session.isAvailable()){
                this.cluster = Cluster.builder().addContactPoint(this.node).withPort(this.port).build();


                final Metadata metadata = this.cluster.getMetadata();
                LOG.log(Level.INFO,"Connected to cluster: "+metadata.getClusterName());
                for (final Host host : metadata.getAllHosts()){
                    LOG.log(Level.INFO, "Datacenter: "+host.getDatacenter()+"; Host: "+host.getAddress()+"; Rack: "+  host.getRack());
                }

                if(session != null)
                    session.closeWrapper();

//                ExecutorService pool = Executors.newFixedThreadPool(1);
//                Callable<CassandraSessionWrapper> callable = new runnableConnect();
//                Future<CassandraSessionWrapper> future = pool.submit(callable);
//                session = future.get();
                session = new CassandraSessionWrapper(cluster.connect());
            }
        }
        catch(Throwable ex){
            ex.printStackTrace();
           this.cluster = null;
           throw ex;
        }
    }

    public boolean isSessionAvailable(){
        return session!=null && session.isAvailable();
    }

    public Session getSession(){
        return this.session;
    }

    public boolean isOpen(){
        if(cluster!=null)
            return !cluster.isClosed();
        return false;
    }

    public void close(){
        try{
            if(!cluster.isClosed())
                cluster.close();
        }catch(Throwable th){
            th.printStackTrace();
        }
    }

    class runnableConnect implements Callable {
        @Override
        public Object call() throws Exception {
            return new CassandraSessionWrapper(cluster.connect());
        }
    }

}
