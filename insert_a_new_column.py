import happybase

# Connect to HBase
connection = happybase.Connection("127.0.0.1", 9090, autoconnect=True)

# Get a reference to the table
table = connection.table('employeeTable')

# Specify the row key and the telephone value
row_key = '1'.encode('utf-8')
tel = '123-456-7890'  # Example telephone number

# Update the 'tel' column in the 'department' column family for row key '1'
table.put(
    row_key,  # Specify the row key '1'
    {
        'department:tel': tel.encode('utf-8')  # Insert '123-456-7890' into 'department:tel'
    }
)

print(f"Row key: 1 updated with 'tel': {tel}")

# Close the connection when done
connection.close()

