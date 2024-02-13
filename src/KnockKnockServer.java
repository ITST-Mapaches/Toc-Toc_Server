import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {

        //valida si se recibe un argumento en tiempo de ejecución
        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        //casteamos el argumentos a entero, este será el puerto por el que escuchará el socket servidor
        int portNumber = Integer.parseInt(args[0]);

        try (
                //crea un socket de servidor que va a esuchar en el puerto indicado
                ServerSocket serverSocket = new ServerSocket(portNumber);

                //crea un socket cliente,
                Socket clientSocket = serverSocket.accept();

                //objeto de flujo de salida, por medio de este vamos a enviar información al socket cliente
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);

                //objeto de flujo de entrada, por medio de este vamos a leer lo que el socket cliente envíe
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {

            //cadenas
            String inputLine, outputLine;

            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();

            //se guarda el knock knock
            outputLine = kkp.processInput(null);

            //envía el knock knock a través del flujo de salida
            out.println(outputLine);

            //!mientras se lea en consola algo que no sea nulo
            while ((inputLine = in.readLine()) != null) {
                //recibimos la resuespta del método processInput
                outputLine = kkp.processInput(inputLine);

                //enviamos la respuesta al cliente por el flujo de salida
                out.println(outputLine);


                //si el método processInput retorna un 'Bye' salimos el ciclo
                if (outputLine.equals("Bye."))
                    break;

            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
