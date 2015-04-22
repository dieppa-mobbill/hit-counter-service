package com.mobbill.hitcounter.dao;

import com.datastax.driver.core.*;
import com.mobbill.hitcounter.domain.MobbillHitCounter;
import com.mobbill.hitcounter.util.connection.CassandraConnector;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antoniop on 22/04/15.
 */
public class MobbillHitDao {

    private CassandraConnector connector;

    public MobbillHitDao(){
        this.connector = CassandraConnector.getInstance();
    }


    public List<MobbillHitCounter> getData(long milisecond){

        List<MobbillHitCounter> result = new ArrayList<>();


        try{
            Session cassandraSession = connector.getCassandraSession();

            //MOCKING
            MobbillHitCounter mhc = new MobbillHitCounter();
            mhc.setCounterValue(1000);
            mhc.setTimeStamp(new Timestamp(System.currentTimeMillis()));
            result.add(mhc);

            mhc = new MobbillHitCounter();
            mhc.setCounterValue(2000);
            mhc.setTimeStamp(new Timestamp(System.currentTimeMillis()));
            result.add(mhc);

//
//            String sql = "SELECT * ....";
//            PreparedStatement statement = cassandraSession.prepare(sql);
//            BoundStatement boundStatement = new BoundStatement(statement);
//            ResultSet listResult = cassandraSession.execute(boundStatement);
//            if(listResult != null){
//                MobbillHitCounter mobbillHitCounter;
//                for(Row row :  listResult){
//                    mobbillHitCounter = new MobbillHitCounter();
//                    DateTime dt = new DateTime(row.getDate("timestamp"));
//                    mobbillHitCounter.setTimeStamp(new Timestamp(dt.getMillis()));
//                    mobbillHitCounter.setCounterValue(row.getLong("counter_value"));
//                    result.add(mobbillHitCounter);
//                }
//            }


        }catch(Exception ex){
            throw new RuntimeException("Exception getting data: "+ex.getMessage());
        }

        return result;
    }

}
