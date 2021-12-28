package de.unidue.inf.is.stores;

import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class Store implements Closeable {

    protected Connection connection;
    protected boolean complete;

    public Store() throws StoreException{

        try{
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);

        }catch (SQLException e){
            throw new StoreException(e);
        }

    }

    public void complete(){
        complete = true;
    }

    @Override
    public void close() throws IOException {

        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }

    }
}
