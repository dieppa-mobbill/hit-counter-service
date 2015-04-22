package com.mobbill.hitcounter.util.connection;

import com.datastax.driver.core.*;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by antoniop on 03/09/14.
 */
public class CassandraSessionWrapper implements Session {

    private Session session;

    public CassandraSessionWrapper(Session session){
        this.session = session;
    }

    public void setSession(Session session){
        closeWrapper();
        this.session = session;
    }

    public boolean isAvailable(){
        return session !=null && !session.isClosed();
    }

    public void  closeWrapper(){
        try{
            if(session!=null){
                session.close();
                session = null;
            }

        }catch(Exception ex){}
    }

    @Override
    public String getLoggedKeyspace() {
        return session.getLoggedKeyspace();
    }

    @Override
    public Session init() {
        return session.init();
    }

    @Override
    public ResultSet execute(String query) {
        try{
            return session.execute(query);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ResultSet execute(String query, Object... values) {
        try{
            return session.execute(query, values);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ResultSet execute(Statement statement) {
        try{
            return session.execute(statement);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ResultSetFuture executeAsync(String query) {

        try{
            return session.executeAsync(query);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ResultSetFuture executeAsync(String query, Object... values) {

        try{
            return session.executeAsync(query, values);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ResultSetFuture executeAsync(Statement statement) {

        try{
            return session.executeAsync(statement);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public PreparedStatement prepare(String query) {

        try{
            return session.prepare(query);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public PreparedStatement prepare(RegularStatement statement) {

        try{
            return session.prepare(statement);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ListenableFuture<PreparedStatement> prepareAsync(String query) {

        try{
            return session.prepareAsync(query);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public ListenableFuture<PreparedStatement> prepareAsync(RegularStatement statement) {

        try{
            return session.prepareAsync(statement);
        }catch(Exception ex){
            closeWrapper();
            throw ex;
        }
    }

    @Override
    public CloseFuture closeAsync() {
        return session.closeAsync();

    }

    @Override
    public void close() {
        this.session.close();
    }

    @Override
    public boolean isClosed() {
        return session.isClosed();
    }

    @Override
    public Cluster getCluster() {
        return session.getCluster();
    }

    @Override
    public State getState() {
        return session.getState();
    }
}
