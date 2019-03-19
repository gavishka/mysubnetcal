import java.util.Scanner;


/**
 *
 * @author sfernando
 */
public class Subnetcal {

    // First part of this code shows the variables used through out the class
    
    
    // This variable is used to store the class of the ip address
    String ipAddressClass;
    // The subnet mask that is corresponding to this ip address
    String subnetMask;
    // Number of hosts that can be accomodated in the given ip address
    int hostsCount;
    // The network address of the given ip address
    String networkAddress;
    // Number of bits in the ip address which are used to represent Network address
    int networkBitsCount;
    // Number of bits in the ip address which are used to represent Host address
    int hostBitsCount;
    
    // The broadcast address for this ip address.
    String broadCastAddress;

    /*
    If the CIDR is given then this boolean variable is set to true and will use that 
    CIDR as the value for the number of network bits
    */
    boolean isCIDRgiven;
    /*
    If the Subnet Mask is given, then that value is used as the subnet mask
    */
    boolean isSubnetMaskGiven;

    // These variables are used to store the octets of the ip address
    int octet1;
    int octet2;
    int octet3;
    int octet4;

    // Variables used to store ip address and subnet mask
    int ipAddressNumber;
    int netMaskNumber;
    
    /*
        Contructor of the IpAddr class which takes in the ip address value, validates the values and
        checks the format of the Ip address according to that it splits into tockens
    */
    public Subnetcal(String ipString) {

        // The ip address user input is trimmed so there will not be any spaces leading or trailing
        ipString = ipString.trim();

        // if the user input IP address is in the format of 199.212.55.7 255.255.0.0
        if (ipString.contains(" ")) {
            /*
            Split the user input considering a space to seperate the ip address and the subnet mask
            */
            String[] tokens = ipString.split(" ");
            
            
            // The second token containes the subnet mask
            subnetMask = tokens[1];

            /*
            Split the first token which is the ip address considering the dot and populate the octets
            */
            String[] octets = tokens[0].split("\\.");
            octet1 = Integer.parseInt(octets[0]);
            octet2 = Integer.parseInt(octets[1]);
            octet3 = Integer.parseInt(octets[2]);
            octet4 = Integer.parseInt(octets[3]);

            // Checks whether the octets are within the range of 0-255 or else print the error message
            if(!(octet1>=0 && octet1<=255) || !(octet2>=0 && octet2<=255) || !(octet3>=0 && octet3<=255) || !(octet4>=0 && octet4<=255)){
                throw new NumberFormatException("The octets within the ip address are not within the range of 0-255");
            }
            
            // i variable initialized with 24 and in the loop the i is decremented 8 each iteration
            int i = 24;
            ipAddressNumber = 0;

            // Each Octet is stored in the integer variable by shifting the number left by 8 positions 
            // in every loop for each octet, so that we have the ip address in a numeric format
            for(int a = 0; a < octets.length; a++) {

                int octetValue = Integer.parseInt(octets[a]);
                ipAddressNumber += octetValue << i;
                i -= 8;
            }
            
            
            /*
            The subnet mask is splitted considering the dot so we can calculate the number of host
            and network bits
            */
            String[] subnetMaskTokens = subnetMask.split("\\.");
            int numOf255 = 0;

            // Checks whether the subnetmask contains exactly four octets or else print an error message
            if(subnetMaskTokens.length!=4){
                throw new NumberFormatException("Invalid Subnet Mask. It should contain 4 octets.");
            }
            
            // Checks whether the first octet of the subnet mask is less than 255
            if(Integer.parseInt(subnetMaskTokens[0])<255){
                throw new NumberFormatException("The first byte of netmask cannot be less than 255.");
            }
            
            // Checks whether the octets are either zero or 255
            if(!(octet1==0 || octet1==255 || octet2>=0 || octet2<=255 || octet3>=0 || octet3<=255 || octet4>=0 || octet4<=255)){
                throw new NumberFormatException("The octets within the ip address are not within the range of 0-255");
            }
            
     
            i = 24;
            netMaskNumber = 0;
            
            // Subnet mask octets are stored in netmaskNumbr integer variable by shifting the number left by 8 positions
            // so that we have the subnet mask in a numeric format
            for (int a = 0; a < subnetMaskTokens.length; a++) {

                int OctetValue = Integer.parseInt(subnetMaskTokens[a]);
                netMaskNumber += OctetValue << i;
                i -= 8;
            }
            
        
            // Counts the number of 255s in the subnet mask
            for (String subStr : subnetMaskTokens) {
                if (subStr.equals("255")) {
                    numOf255++;
                }
            }

            /*
            To calculate the number of network bits, the number of 255s are mulitplied by 8
            To get the number of host bits, number of net bits are substracted from 32
            */
            networkBitsCount = numOf255 * 8;
            hostBitsCount = 32 - networkBitsCount;

            /* Setting the isCIDRgiven and isSubnetMaksgiven boolean values to true*/
            isCIDRgiven = true;
            isSubnetMaskGiven = true;
        } 
        // If the user enetered ip address is in the format of 199.212.55.7/16
        else if (ipString.contains("/")) {
            
            // Splits the user input to seperate the ip address and CIDR
            String[] tokens = ipString.split("/");
            
            // Checking whether the CIDR is greater than 32 or less than 8, if so print an error message
            if((Integer.parseInt(tokens[1])>32) || (Integer.parseInt(tokens[1])<8)){
                throw new NumberFormatException("CIDR cannot be greater than 32 or less than 8");
            }
            
            // The CIDR value is directly taken as number of network bits
            networkBitsCount = Integer.parseInt(tokens[1]);
            // To get the number of host bits 32- number of network bits
            hostBitsCount = 32 - networkBitsCount;

            // Split the ip address which is the first token considering the dot and populates the values to octets
            String[] octets = tokens[0].split("\\.");
            octet1 = Integer.parseInt(octets[0]);
            octet2 = Integer.parseInt(octets[1]);
            octet3 = Integer.parseInt(octets[2]);
            octet4 = Integer.parseInt(octets[3]);

            // Checks whether the octets fall within the range of 0-255
            if(!(octet1>=0 && octet1<=255) || !(octet2>=0 && octet2<=255) || !(octet3>=0 && octet3<=255) || !(octet4>=0 && octet4<=255)){
                throw new NumberFormatException("The octets within the ip address are not within the range of 0-255");
            }
            
            // Set the isCIDRgiven value to true
            isCIDRgiven = true;
            
            
            // Each octet from the ip address string value is stored in the integer variable
            // In each iteration of the loop the position is shifted left by 8 positions for each octet
            // so that we would have the ip address in numeric format
            // i is initialized as 24 since we want the first octet to appear first in the numeric value
            
            int i = 24;
            ipAddressNumber = 0;

            for (int a = 0; a < octets.length; a++) {

                int OctetValue = Integer.parseInt(octets[a]);
                ipAddressNumber += OctetValue << i;
                
                i -= 8;
            }
            
            
            // After splitting the ip address string the second token is seperated to store the CIDR value
            String CIDRValue=tokens[1];
            // The CIDR string value is stored as an integer value
            Integer cidrIntegerValue = new Integer(CIDRValue);
            
            // If the cidr value is greater than 32 or less than 
            if (cidrIntegerValue > 32){
                throw new NumberFormatException("CIDR can not be greater than 32");
            }

            if (cidrIntegerValue < 8){
                throw new NumberFormatException("CIDR can not be less than 8");
            }
            
            // The net mask number is initialized as all 1's
            netMaskNumber = 0xffffffff;
            // The net mask number is shifted left by the number of host bits which is 32 - CIDR 
            // So that we would get the netmask number having zeros only in the host part and 1's in the net part
            netMaskNumber = netMaskNumber << (32 - cidrIntegerValue);
                
        } 
        // If the user entered input is in the format of 199.212.55.7
        else {
            // Split the ip address considering dots to octets
            String[] octets = ipString.split("\\.");
            
            // Checks whether the number of the octets are 4
            if(octets.length!=4){
                throw new NumberFormatException("Invalid IP address: " + octets);
            }
            
            octet1 = Integer.parseInt(octets[0]);
            octet2 = Integer.parseInt(octets[1]);
            octet3 = Integer.parseInt(octets[2]);
            octet4 = Integer.parseInt(octets[3]);
            
            // Checks whether the octets are within 0-255, if not print the error message
            if(!(octet1>=0 && octet1<=255) || !(octet2>=0 && octet2<=255) || !(octet3>=0 && octet3<=255) || !(octet4>=0 && octet4<=255)){
                throw new NumberFormatException("The octets within the ip address are not within the range of 0-255");
            }
        }
    }
    
    
    
    
    /*
    The netmask number stored in numeric format "netmaskNumber" is converted to a dotted string format
    The dotted netmask string value is returned by this function
    */

