/**
 * class Network rule to create objects of each rule
 */
class NetworkRule {
    public String direction;
    public String protocol;
    public int port;
    public String ip;

    public NetworkRule(String direction, String protocol, int port, String ip) {
        this.direction = direction;
        this.protocol = protocol;
        this.port = port;
        this.ip = ip;
    }

    // Overriding the equals and hashcode function of the object to calculate hashcode
    // for NetworkRule object

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkRule)) return false;
        NetworkRule nr = (NetworkRule) o;
        return  direction.equals(nr.direction) && protocol.equals(nr.protocol)
                && port == nr.port && ip.equals(nr.ip);
    }

    public int hashCode() {
        // Multiplying by a prime number to get less number of collisions
        long hash =  47 * (this.ip.hashCode() + this.port + this.direction.hashCode()
                + this.protocol.hashCode());
        return Long.valueOf(hash).hashCode();
    }
}