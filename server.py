import socket

# Step 1: Create socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Step 2: Bind to localhost on port 5001
server_socket.bind(('127.0.0.1', 5001))
server_socket.listen(1)

print("📡 Server is listening on 127.0.0.1:5001...")

# Step 3: Accept connection from client
conn, addr = server_socket.accept()
print(f"✅ Connected by {addr}")

# Step 4: Receive file name
filename = conn.recv(1024).decode()
print(f"📁 Receiving file: {filename}")

# Step 5: Receive and save the file
with open("received_" + filename, 'wb') as f:
    while True:
        data = conn.recv(4096)
        if not data:
            break
        f.write(data)

print("✅ File received successfully.")
conn.close()
server_socket.close()
