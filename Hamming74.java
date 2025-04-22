import java.util.Scanner;

public class Hamming74 {

    // Function to generate Hamming(7,4) code from 4 data bits
    public static int[] generateHammingCode(int[] dataBits) {
        int[] codeword = new int[7];

        // Data bits
        codeword[2] = dataBits[0]; // d1
        codeword[4] = dataBits[1]; // d2
        codeword[5] = dataBits[2]; // d3
        codeword[6] = dataBits[3]; // d4

        // Parity bits
        codeword[0] = codeword[2] ^ codeword[4] ^ codeword[6]; // p1
        codeword[1] = codeword[2] ^ codeword[5] ^ codeword[6]; // p2
        codeword[3] = codeword[4] ^ codeword[5] ^ codeword[6]; // p4

        return codeword;
    }

    // Function to simulate transmission error
    public static void introduceError(int[] codeword, int pos) {
        if (pos >= 0 && pos < 7) {
            codeword[pos] ^= 1;
        }
    }

    // Function to detect and correct error
    public static void detectAndCorrect(int[] received) {
        int p1 = received[0] ^ received[2] ^ received[4] ^ received[6];
        int p2 = received[1] ^ received[2] ^ received[5] ^ received[6];
        int p4 = received[3] ^ received[4] ^ received[5] ^ received[6];

        int errorPosition = p4 * 4 + p2 * 2 + p1 * 1;

        if (errorPosition == 0) {
            System.out.println("No error detected.");
        } else {
            System.out.println("Error detected at position: " + errorPosition);
            received[errorPosition - 1] ^= 1; // correct the bit
            System.out.println("✅ Error corrected.");
        }

        System.out.print("Corrected codeword: ");
        for (int bit : received) {
            System.out.print(bit);
        }
        System.out.println();
    }

    // Convert ASCII character to 4-bit blocks and apply Hamming
    public static void processChar(char ch) {
        int ascii = (int) ch;
        String binary = String.format("%8s", Integer.toBinaryString(ascii)).replace(' ', '0');
        System.out.println("Binary of '" + ch + "': " + binary);

        int[] block1 = new int[4];
        int[] block2 = new int[4];

        for (int i = 0; i < 4; i++) {
            block1[i] = binary.charAt(i) - '0';
            block2[i] = binary.charAt(i + 4) - '0';
        }

        int[] code1 = generateHammingCode(block1);
        int[] code2 = generateHammingCode(block2);

        System.out.print("Hamming Code Block 1: ");
        for (int bit : code1) System.out.print(bit);
        System.out.println();

        System.out.print("Hamming Code Block 2: ");
        for (int bit : code2) System.out.print(bit);
        System.out.println();

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce error in block 1? (y/n): ");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.print("Enter position to flip (1-7): ");
            int pos = sc.nextInt();
            introduceError(code1, pos - 1);
        }

        System.out.print("Introduce error in block 2? (y/n): ");
        if (sc.next().equalsIgnoreCase("y")) {
            System.out.print("Enter position to flip (1-7): ");
            int pos = sc.nextInt();
            introduceError(code2, pos - 1);
        }

        System.out.print("Received Block 1: ");
        for (int bit : code1) System.out.print(bit);
        System.out.println();
        detectAndCorrect(code1);

        System.out.print("Received Block 2: ");
        for (int bit : code2) System.out.print(bit);
        System.out.println();
        detectAndCorrect(code2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a character to send: ");
        char ch = sc.next().charAt(0);
        processChar(ch);
        sc.close();
    }
}
