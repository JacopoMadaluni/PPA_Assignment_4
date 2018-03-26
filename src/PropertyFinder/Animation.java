package PropertyFinder;

import javax.swing.*;
import java.awt.*;

/**
 * Animation for the panel.
 *
 * @author Luka Kralj
 * @version 26 March 2018
 */
public class Animation {
    // Time in milliseconds, how long we want the animation to be.
    public static final int RUN_TIME = 300;

    private JPanel panel;
    private Rectangle start;
    private Rectangle target;

    private long startTime;

    /**
     * Create new animation for the panel.
     *
     * @param panel Panel which we want to animate.
     * @param start Starting bounds of the panel, panel starts moving from here.
     * @param target Target bound of the panel, panel moves here.
     */
    public Animation(JPanel panel, Rectangle start, Rectangle target) {
        this.panel = panel;
        this.start = start;
        this.target = target;
    }

    /**
     * Runs the animation.
     */
    public void run() {
        // The lower the delay the smoother the animation is.
        Timer timer = new Timer(0, e -> {
                long duration = System.currentTimeMillis() - startTime;
                double progress = (double)duration / (double)RUN_TIME;
                if (progress > 1f) {
                    progress = 1f;
                    ((Timer)e.getSource()).stop();
                }
                Rectangle newBounds = newBounds(start, target, progress);
                panel.setBounds(newBounds);
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        startTime = System.currentTimeMillis();
        timer.start();
    }

    /**
     * Calculates new bound. Panel will be moved here in the particular step.
     *
     * @param start Starting bounds of the panel, panel starts moving from here.
     * @param target Target bound of the panel, panel moves here.
     * @param progress Relative fraction from the beginning of animation.
     * @return New position of the panel.
     */
    private Rectangle newBounds(Rectangle start, Rectangle target, double progress) {
        Rectangle newBounds = new Rectangle();
        newBounds.setLocation(newPoint(start.getLocation(), target.getLocation(), progress));
        newBounds.setSize(newDimension(start.getSize(), target.getSize(), progress));
        return newBounds;
    }

    /**
     * Calculates new size for the panel.
     *
     * @param startSize Starting size.
     * @param targetSize Size that we need to reach.
     * @param progress Relative fraction from the beginning of animation.
     * @return New dimension of the new bounds.
     */
    private Dimension newDimension(Dimension startSize, Dimension targetSize, double progress) {
        Dimension size = new Dimension();
        size.width = newValue(startSize.width, targetSize.width, progress);
        size.height = newValue(startSize.height, targetSize.height, progress);
        return size;
    }

    /**
     * Calculates new location for the panel.
     *
     * @param startPoint Starting location.
     * @param targetPoint Location we want to reach.
     * @param progress Relative fraction from the beginning of animation.
     * @return New location of the panel.
     */
    private Point newPoint(Point startPoint, Point targetPoint, double progress) {
        Point point = new Point();
        point.x = newValue(startPoint.x, targetPoint.x, progress);
        point.y = newValue(startPoint.y, targetPoint.y, progress);
        return point;
    }

    /**
     * Calculates new values according to distance and current progress.
     *
     * @param startValue Value at which the animation started.
     * @param targetValue  Value we want to reach.
     * @param fraction Progress of the animation.
     * @return New value.
     */
    private int newValue(int startValue, int targetValue, double fraction) {
        int value;
        int distance = targetValue - startValue;
        value = (int)Math.round((double)distance * fraction);
        value += startValue;

        return value;
    }


}

