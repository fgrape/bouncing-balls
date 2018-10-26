package graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Display {

    public final int width;
    public final int height;

    private JFrame frame;

    private boolean closeRequested;

    private long lastFrameTime;

    private BufferStrategy bufferStrategy;
    private Graphics2D graphics;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setAutoRequestFocus(true);
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeRequested = true;
            }
        });

        Canvas canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setPreferredSize(new Dimension(width, height));

        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        frame.pack();

        frame.setLocationRelativeTo(null);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        graphics = (Graphics2D) bufferStrategy.getDrawGraphics();

        lastFrameTime = System.currentTimeMillis();
    }

    public boolean isCloseRequested() {
        return closeRequested;
    }

    public void destroy() {
        frame.dispose();
    }

    public void update() {
        if (bufferStrategy.contentsLost()) {
            graphics.dispose();
            graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        }
        bufferStrategy.show();
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void sync(int fps) {
        if (fps < 1) {
            return;
        }
        long currentFrameTime = System.currentTimeMillis();
        long deltaTime = currentFrameTime - lastFrameTime;
        long timeToSleep = (1000 / fps) - deltaTime;

        while (System.currentTimeMillis() - currentFrameTime < timeToSleep) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
            }
        }
        lastFrameTime = System.currentTimeMillis();
    }

}
