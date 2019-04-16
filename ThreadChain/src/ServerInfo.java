public class ServerInfo {

    private String host;
    private int port;

    public ServerInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public String getHost() {
    	if(this.host==null || this.host.equals("")){
    		return null;
    	}
        return host;
    }

    public int getPort() {
    	if(this.port==0 || this.port<1024 || this.port>65535 ){
    		return 0;
    	}
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    // implement any helper method here if you need any
}
