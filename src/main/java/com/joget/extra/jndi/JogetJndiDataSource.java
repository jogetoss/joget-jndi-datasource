package com.joget.extra.jndi;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.joget.commons.util.DatabaseUtil;
import org.joget.commons.util.DynamicDataSource;
import org.joget.commons.util.LogUtil;

public class JogetJndiDataSource extends DynamicDataSource {

    @Override
    public Connection getConnection() throws SQLException {
        
        LogUtil.info(getClass().getName(), "trying to obtain datasource thru JNDI...");
        
        try{
            InitialContext ctx = new InitialContext();
            
            Context envContext = (Context) ctx.lookup("java:comp/env/");
            
            DataSource ds = (DataSource) envContext.lookup("myDataSource");
        
            LogUtil.info(getClass().getName(), "datasource obtained " + ds);
            
            Connection conn = ds.getConnection();
            
            //for mysql
            //DatabaseUtil.checkAndFixMySqlDbCollation(conn);
            
            return conn;
            
        }catch(Exception e){
            LogUtil.error(getClass().getName(), e, "error obtaining datasource thru JNDI");
        }
        
        return null;
        
    }
    
    
}
