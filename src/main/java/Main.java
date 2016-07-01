import handlers.StateHandler;
import tools.Console;
import events.Keyboard;
import events.Mouse;
import graphics.Screen;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class Main extends Canvas implements Runnable {
  
    private double SCALE = Screen.SCALE;
    private int WIDTH = (int)(Screen.WIDTH / SCALE), HEIGHT = (int)(Screen.HEIGHT / SCALE);
    private Screen screen;
    private Thread           thread       = null;
    private boolean running = false;
    private Mouse m;
    private Keyboard key;
    private StateHandler sh = new StateHandler();
    public static JFrame frame;
  
    public Main(){
      setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
      
      screen = new Screen(WIDTH, HEIGHT);
      
      m = new Mouse(sh);
      key = new Keyboard(sh);
      
      addMouseListener(m);
      addMouseMotionListener(m);
      addKeyListener(key);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            render();
            while (delta >= 1) {
                sh.update();
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }
  
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        Graphics g = bs.getDrawGraphics();
      
        sh.render((Graphics2D)g, screen);
  
        g.dispose();
        bs.show();
    }
  
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }
	
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
  
    public static void main(String[] args) {
        new Console();
        Main m = new Main();
        frame = new JFrame("Midnight-Rider");
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(m, gbc);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocus();
        m.start();
        frame.requestFocus();
    }
  
}
