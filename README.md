# mysubnetcal
Program function: The program provides the network class, subnet mask, CIDR, Hosts per subnet, Network Address,
                    Broadcast Address, Bits in Host, Bits in Network
Name: Sachini Fernando
Description: IpAddr class contains the above method implemetations to get the expected results from the ip address entered.
            Ip address can be one of the following three formats.
Expected Inputs:    Regular - 199.212.55.7
                       Or
                    With a VLSM - 199.212.55.7/16
                       Or
                    With a dotted decimal notation subnet mask - 199.212.55.7 255.255.0.0
Expected Outputs:   
                    Sample - Input: 199.212.55.7
                    Network Class: C
                    Subnet Mask: 255.255.255.0
                    CIDR: /24
                    Hosts per subnet: 254
                    Network Address: 199.212.55.0
                    Broadcast Address: 199.212.55.255
                    Bits in Host: 8
                    Bits in Network: 24
Called by whom: Running this Java class itself, will call the other methods corresponding to the format of IP address entered
