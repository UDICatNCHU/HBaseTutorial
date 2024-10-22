
import happybase

# Connect to HBase
connection = happybase.Connection("127.0.0.1", 9090, autoconnect=True)


# Get a reference to the table
table = connection.table('employeeTable')

# Scan all rows in the table
print("Scanning all rows:")
for key, data in table.scan():
    print(f"Row key: {key}, Data: {data}")

# Close the connection when done
connection.close()

