import socket
import time

# Step 1: Get file name from user
filename = input("📂 Enter the file name to send: ")

# Step 2: Create socket and connect to localhost
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(('127.0.0.1', 5001))

# Step 3: Send the filename first
client_socket.send(filename.encode())

# Step 4: Send the file data
with open(filename, 'rb') as f:
    data = f.read(4096)
    while data:
        client_socket.send(data)
        time.sleep(0.01)
        data = f.read(4096)

print("✅ File sent successfully.")
client_socket.close()
