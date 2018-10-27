# Firewall



This project contains two java files
1. Firewall.java - to accept, parse and create rules and to accept/reject the incoming packet
2. NetworkRule.java - a NetworkRule class to create objects of every rule

The main logic behind the code is 
1. Parse the rules.csv file
2. For every rule create a NetworkRule object (direction,protocol,port,ip)
	2.a For a port range create an object for every port in that range
	2.b For a ip range, create an object for every ip in that range
	2.c For a range of ip and port, create an object for every ip possible for every port
3. Store these objects in the HashSet<NetworkRule>
4. For an incoming packet, create a NetworkRule object and check if the object exists in the HashSet
5. If it exists, then the packet can be accepted, otherwise cannot be accepted. 


a. I tested the code based on the examples and rules given in the question. 

b. The naive solution is to check for an incoming packet whether it fits in one of the rule or not. This would have caused the program to read the file again and again multiple time depending on the number of test 
cases and would have increased the time complexity. So i needed a way in which I could cache the entire rules in some data structuren and then check whether incoming packet can be accpeted or not.
I decided to use HashSet data structure to store all possible rules. . As the lookup time is O(1) it becomes effecient. Now when I decided to use hashing, the one important thing to think about is how will my datastructure handle the 
collisions and hashcodes of the NetworkRule object. So i calculated the hashcode for an object by multiplying it with a prime number(for better calculation) and getting the hashvalues of each string in the object and then calculated 
the hashcode again.

c. If I had more time, I would have tested my code more rigorously by trying some possible edge cases, improved the effeciency further. One of the drawbacks of the code for now is that, it creates an object
for every possible rule in a given range for ip address or port number (worse if the both ip and port number are given in ranges).


I am interested in :
1. Data Team
2. Platform Team