    public String getNetmask() {
        // String buffer is initialized
        StringBuffer stringbuff = new StringBuffer(15);

        for (int shiftAmount = 24; shiftAmount > 0; shiftAmount -= 8) {

            // Netmask number is shifted right by 24 at first, then 16 and finally 8
            // The shifted part(last octet) is appended to String buffer variable with a dot at the end
            // While shifing the number to the right it only considers the last octet by performing the AND operation with 0xff
            // Leaving the first 3 octets zeros
            // 0xff = 00000000.00000000.00000000.11111111
            stringbuff.append(Integer.toString((netMaskNumber >>> shiftAmount) & 0xff));

            stringbuff.append('.');
        }
        // The last octet is appended at the end of the string buffer
        stringbuff.append(Integer.toString(netMaskNumber & 0xff));

        // The string value of the netmask is returned
        return stringbuff.toString();
    }

    /*
    The function converts the ip address which is an integer value to dotted string value 
    by using shifting and AND operators.
    The input is the integer IP address value
    The output is the string value of dotted decimal ip adddress
    */    
    private String convertToDottedString(Integer ip) {
        StringBuffer stringbuff = new StringBuffer(15);

        for (int shiftAmount = 24; shiftAmount > 0; shiftAmount -= 8) {

            // Any Ip address integer value is shifted right by 24 at first, then 16 and finally 8
            // The shifted part(last octet) is appended to String buffer variable with a dot at the end
            // While shifing the number to the right it only considers the last octet by performing the AND operation with 0xff
            // Logic shifting is used to move everything to the right and fills in from the left with 0's >>>
            // Leaving the first 3 octets zeros
            // 0xff = 00000000.00000000.00000000.11111111
            stringbuff.append(Integer.toString((ip >>> shiftAmount) & 0xff));
            stringbuff.append('.');
        }
        // The last octet is appended at the end of the string buffer
        stringbuff.append(Integer.toString(ip & 0xff));

        // The string value of the ip address is returned
        return stringbuff.toString();
    }

        
        
