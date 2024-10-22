
import happybase

# Connect to HBase
connection = happybase.Connection("127.0.0.1", 9090, autoconnect=True)

# Create a table with two column families
connection.create_table(
    'employeeTable',
    {
        'name': dict(max_versions=10),
        'department': dict()  # Use defaults
    }
)

print("Table 'employeeTable' created successfully!")
