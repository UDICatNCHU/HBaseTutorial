
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

public class HBaseInsertExample {

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
        Put put = new Put(Bytes.toBytes("Development_999999"));
        byte[] familyName = Bytes.toBytes("cf1");
        String[] columnNames = new String[] { "p-birth_date", "p-first_name", "p-last_name","p-gender"};
        String[] values = new String[] { "1970-03-10", "John", "Li", "Mageeee"};

        put.addColumn(familyName, Bytes.toBytes(columnNames[0]), Bytes.toBytes( values[0] ));
        put.addColumn(familyName, Bytes.toBytes(columnNames[1]), Bytes.toBytes( values[1] ));
        put.addColumn(familyName, Bytes.toBytes(columnNames[2]), Bytes.toBytes( values[2] ));
        put.addColumn(familyName, Bytes.toBytes(columnNames[3]), Bytes.toBytes( values[3] ));
        htable.put(put);
        htable.close();
	hAdmin.close();
        conn.close();
	System.out.println("insert completed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

