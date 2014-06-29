package net.tsuttsu305.tundere.screenshot;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by tsuttsu305 on 2014/06/29.
 */
public class Camera {
    private Camera(){}

    public static BufferedImage createDesktopCap(int x1, int y1, int x2, int y2) throws AWTException {
        Robot robot = new Robot();
        java.awt.Rectangle size = new java.awt.Rectangle(x1, y1, x2, y2);
        return robot.createScreenCapture(size);
    }
}
