
import happybase

# Connect to HBase
connection = happybase.Connection("127.0.0.1", 9090, autoconnect=True)


# Get a reference to the table
table = connection.table('employeeTable')


# Open the file and read its contents
with open('employees.csv', 'r') as file:
    for line in file:
        # Split the line into id, name, and department
        id, name, department = line.strip().split(',')
        
        # Insert the data into HBase
        table.put(
            id.encode('utf-8'),  # Row key
            {
                'name:first': name.encode('utf-8'),           # Column family: 'name', qualifier: 'first'
                'department:name': department.encode('utf-8')  # Column family: 'department', qualifier: 'name'
            }
        )

print("Data inserted successfully!")

# Close the connection when done
connection.close()


print("Table 'employeeTable' created successfully!")
