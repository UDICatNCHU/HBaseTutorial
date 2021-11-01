
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.Cell;


public class HBaseScanExample {

    public static void main(String[] args) {
        Configuration conf = HBaseConfiguration.create();
        try {           
            Connection conn = ConnectionFactory.createConnection(conf);
            Admin hAdmin = conn.getAdmin();
            String tableName = "employees";

            HTableDescriptor hTableDesc = new HTableDescriptor(TableName.valueOf("employees"));
            hTableDesc.addFamily(new HColumnDescriptor("cf1"));
            if(!hAdmin.tableExists(TableName.valueOf("employees"))){
             hAdmin.createTable(hTableDesc);
             System.out.println("Table created Successfully...");}
            else{
             System.out.println("Table Existed");
            }

        Table htable = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        ResultScanner rs = htable.getScanner(scan);
        Get get = new Get(Bytes.toBytes("Development_999999"));
        try {
            for (Result r = rs.next(); r != null; r = rs.next()) {
                for (Cell cell: r.listCells()){
                    
		    //System.out.println(Bytes.toString(cell.getRowArray()));
                    //System.out.println("---");
		    //System.out.println(Bytes.toString(cell.getValueArray()));
		    //System.out.println("--------");
		    //System.out.println(((KeyValue)cell).getValueArray());
                }
		System.out.println(r.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("p-last_name")));
            }
        
	Result getresult = htable.get(get);
        System.out.println(Bytes.toString(getresult.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("p-last_name"))));

        } finally {
            rs.close();  // always close the ResultScanner!
        }
        htable.close();
        hAdmin.close();
        conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

