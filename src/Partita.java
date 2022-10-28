import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Partita implements Runnable {


    private final Socket socket;
    public Partita(Socket socket) {
        this.socket=socket;
    }
    public void run(){
        System.out.println("Nuovo giocatore: "+ this.socket.toString());
        try(Scanner in=new Scanner(socket.getInputStream());PrintWriter output=new PrintWriter(socket.getOutputStream(),true)){

        Random var=new Random();
        int gioca=0;
        while(gioca==0){
            //gioca==0 sto settando la partita
            //gioca==1 sto giocando la partita
            //gioca==2 abbandono la partita
            //setting partita
            int vita= abs(var.nextInt()%100+10);
            int pozze= abs(var.nextInt()%10+1);
            int vitaM=abs(var.nextInt()%100+10);
            output.println("La tua vita è "+vita+" e hai "+pozze+" pozioni, vuoi combattere un mostro?(1:si /2: no)");
            int rispa;
            while(gioca==0) {
                rispa=in.nextInt();
                System.out.println("ciao1");
                System.out.println(rispa);
                output.println("La tua vit1");
                System.out.println("ciao12");
                output.println("La tua vit2");
                switch (rispa) {

                    case 1 -> {
                        output.println("Il mostro sta arrivando, buona fortuna!");
                        gioca = 1;
                    }
                    case 2 -> {
                        output.println("Alla prossima!");
                        gioca = 2;
                    }
                    default -> output.println("Inserire solo y (yes) o n (no)");
                }
            }

            //partita vera e propria
            while(gioca==1){
                output.println("Punti vita giocatore: "+vita+"\nParti di pozione: "+pozze+"\nVita del mostro: "+vitaM+"\n");
                output.println("Che vuoi fare? (1:combatti/2:usa pozione/3: scappa)");
                rispa=in.nextInt();
                int cura;
                int inputvalido=0;
                while(inputvalido==0){
                    switch (rispa) {
                        case 1-> {
                            int danni = (abs(var.nextInt() % vitaM) + 1);
                            int danniM = (abs(var.nextInt() % vita) + 1);
                            vitaM = vitaM - danni;
                            vita = vita - danniM;
                            output.println("Hai inflitto al mostro " + danni + " ma hai subito " + danniM);
                            inputvalido = 1;
                        }
                        case 2 -> {
                            cura = (abs(var.nextInt() % pozze) + 1);
                            vita = vita + cura;
                            pozze = pozze - cura;
                            output.println("Hai utilizzato " + cura + " parti di pozione");
                            inputvalido = 1;
                        }
                        case 3 -> {
                            output.println("Sei fuggito!");
                            inputvalido = 2;
                        }
                        default -> output.println("Non hai inserito una risposta corretta, riprova.");
                    }
                }
                if(vita==0&&vitaM==0){
                    output.println("Sia tu che il mostro avete terminato i vostri punti vita, è un pareggio!");
                    gioca=0;
                    break;
                } else if (vita == 0) {
                    output.println("Hai finito i tuoi punti vita, il mostro ha vinto e tu hai perso...");
                    gioca=2;
                    break;
                }
                else if(vitaM == 0){
                    output.println("Hai sconfitto il mostro, congratulazioni!");
                    gioca=0;
                    break;
                }
                else if(inputvalido==2){
                    output.println("Hai deciso di scappare, il mostro ha vinto a tavolino...");
                    gioca=2;
                    break;
                }

                }

            }
        //termino partita
        output.println("GAME OVER");
    }
        catch (Exception e){System.out.println("Socket Error");}
}}