    /*
    This function returns the number of network bits from the net mask number as the CIDR number
    The input would be Netmask number
    */

    public String getCIDR() {
        int i;
        for (i = 0; i < 32; i++) {
            // The net mask number is shifted left by 1 position each iteration
            // Untill the number resulted after shifting equals zero
            // It breaks out of the loop when the shifted value equals to zero
            // The number of times this loop ran gives you the number of 1's in the netmask number which is the number of network bits
            if ((netMaskNumber << i) == 0)
                break;
        }
        // The number of network bits which is the number of times the loop ran is converted to string and is returned
        return "/" + String.valueOf(i);
    }
    
    /*
    This function gets the number of host bits by using the shift operator and tracking the 
    number of network bits and host bits.
    The input is the netmask number and the output is the number of number of host bits = 32- number of network bits
    */
        
    public String getHostbitCount(){
        int i;
        for (i = 0; i < 32; i++) {
            // The net mask number is shifted left by 1 position each iteration
            // Untill the number resulted after shifting equals zero
            // It breaks out of the loop when the shifted value equals to zero
            // The number of times this loop ran gives you the number of 1's in the netmask number which is the number of network bits
            if ((netMaskNumber << i) == 0)
                break;
        }
        // The number of host bits equals to (32 - number of network bits), number of network bits = i (The number of times the loop ran)
        return String.valueOf(32-i);
    }  
    
