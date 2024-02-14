import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class KnockKnockProtocol {
    //? constantes que permiten saber el punto en que se encuentra la(s) broma(s)
    //? constante: representa la espera del programa
    private static final int WAITING = 0;
    //? constante: representa el envío de toc toc
    private static final int SENTKNOCKKNOCK = 1;
    //? constante: representa el envío de una pista
    private static final int SENTCLUE = 2;
    //? constante: representa el punto en que le preguntamos al cliente si quiere otra broma
    private static final int ANOTHER = 3;
    //? constante: representa el numero de bromas que hay en el programa
    private static final int NUMJOKES = 5;
    //? variable / atributo: representa el estado actual de la broma, en un inicio está esperando
    private int state = WAITING;
    //? variable / atributo: representa el número de la broma actual
    private int currentJoke = 0;

    //! atributo / variable: Lista que contendrá las pistas de las bromas
    List<String> pistas;
    //! atributo / variable: Lista que contendrá las respuestas de las pistas, la broma en sí
    List<String> respuestas;


    //!Constructor vacío, en el se inicializa el arraylist y luego se agrega elementos según corresponda
    public KnockKnockProtocol() {
        //!inicialización de arraylists pistas
        this.pistas = new ArrayList<>();
        //!poblado de arraylists pistas
        agregaPistas();

        //!inicialización de arraylists respuestas
        this.respuestas = new ArrayList<>();
        //!poblado de arraylists respuestas
        agregaRespuestas();
    }

    //!Método que agrega pistas a la lista de pistas
    public void agregaPistas() {
        this.pistas.add("ito");
        this.pistas.add("Tomas");
        this.pistas.add("Lola");
        this.pistas.add("Santa");
        this.pistas.add("Llito");
    }

    //!Método que agrega respuestas a la lista de respuestas a las pistas
    public void agregaRespuestas() {
        this.respuestas.add("El que te vio hace ratito");
        this.respuestas.add("Agua, por favor");
        this.respuestas.add("Loladrones");
        this.respuestas.add("Santander, venimos a embargarle la casa por no pagar");
        this.respuestas.add("yo, pollito!");
    }

    /**
     * ? Método que procesa la entrada / respuesta del cliente
     *
     * @param theInput respuesta del cliente
     * @return respuesta del propio servidor
     */
    public String processInput(String theInput) {
        //? cadena que representa la respuesta del servidor, que es el retorno del método
        String theOutput = null;

        //? si el estado esta en 'esperando', entonces envia la frase 'toc toc' y el estado cambia a "toc toc enviado"
        if (state == WAITING) {
            theOutput = "toc! toc!";
            state = SENTKNOCKKNOCK;

            //? si el estado es 'toc toc enviado' (ya se envío un toc toc), entonces
        } else if (state == SENTKNOCKKNOCK) {
            //? si la entrada (lo que escribió el cliente) es quien es?, entonces
            if (theInput.equalsIgnoreCase("Quien es?")) {
                //? se envía la pista de la broma actual
                theOutput = pistas.get(currentJoke);
                //? el estado cambia a 'pista enviada'
                state = SENTCLUE;

                //? si no es asi (si el cliente no escribe 'quien es?'), la respuesta del servidor es:
            } else {
                theOutput = "debiste decir \"Quien es?\", Prueba de nuevo." +  "\nToc! Toc!";
            }

            //? si es estado es igual a 'pista enviada' (ya se envío una pista previamente), entonces
        } else if (state == SENTCLUE) {
            // ? si la respuesta del cliente es igual a la pista que le envío + ?
            if (theInput.equalsIgnoreCase(pistas.get(currentJoke) + "?")) {
                //? la respuesta del servidor es la respuesta de la pista y le pregunta si quiere otra broma
                theOutput = respuestas.get(currentJoke) + ", Quieres otra super broma? (y/n)";
                //? el estado cambia a 'otra'
                state = ANOTHER;

                //? si no responde lo esperado entonces le dice que se supone que debió haber constestado:
            } else {
                theOutput = "Debiste decir \"" + pistas.get(currentJoke) + "?\", " + "Prueba de nuevo. \nToc! Toc!";
                //? el estado de nuevo 'toc toc enviado'
                state = SENTKNOCKKNOCK;
            }

            //? si el estado es 'otra', entonces
        } else if (state == ANOTHER) {
            //? si la respuesta del cliente es 'y' es decir, que sí quiere otra broma, entonces
            if (theInput.equalsIgnoreCase("y")) {
                //? enviamos toc toc
                theOutput = "Toc! Toc!";
                //? si la broma actual es igual a 4, que es el índice final de las bromas (de pruebas / respuestas), entonces iniciamos de nuevo
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                // ? si no es la última broma, avanzamos de broma
                else
                    currentJoke++;
                //? el estado de nuevo 'toc toc enviado'
                state = SENTKNOCKKNOCK;

                //? si el cliente no quiere otra broma la respuesta del servidor es: 'adios'
            } else {
                theOutput = "Adios!.";
                //? el estado cambia a esperando
                state = WAITING;
            }
        }

        //? retornamos la salida
        return theOutput;
    }
}

//TODO: implementar hilos
