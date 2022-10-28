import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        try(ServerSocket listener=new ServerSocket(10000)){
            System.out.println("Server of Dungeon Adventures is waiting for players on ip: "+ InetAddress.getLocalHost());
            ExecutorService lobby= Executors.newCachedThreadPool();
            while(true){
                lobby.execute(new Partita(listener.accept()));
            }

        }
    }
}