    /*
    This function gets the number of network bits by using the shift operator and tracking the 
    number of network bits using netmask number.
    The input is the netmask number and the output is the number of network bits.
    */
    
    public String getNetbitCount(){
        int i;
        for (i = 0; i < 32; i++) {
            // The net mask number is shifted left by 1 position each iteration
            // Untill the number resulted after shifting equals zero
            // It breaks out of the loop when the shifted value equals to zero
            // The number of times this loop ran gives you the number of 1's in the netmask number which is the number of network bits
            if ((netMaskNumber << i) == 0)
                break;
        }
        // The number of host bits equals to (32 - number of network bits), number of network bits = i (The number of times the loop ran)
        return String.valueOf(i);
    }  
    
    /*
    This function returns the network address by performing the AND opeation between the ip address and 
    the subnet mask address
    Inputs are ip address and subnet mask
    Output is Network address
    */
    
    public String getNetworkAddress(){
        // Performs the AND operation between ip addres and netmask number
        // It results the network address
        // The integer network address is converted to dotted string value using the below method
        return convertToDottedString(ipAddressNumber & netMaskNumber);
    }    
      
    /*
    This function returns the broadcast address by using the shift operator and OR operators.
    Basically what it does is, it calculates the network address and adds the host component 1 bits to the network address
    The inputs are ip address and subnet mask
    Output is the broadcast address
    */
    public String getBroadcastAddress() {
        int bitCount;
        for (bitCount = 0; bitCount < 32; bitCount++) {
            // The net mask number is shifted left by 1 position each iteration
            // Untill the number resulted after shifting equals zero
            // It breaks out of the loop when the shifted value equals to zero
            // The number of times this loop ran gives you the number of 1's in the netmask number which is the number of network bits
            if ((netMaskNumber << bitCount) == 0)
                break;
        }
        
        // The number of times the loop ran equals to the number of network bits
        int noOfNetworkbits=bitCount;
        int maxNoOfHosts = 0;

        for (int a = 0; a < (32 - noOfNetworkbits); a++) {

            // maxNoOfHosts is initialized as 0 and is shifted one position left each iteration
            maxNoOfHosts = maxNoOfHosts << 1;
            // maxNoOfHosts value is OR ed with 000000000.00000000.00000000.00000001
            // So that the last bit will always have 1
            maxNoOfHosts = maxNoOfHosts | 0x01;
            
            // The resulting maxNoOfHosts will result with 1's in the host part of the address
            // example: if number of host bits = 3 then it will result 0.0.0.7 = 00000000.00000000.00000000.00000111
        }

        // The network address is calculated by performing AND operation between ip address and netmask number
        int networkAddrNumber = ipAddressNumber & netMaskNumber;
        //System.out.println("networkAddrNumber :"+convertToDottedString(networkAddrNumber));
        
        // The maxNoOfHosts is added to the network address to get the broadcast address
        // example: if the network address is 11010110.00010000.00000111.00000000
        // Subnet mask = 11111111.11111111.11111111.11111000
        // The maxNoOfhosts = 00000000.00000000.00000000.00000111
        // Then the broadcast address is 11010110.00010000.00000111.00000111
        int broadcastAddrNumber = networkAddrNumber + maxNoOfHosts;
        //System.out.println("broadcastAddrNumber :" + convertToDottedString(broadcastAddrNumber));

        // Converting the broadcast number to dotted string
        String broadcastAddrString = convertToDottedString(broadcastAddrNumber);

        return broadcastAddrString;
    }

