import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Box
{
    int x;
    int y;
    int width;
    int height;
    Color color;
    int age;

    public Box(int x, int y, int width, int height, Color color, int age)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.age = age;
    }
}

public class Snake
{
    private static List<Box> boxes;
    private static JPanel panel;

    private static int playerIndex;
    private static int playerDirection = 0;
    private static int foodIndex;
    private static boolean gameOver = false;
    private static int length = 5;
    private static int score = 0;
    private static boolean hasMoved = true;
    private static Timer deathTimer;
    private static Timer gameTimer;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Snake");

            frame.setSize(780,825);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            boxes = new ArrayList<>();

            for (int i = 1; i <= 50; i++)
            {
                for(int j = 1; j <= 50; j++)
                {
                    boxes.add(new Box((15*j), (15*i), 15, 15, Color.WHITE, 0));
                }
            }

            Random rand = new Random();

            playerIndex = rand.nextInt(2500);
            foodIndex = rand.nextInt(2500);
            boxes.get(playerIndex).color = Color.GREEN;

            while(playerIndex == foodIndex)
            {
                foodIndex = rand.nextInt(2500);
            }

            boxes.get(foodIndex).color = Color.RED;

            panel = new JPanel()
            {
                @Override
                protected void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    drawBoxes(g);
                    drawScore(g);
                    drawGameOver(g);
                }
            };

            frame.add(panel);

            panel.setLayout(null);

            JButton restartButton = new JButton("Restart");
            restartButton.setBounds(10, 770, 100, 25);

            restartButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    restartGame();
                }
            });

            panel.add(restartButton);

            frame.addKeyListener(new ArrowKeyListener());
            frame.setFocusable(true);
            frame.setVisible(true);

            deathTimer = new Timer(500, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Color colorHead = new Color(46,105,35);

                    if(boxes.get(playerIndex).color == Color.GREEN)
                    {
                        boxes.get(playerIndex).color = colorHead;
                    }
                    else
                    {
                        boxes.get(playerIndex).color = Color.GREEN;
                    }

                    panel.repaint();
                }
            });

            // Set up a Timer to repaint every 50 ms
            gameTimer = new Timer(50, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    switch (playerDirection) {
                        case 1:
                            if (playerIndex <= 49) {
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex - 50).color == Color.GREEN) {
                                gameOver = true;
                                playerDirection = 5;
                            } else {
                                for (int i = 0; i < 2500; i++) {
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex - 50).color = Color.GREEN;
                                playerIndex = playerIndex - 50;
                                hasMoved = true;
                                boxes.get(playerIndex).age = 0;

                                if (playerIndex == foodIndex) {
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) {
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED;
                                    length = length + 5;
                                    score = score + 1;
                                }

                                for (int i = 0; i < 2500; i++) {
                                    if ((boxes.get(i).age) >= length) {
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE;
                                        }
                                    }
                                }
                            }
                            break;
                        case 2:
                            if (((playerIndex + 1) % 50) == 0) {
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex + 1).color == Color.GREEN) {
                                gameOver = true;
                                playerDirection = 5;
                            } else {
                                for (int i = 0; i < 2500; i++) {
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex + 1).color = Color.GREEN;
                                playerIndex = playerIndex + 1;
                                hasMoved = true;
                                boxes.get(playerIndex).age = 0;

                                if (playerIndex == foodIndex) {
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) {
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED;
                                    length = length + 5;
                                    score = score + 1;
                                }

                                for (int i = 0; i < 2500; i++) {
                                    if ((boxes.get(i).age) >= length) {
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE;
                                        }
                                    }
                                }
                            }
                            break;
                        case 3:
                            if (playerIndex >= 2450) {
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex + 50).color == Color.GREEN) {
                                gameOver = true;
                                playerDirection = 5;
                            } else {
                                for (int i = 0; i < 2500; i++) {
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex + 50).color = Color.GREEN;
                                playerIndex = playerIndex + 50;
                                hasMoved = true;
                                boxes.get(playerIndex).age = 0;

                                if (playerIndex == foodIndex) {
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) {
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED;
                                    length = length + 5;
                                    score = score + 1;
                                }

                                for (int i = 0; i < 2500; i++) {
                                    if ((boxes.get(i).age) >= length) {
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE;
                                        }
                                    }
                                }
                            }
                            break;
                        case 4:
                            if ((playerIndex % 50) == 0) {
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex - 1).color == Color.GREEN) {
                                gameOver = true;
                                playerDirection = 5;
                            } else {
                                for (int i = 0; i < 2500; i++) {
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex - 1).color = Color.GREEN;
                                playerIndex = playerIndex - 1;
                                hasMoved = true;
                                boxes.get(playerIndex).age = 0;

                                if (playerIndex == foodIndex) {
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) {
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED;
                                    length = length + 5;
                                    score = score + 1;
                                }

                                for (int i = 0; i < 2500; i++) {
                                    if ((boxes.get(i).age) >= length) {
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE;
                                        }
                                    }
                                }
                            }
                            break;
                        case 5:
                            // do nothing
                            deathTimer.start();
                            gameTimer.stop();
                            break;
                }

                    // Trigger a repaint to reflect the color update
                    panel.repaint();
                }
            });

            gameTimer.start();
        });
    }

    private static void drawBoxes(Graphics g)
    {
        for (Box box : boxes)
        {
            g.setColor(box.color);
            g.fillRect(box.x, box.y, box.width, box.height);
            g.setColor(Color.BLACK);
            g.drawRect(box.x, box.y, box.width, box.height);
        }
    }

    private static void drawScore(Graphics g)
    {
        Font numFont = new Font("Arial", Font.BOLD, 13);

        g.setFont(numFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 650, 785);
    }

    private static void drawGameOver(Graphics g)
    {
        if(gameOver)
        {
            Font numFont = new Font("Arial", Font.BOLD, 13);

            g.setFont(numFont);
            g.setColor(Color.BLACK);
            g.drawString("Game Over!", 354, 785);
        }
    }

    private static class ArrowKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(gameOver)
            {
                return;
            }

            if(!hasMoved)
            {
                return;
            }

            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if(playerDirection != 3) {
                        playerDirection = 1;
                        hasMoved = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(playerDirection != 1) {
                        playerDirection = 3;
                        hasMoved = false;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(playerDirection != 2) {
                        playerDirection = 4;
                        hasMoved = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(playerDirection != 4) {
                        playerDirection = 2;
                        hasMoved = false;
                    }
                    break;
            }
        }
    }

    private static void restartGame()
    {
        if(deathTimer.isRunning()) {
            deathTimer.stop();
        }

        playerIndex = new Random().nextInt(2500);
        foodIndex = new Random().nextInt(2500);
        gameOver = false;
        length = 5;
        score  = 0;
        hasMoved = true;
        playerDirection = 0;

        for (Box box : boxes)
        {
            box.color = Color.WHITE;
        }

        boxes.get(playerIndex).color = Color.GREEN;

        while (playerIndex == foodIndex)
        {
            foodIndex = new Random().nextInt(2500);
        }

        boxes.get(foodIndex).color = Color.RED;

        panel.repaint();
        panel.transferFocusBackward();

        gameTimer.restart();
    }
}
