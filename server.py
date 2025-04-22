import socket

# Step 1: Create a UDP socket and bind to localhost on port 5002
udp_server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_server.bind(('127.0.0.1', 5001))

print("📡 UDP Server is ready to receive on 127.0.0.1:5002...")

# Step 2: Receive filename first
filename, addr = udp_server.recvfrom(4096)
filename = filename.decode()
print(f"📁 Receiving file: {filename}")


# Step 3: Receive file data  
with open("received_" + filename, 'wb') as f:
    while True:
        data, addr = udp_server.recvfrom(4096)
        if data == b'__END__':
            break
        f.write(data)

print("✅ File received successfully.")
udp_server.close()
