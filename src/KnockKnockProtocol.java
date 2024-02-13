import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class KnockKnockProtocol {
    //? constantes que permiten saber el punto en que se encuentra la broma
    private static final int WAITING = 0;
    //? solo se va a enviar un knock knock
    private static final int SENTKNOCKKNOCK = 1;
    //? enviar
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    //! Lista con pistas
    List<String> pistas;
    //! Lista con respuestas
    List<String> respuestas;


    //!Constructor vacío
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

    //!Método que agrega respuestas a la lista de respuestas
    public void agregaRespuestas() {
        this.respuestas.add("El que te vio hace ratito");
        this.respuestas.add("Agua, por favor");
        this.respuestas.add("Loladrones");
        this.respuestas.add("Santander, venimos a embargarle la casa por no pagar");
        this.respuestas.add("yo, pollito!");
    }


    /**
     * ? Método que
     *
     * @param theInput
     * @return
     */
    public String processInput(String theInput) {
        String theOutput = null;

        //? si el estado esta en esperando, envia la frase y el estado cambia
        if (state == WAITING) {
            theOutput = "toc! toc!";
            state = SENTKNOCKKNOCK;

            //? si ya se envío la frase, es decir si ya dijmos knock knock
        } else if (state == SENTKNOCKKNOCK) {
            //? si la entrada (lo que escribió el cliente) es who's there?
            if (theInput.equalsIgnoreCase("Quien es?")) {
                //? entonces se envía el primer elemento de clues 'Turnip'
//                theOutput = clues[currentJoke];
                theOutput = pistas.get(currentJoke);
                //?el estado cambia a pista enviada
                state = SENTCLUE;

                //? si no es asi (si el cliente no escribe 'Who's there?') la respuesta es "se supone que debiste haber dicho who's there"
            } else {
                theOutput = "debiste decir \"Quien es?\" " +
                        "Prueba de nuevo. toc! toc!";
            }

            //? si es estado es igual a : (si ya se envió una pista)
        } else if (state == SENTCLUE) {
            // ? si la respuesta del cliente es igual a la pista que le envío + ?
            if (theInput.equalsIgnoreCase(pistas.get(currentJoke) + "?")) {
                //? la respuesta de este es el primer elemento del array answers y le pregunta si quiere otra broma
                theOutput = respuestas.get(currentJoke) + " Quieres otra super broma? (y/n)";
                //? el estado es 'otra', es decir que el cliente quiere otra broma
                state = ANOTHER;

                //? si no responde lo esperado entonces le dice que se supone que debió haber constestado x
            } else {
                theOutput = "Debiste decir \"" +
                        pistas.get(currentJoke) +
                        " ?\"" +
                        "Prueba de nuevo. Toc! Toc!";
                //? el estado de nuevo es enviar knock kcnok
                state = SENTKNOCKKNOCK;
            }

            //? si el estado es 'otra' es decir si el usuario quiere otra broma
        } else if (state == ANOTHER) {
            //? si la respuesta del cliente es y, es decir que sí quiere otra broma
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Toc! Toc!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = SENTKNOCKKNOCK;
                //? si el usuario no quiere otra broma la respuesta de este es adios
            } else {
                theOutput = "Adios!.";
                state = WAITING;
            }
        }
        return theOutput;
    }
}

//TODO: implementar hilos
