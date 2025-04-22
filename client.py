import socket
import time

# Step 1: Get file name from userima
filename = input("📂 Enter the file name to send: ")

# Step 2: Create a UDP socket
udp_client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('127.0.0.1', 5001)

# Step 3: Send filename first
udp_client.sendto(filename.encode(), server_address)
 
# Step 4: Send the file data
with open(filename, 'rb') as f:
    data = f.read(4096)
    while data:
        udp_client.sendto(data, server_address)
        time.sleep(0.01)  # Small delay to prevent data loss
        data = f.read(4096)

# Step 5: Send an end signal
udp_client.sendto(b'__END__', server_address)

print("✅ File sent successfully.")
udp_client.close()





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


