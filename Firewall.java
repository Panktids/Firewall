import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Author: Pankti Shah
 */

class Firewall {
    // Creating a hashset of networkrule objects
    static HashSet<NetworkRule> allRules = new HashSet<>();
    static String COMMA = ",";
    static String DASH = "-";
    private Firewall(String filePath){
        String line;
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                // Reading every line of the rules

                // Operating on csv files by splitting
                String[] rules = line.split(COMMA);

                //If both IP and port contain range
                // they will contain the - character
                if (String.valueOf(rules[2]).contains(DASH) &&
                        (rules[3].contains(DASH))) {

                    // Getting the range of IP address
                    String[] rangeip = rules[3].split(DASH);
                    String startip = rangeip[0];
                    String endip = rangeip[1];

                    // Getting the range of port number
                    String[] range = rules[2].split("-");
                    int rangeStart = Integer.parseInt(range[0]);
                    int rangeEnd = Integer.parseInt(range[1]);

                    // Creating new rule object for every ip within every port
                    for (int i = rangeStart; i <= rangeEnd; i++) {
                        String starting = startip;
                        while (!starting.equals(nextIp(endip))){
                            NetworkRule nr = new NetworkRule(rules[0], rules[1], i,
                                    starting);
                            allRules.add(nr);
                            starting = nextIp(starting);
                        }
                    }

                }
                // if only port contains range
                else if (String.valueOf(rules[2]).contains(DASH)) {

                    // Getting range for port number
                    String[] range = rules[2].split(DASH);
                    int rangeStart = Integer.parseInt(range[0]);
                    int rangeEnd = Integer.parseInt(range[1]);

                    // Creating NetworkRule object for every port number
                    for (int i = rangeStart; i <= rangeEnd; i++) {
                        NetworkRule nr = new NetworkRule(rules[0], rules[1], i,
                                rules[3]);
                        allRules.add(nr);
                    }

                }

                // if only ip contains range
                else if (String.valueOf(rules[3]).contains(DASH)) {

                    // Getting the range of ip address
                    String[] range = rules[3].split(DASH);
                    String startip = range[0];
                    String endip = range[1];

                    // Creating NetworkRule object for every ip address in the range
                    while (!startip.equals(endip)){
                        NetworkRule nr = new NetworkRule(rules[0],rules[1],
                                Integer.parseInt(rules[2]), startip);
                        startip = nextIp(startip);
                        allRules.add(nr);
                    }
                }
                else {
                    // Creating network rule object for the given rules
                    NetworkRule nr = new NetworkRule(rules[0],rules[1],
                            Integer.parseInt(rules[2]),rules[3]);
                        allRules.add(nr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to check to accept or reject the packet
     *
     * @param direction direction of the incoming packet
     * @param protocol  protocol of the incoming packet
     * @param port      port number of the incoming packet
     * @param ip       ip address of the incoming packet
     * @return True if the packet is accepted based on the rules,
     * false otherwise
     *
     */
    private boolean acceptPacket(String direction, String protocol,
                                 int port, String ip){

        // Accepting packet only if the object of the packet already
        // exists in the hashset
        NetworkRule nr = new NetworkRule(direction,protocol,port,ip);
        return allRules.contains(nr);
    }
    public static void main(String[] args) {

        //Scanner for accepting the file path of the rules.csv
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the path of the rules files(csv)");
        String filePath = s.nextLine();

        // Checking if filepath given is valid or not
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()){
            System.out.println("Invalid File Path");
        }
        // Calling the constructor with filePath
        Firewall fw = new Firewall(filePath);

        // Test cases
        System.out.println(fw.acceptPacket("inbound", "tcp",
                81, "192.168.1.2"));

    }

     /**
     * Function to generate the next ip address of
     * the given ip address
     * @param start The start ip address
     * @return      The next ip address
     */
    public static String nextIp(String start){
            String seperator = ".";
            String[] parts = start.split(seperator);
            int done = 0;

            // Splitting the IP address in four integer parts
            Integer part1 = Integer.parseInt(parts[0]);
            Integer part2 = Integer.parseInt(parts[1]);
            Integer part3 = Integer.parseInt(parts[2]);
            Integer part4 = Integer.parseInt(parts[3]);

            // Checking and adding 1 to the parts to get
            // the next ip address
            if (part4 < 255) {
                part4 = part4 + 1;
                return part1 + seperator + part2 + seperator +
                        part3 + seperator + part4;
            }
            else if (part4 == 255) {
                if (part3 < 255) {
                    part3 = part3 + 1;
                    return part1 + seperator + part2 + seperator +
                            part3 + seperator + done;
                } 
                else if (part3 == 255) {
                    if (part2 < 255) {
                        part2 = part2 + 1;
                        return part1 + seperator + part2 + seperator +
                                done + seperator + done;
                    }
                    else if (part2 == 255) {
                        if (part1 < 255) {
                            part1 = part1 + 1;
                            return part1 + seperator + done + seperator +
                                    done + seperator + done;
                        }
                    }
                }
            }
        return null;
    }
}
