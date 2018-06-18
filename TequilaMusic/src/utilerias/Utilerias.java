package utilerias;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author alanc
 */
public class Utilerias {
    /**
     * Apply the Fade Transition to the node
     *
     * @param e the node to which apply transition
     */
    public static void fadeTransition(Node e) {
        FadeTransition x = new FadeTransition(new Duration(1000), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }
    
    /**
     * Apply the Fade Transition to the node
     *
     * @param e the node to which apply transition
     */
    public static void fadeTransitionFast(Node e) {
        FadeTransition x = new FadeTransition(new Duration(250), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }
    
     /**
     * Apply the Fade Transition to the node
     *
     * @param e the node to which apply transition
     */
    public static void doingTransition(Node e) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.1));
        transition.setNode(e);
        transition.setToX(15);
        transition.setAutoReverse(true);
        transition.setCycleCount(4);
        transition.play();
    }
}