    /*
    This function returns the number of hosts by calculating the number of net bits and geting the number of host bits
    After getting the number of hosts bits by (32 - noOfNetworkbits) then finds the number of hosts by 2^noOfhostBits - 2
    Input is the netmask number and the output would be number of hosts
    */
    public int getNumberOfHosts() {
        int bitCount;

        for (bitCount = 0; bitCount < 32; bitCount++) {
            // The net mask number is shifted left by 1 position each iteration
            // Untill the number resulted after shifting equals zero
            // It breaks out of the loop when the shifted value equals to zero
            // The number of times this loop ran gives you the number of 1's in the netmask number which is the number of network bits
            if ((netMaskNumber << bitCount) == 0)
                break;
        }
        // The number of times the loop ran equals to the number of network bits
        int noOfNetworkbits=bitCount;
        
        // number of host bits = 2^(32 - noOfNetworkbits) - 2
        int x = (int) Math.pow(2, (32 - noOfNetworkbits));
        return x-2;
    }

    
    
        /*
    This method is used to set the netowrk and host bits after checking the first octet.
    This is basically used when the CIDR is not given in the user input
    For classful addresses
    */
    public void setCountOfNetworkAndHostBits() {
        // If the first octet falls within 0-127, then class A 
        // For class A, network bits = 8 && host bits = 24
        if (octet1 >= 0 && octet1 <= 127) {

            networkBitsCount = 8;
            hostBitsCount = 24;
        } 
        // If the first octet falls within 128-191, then class B 
        // For class B, network bits = 16 && host bits = 16
        else if (octet1 >= 128 && octet1 <= 191) {

            networkBitsCount = 16;
            hostBitsCount = 16;
        } 
        // If the first octet falls within 192-223, then class C
        // For class C, network bits = 24 && host bits = 8
        else if (octet1 >= 192 && octet1 <= 223) {

            networkBitsCount = 24;
            hostBitsCount = 8;
        }
    }

    // This method is used to get the IP class according the first octet
    // Baically this method is used for the classful ip addresses
    public String getClassOfIpAddr() {
        
        if (octet1 >= 0 && octet1 <= 127) {
            ipAddressClass = "A";
        } else if (octet1 >= 128 && octet1 <= 191) {
            ipAddressClass = "B";
        } else if (octet1 >= 192 && octet1 <= 223) {
            ipAddressClass = "C";
        } else if (octet1 >= 224 && octet1 <= 239) {
            ipAddressClass = "D";
        } else if (octet1 >= 240 && octet1 <= 255) {
            ipAddressClass = "E";
        }

        // If it is a classful ip address then the CIDR is not given, then we have to call
        // the method to set the network and host bits
        if (isCIDRgiven == false) {
            setCountOfNetworkAndHostBits();
        }

        return ipAddressClass;
    }

    // Returns the number of host bits when this get method is called
    // For classful addresses
    public int getClassfulHostBitsCount() {
        return hostBitsCount;
    }

    // Returns the number of net bits when this get method is called
    // For classful addresses
    public int getClassfulNetworkBitsCount() {
        return networkBitsCount;
    }

    // Returns the CIDR with the number of network bits
    // For classful addresses
    public String getClassfulCIDR() {
        return "/" + networkBitsCount;
    }

    /*
    Number of host bits are calculated by substracting the number of network bits from 32
    Number of hosts are calculated by (2^ number of host bits) -2
    For classful addresses
    */
    public int getClassfulHostsCount() {
        int noOfHostBits = (32 - networkBitsCount);
        hostsCount = (int) (Math.pow(2, noOfHostBits) - 2);
        return hostsCount;
    }

    // This method is used to return the network address according to the network bits
    // According to the net bits place the octet from the ip address to the network address
    // For classful addresses
    public String getClassfulNetworkAddress() {
        // If number of network bits = 8, place 0 for the last 3 octets and place the first octet same as the ip address
        if (networkBitsCount == 8) {
            networkAddress = octet1 + "." + 0 + "." + 0 + "." + 0;
        }
        // If number of network bits = 16, place 0 for the last 2 octets and place the first two octets same as the ip address
        else if (networkBitsCount == 16) {
            networkAddress = octet1 + "." + octet2 + "." + 0 + "." + 0;
        } 
        // If number of network bits = 24, place 0 for the last octet and place the first three octets same as the ip address
        else if (networkBitsCount == 24) {
            networkAddress = octet1 + "." + octet2 + "." + octet3 + "." + 0;
        }

        return networkAddress;
    }

    // This method is used to get the subnet mask according to the number of network bits
    // For classful addresses
    public String getClassfulSubnetMask() {
        // If the subnet mask is given return that value directly
        if (isSubnetMaskGiven) {
            return subnetMask;
        }
        // If the number of network bits = 8 place 255 for the first octet and place zeros for the rest of the octets
        if (networkBitsCount == 8) {
            subnetMask = 255 + "." + 0 + "." + 0 + "." + 0;
        } 
        // If the number of network bits = 16 place 255 for the first two octets and place zeros for the rest of the octets
        else if (networkBitsCount == 16) {
            subnetMask = 255 + "." + 255 + "." + 0 + "." + 0;
        } 
        // If the number of network bits = 24 place 255 for the first three octets and place zeros for the rest of the octets
        else if (networkBitsCount == 24) {
            subnetMask = 255 + "." + 255 + "." + 255 + "." + 0;
        }

        return subnetMask;
    }

    // This method is used tp get the broadcast address by replacing the zeros of the network address with 255
    // For classful addresses
    public String getClassfulBroadCastAddress() {
        broadCastAddress="";
        
        // The network address is splitted according to the dots
        String[] tokens = networkAddress.split("\\.");
        
        // Replace each zero token with 255
        for (int i=0;i<tokens.length;i++){
            if(tokens[i].equals("0")){
                tokens[i]="255";
            }
        }
        // The replaced tokens are concatenated to get the broadcast cast address
         for (int i=0;i<tokens.length;i++){
            if(i==tokens.length-1){
                broadCastAddress=broadCastAddress+tokens[i];
            }
            else{
                broadCastAddress=broadCastAddress+tokens[i]+".";
            }
        }

        return broadCastAddress;
    }
    

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);
        String ipAddress = "";
        
        // Print the message including instructions to enter the user input
        System.out.println("Enter the Ip Address in any of the following format");
        System.out.println();
        System.out.println("1st option: 199.212.55.7");
        System.out.println("2nd option: 199.212.55.7/16");
        System.out.println("3rd option: 199.212.55.7 255.255.0.0");
        System.out.println();
        System.out.println("Your input: ");
        
        // Get the Ip address from user input and store it in a variable
        ipAddress = sc.nextLine();
        System.out.println();
        
        // Trim the entered ip address to remove any leading or trailing spaces
        ipAddress=ipAddress.trim();
        
        // Create an object from the IpAddr class to make use of the methods
        Subnetcal ipobj = new Subnetcal(ipAddress);

        // Display the network class if and only if the ip address is a classful address
        if(!(ipAddress.contains(" ") || ipAddress.contains("/"))){
            System.out.println("Network Class: " + ipobj.getClassOfIpAddr());
            System.out.println("Subnet Mask: " + ipobj.getClassfulSubnetMask());
            System.out.println("CIDR: " + ipobj.getClassfulCIDR());
            System.out.println("Hosts per subnet: " + ipobj.getClassfulHostsCount());
            System.out.println("Network Address: " + ipobj.getClassfulNetworkAddress());
            System.out.println("Broadcast Address: " + ipobj.getClassfulBroadCastAddress());
            System.out.println("Bits in Host: " + ipobj.getClassfulHostBitsCount());
            System.out.println("Bits in Network: " + ipobj.getClassfulNetworkBitsCount());
        }
        else{
        // Print the following after calling the methods of the class using the ipobject
            System.out.println("Subnet Mask: " + ipobj.getNetmask());
            System.out.println("CIDR: " + ipobj.getCIDR());
            System.out.println("Hosts per subnet: " + ipobj.getNumberOfHosts());
            System.out.println("Network Address: " + ipobj.getNetworkAddress());
            System.out.println("Broadcast Address: " + ipobj.getBroadcastAddress());
            System.out.println("Bits in Host: " + ipobj.getHostbitCount());
            System.out.println("Bits in Network: " + ipobj.getNetbitCount());
        }
    }
    
}
