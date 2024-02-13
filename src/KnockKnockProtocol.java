import java.net.*;
import java.io.*;

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

    //? arreglo con las pistas
    private String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
    
    //? respuestas a las pistas
    private String[] answers = {"Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"};

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
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK;

            //? si ya se envío la frase, es decir si ya dijmos knock knock
        } else if (state == SENTKNOCKKNOCK) {
            //? si la entrada (lo que escribió el cliente) es who's there?
            if (theInput.equalsIgnoreCase("Who's there?")) {
                //? entonces se envía el primer elemento de clues 'Turnip'
                theOutput = clues[currentJoke];
                //?el estado cambia a pista enviada
                state = SENTCLUE;

                //? si no es asi (si el cliente no escribe 'Who's there?') la respuesta es "se supone que debiste haber dicho who's there"
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                        "Try again. Knock! Knock!";
            }

            //? si es estado es igual a : (si ya se envió una pista)
        } else if (state == SENTCLUE) {
            // ? si la respuesta del cliente es igual a la pista que le envío + who?
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                //? la respuesta de este es el primer elemento del array answers y le pregunta si quiere otra broma
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                //? el estado es 'otra', es decir que el cliente quiere otra broma
                state = ANOTHER;

                //? si no responde lo esperado entonces le dice que se supone que debió haber constestado x
            } else {
                theOutput = "You're supposed to say \"" +
                        clues[currentJoke] +
                        " who?\"" +
                        "! Try again. Knock! Knock!";
                //? el estado de nuevo es enviar knock kcnok
                state = SENTKNOCKKNOCK;
            }

            //? si el estado es 'otra' es decir si el usuario quiere otra broma
        } else if (state == ANOTHER) {
            //? si la respuesta del cliente es y, es decir que sí quiere otra broma
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = SENTKNOCKKNOCK;
                //? si el usuario no quiere otra broma la respuesta de este es adios
            } else {
                theOutput = "Bye.";
                state = WAITING;
            }
        }
        return theOutput;
    }
}

//TODO: Cambiar los arrelgos 'clues' y 'answers por Arraylist<>'
//TODO: Cambiar las bromas por bromas en español, cambiar pistas y respuestas por bromas en español
//TODO:
