package viewcontroller;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation extends Transition {

    private ImageView imageView;
    private int count;

    private int lastIndex;

    private Image[] sequence;

    public Animation( Image[] sequence, double durationMs, ImageView imgView) {
        init( sequence, durationMs, imgView);
    }

    private void init( Image[] sequence, double durationMs, ImageView imgView) {
        this.imageView = imgView;
        this.sequence = sequence;
        this.count = sequence.length;
        
        this.imageView.setImage(sequence[0]);

        setCycleCount(1);
        setCycleDuration(Duration.millis(durationMs));
        setInterpolator(Interpolator.LINEAR);

    }

    protected void interpolate(double k) {

        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            imageView.setImage(sequence[index]);
            lastIndex = index;
        }

    }

}

