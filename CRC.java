import java.util.Scanner;

public class CRC {
    
    // XOR operation for binary strings
    public static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < b.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    // Mod-2 Division
    public static String mod2div(String dividend, String divisor) {
        int pick = divisor.length();
        String tmp = dividend.substring(0, pick);

        while (pick < dividend.length()) {
            if (tmp.charAt(0) == '1') {
                tmp = xor(divisor, tmp) + dividend.charAt(pick);
            } else {
                tmp = xor("0".repeat(pick), tmp) + dividend.charAt(pick);
            }
            pick += 1;
        }

        if (tmp.charAt(0) == '1') {
            tmp = xor(divisor, tmp);
        } else {
            tmp = xor("0".repeat(pick), tmp);
        }

        return tmp;
    }

    public static String encodeCRC(char dataChar) {
        int ascii = (int) dataChar;
        String data = String.format("%8s", Integer.toBinaryString(ascii)).replace(' ', '0');

        System.out.println("🧩 Binary of '" + dataChar + "': " + data);

        String generator = "1011";  // x^3 + x + 1
        String dataPadded = data + "000";  // append 3 zero bits

        String remainder = mod2div(dataPadded, generator);

        System.out.println("📦 CRC Remainder: " + remainder);

        String codeword = data + remainder;

        System.out.println("🚀 Transmitted Codeword: " + codeword);

        return codeword;
    }

    public static void checkCRC(String receivedData) {
        String generator = "1011";
        String remainder = mod2div(receivedData, generator);

        System.out.println("🧪 Receiver CRC Remainder: " + remainder);

        if (remainder.contains("1")) {
            System.out.println("❌ Error detected in received data!");
        } else {
            System.out.println("✅ No error in received data.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a character to send: ");
        char dataChar = sc.next().charAt(0);

        String codeword = encodeCRC(dataChar);

        System.out.print("Flip any bit? (y/n): ");
        String choice = sc.next();

        String received;
        if (choice.equalsIgnoreCase("y")) {
            System.out.print("Enter bit position to flip (0-based): ");
            int pos = sc.nextInt();

            char[] bits = codeword.toCharArray();
            bits[pos] = (bits[pos] == '1') ? '0' : '1';
            received = new String(bits);

            System.out.println("📥 Received with error: " + received);
        } else {
            received = codeword;
        }

        checkCRC(received);

        sc.close();
    }
}
