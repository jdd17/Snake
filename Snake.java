import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Box // Each box on the grid
{
    int x;
    int y;
    int width;
    int height;
    Color color;
    int age; // Attribute to determine the box's color

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
    private static List<Box> boxes; // Array of boxes
    private static JPanel panel;

    private static int playerIndex; // Location of the player
    private static int playerDirection = 0;
    private static int foodIndex; // Location of the next apple
    private static boolean gameOver = false;
    private static int length = 5; // Length of the snake
    private static int score = 0;
    private static boolean hasMoved = true;
    private static Timer deathTimer;
    private static Timer gameTimer;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Snake"); // New Window

            frame.setSize(780,825);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            boxes = new ArrayList<>(); // Create the array

            for (int i = 1; i <= 50; i++) // Add all the boxes to fill the grid
            {
                for(int j = 1; j <= 50; j++)
                {
                    boxes.add(new Box((15*j), (15*i), 15, 15, Color.WHITE, 0));
                }
            }

            Random rand = new Random();

            playerIndex = rand.nextInt(2500);
            foodIndex = rand.nextInt(2500);
            boxes.get(playerIndex).color = Color.GREEN; // Start the snake somewhere random

            while(playerIndex == foodIndex)
            {
                foodIndex = rand.nextInt(2500); // Put the apple somewhere random on a different square
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

            JButton restartButton = new JButton("Restart"); // Add a restart button
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

            frame.addKeyListener(new ArrowKeyListener()); // Add arrow key functionality
            frame.setFocusable(true);
            frame.setVisible(true);

            deathTimer = new Timer(500, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) // If game over, blink the head of the snake
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

            gameTimer = new Timer(50, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) // Move snake every 50 ms
                {
                    switch (playerDirection) { // Determine the direction of the snake's movement
                        case 1:
                            if (playerIndex <= 49) { // Snake hits top wall
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex - 50).color == Color.GREEN) { // Snake hits itself
                                gameOver = true;
                                playerDirection = 5;
                            } else { // Valid move
                                for (int i = 0; i < 2500; i++) { // Increase every box's age by 1
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex - 50).color = Color.GREEN; // Update the start of the snake
                                playerIndex = playerIndex - 50;
                                hasMoved = true; // Update movement flag
                                boxes.get(playerIndex).age = 0; // Set the start of the snake to age 0

                                if (playerIndex == foodIndex) { // Check whether the snake got the apple
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) { // Put the next apple somewhere not on the snake
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED; // Update new apple box
                                    length = length + 5; // Lengthen the snake
                                    score = score + 1; // Increase the score
                                }

                                for (int i = 0; i < 2500; i++) { // For all boxes
                                    if ((boxes.get(i).age) >= length) { // If its age is greater than the snake's length
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE; // Then it is not part of the snake, so color it white
                                        }
                                    }
                                }
                            }
                            break;
                        case 2:
                            if (((playerIndex + 1) % 50) == 0) { // Snake hits right wall
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex + 1).color == Color.GREEN) { // Snake hits itself
                                gameOver = true;
                                playerDirection = 5;
                            } else { // Valid move
                                for (int i = 0; i < 2500; i++) { // Increase every box's age by 1
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex + 1).color = Color.GREEN; // Update the start of the snake
                                playerIndex = playerIndex + 1;
                                hasMoved = true; // Update movement flag
                                boxes.get(playerIndex).age = 0; // Set the start of the snake to age 0

                                if (playerIndex == foodIndex) { // Check whether the snake got the apple
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) { // Put the next apple somewhere not on the snake
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED; // Update new apple box
                                    length = length + 5; // Lengthen the snake
                                    score = score + 1; // Increase the score
                                }

                                for (int i = 0; i < 2500; i++) { // For all boxes
                                    if ((boxes.get(i).age) >= length) { // If its age is greater than the snake's length
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE; // Then it is not part of the snake, so color it white
                                        }
                                    }
                                }
                            }
                            break;
                        case 3:
                            if (playerIndex >= 2450) { // Snake hits bottom wall
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex + 50).color == Color.GREEN) { // Snake hits itself
                                gameOver = true;
                                playerDirection = 5;
                            } else { // Valid move
                                for (int i = 0; i < 2500; i++) { // Increase every box's age by 1
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex + 50).color = Color.GREEN; // Update the start of the snake
                                playerIndex = playerIndex + 50;
                                hasMoved = true; // Update movement flag
                                boxes.get(playerIndex).age = 0; // Set the start of the snake to age 0

                                if (playerIndex == foodIndex) { // Check whether the snake got the apple
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) { // Put the next apple somewhere not on the snake
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED; // Update new apple box
                                    length = length + 5; // Lengthen the snake
                                    score = score + 1; // Increase the score
                                }

                                for (int i = 0; i < 2500; i++) { // For all boxes
                                    if ((boxes.get(i).age) >= length) { // If its age is greater than the snake's length
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE; // Then it is not part of the snake, so color it white
                                        }
                                    }
                                }
                            }
                            break;
                        case 4:
                            if ((playerIndex % 50) == 0) { // Snake hits left wall
                                gameOver = true;
                                playerDirection = 5;
                            } else if (boxes.get(playerIndex - 1).color == Color.GREEN) { // Snake hits itself
                                gameOver = true;
                                playerDirection = 5;
                            } else { // Valid move
                                for (int i = 0; i < 2500; i++) { // Increase every box's age by 1
                                    boxes.get(i).age = boxes.get(i).age + 1;
                                }

                                boxes.get(playerIndex - 1).color = Color.GREEN; // Update the start of the snake
                                playerIndex = playerIndex - 1;
                                hasMoved = true; // Update movement flag
                                boxes.get(playerIndex).age = 0; // Set the start of the snake to age 0

                                if (playerIndex == foodIndex) { // Check whether the snake got the apple
                                    foodIndex = rand.nextInt(2500);

                                    while (boxes.get(foodIndex).color == Color.GREEN) { // Put the next apple somewhere not on the snake
                                        foodIndex = rand.nextInt(2500);
                                    }

                                    boxes.get(foodIndex).color = Color.RED; // Update new apple box
                                    length = length + 5; // Lengthen the snake
                                    score = score + 1; // Increase the score
                                }

                                for (int i = 0; i < 2500; i++) { // For all boxes
                                    if ((boxes.get(i).age) >= length) { // If its age is greater than the snake's length
                                        if (i != foodIndex) {
                                            boxes.get(i).color = Color.WHITE; // Then it is not part of the snake, so color it white
                                        }
                                    }
                                }
                            }
                            break;
                        case 5:
                            // Do nothing
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

    private static void drawBoxes(Graphics g) // Draw the boxes
    {
        for (Box box : boxes)
        {
            g.setColor(box.color);
            g.fillRect(box.x, box.y, box.width, box.height);
            g.setColor(Color.BLACK);
            g.drawRect(box.x, box.y, box.width, box.height);
        }
    }

    private static void drawScore(Graphics g) // Display the score
    {
        Font numFont = new Font("Arial", Font.BOLD, 13);

        g.setFont(numFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 650, 785);
    }

    private static void drawGameOver(Graphics g) // If game over, display the message
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
        public void keyPressed(KeyEvent e) // When a key is pressed
        {
            if(gameOver) // Return if the game is already over
            {
                return;
            }

            if(!hasMoved) // Return if the snake has not moved yet
            {
                return;
            }

            switch (e.getKeyCode()) // Determine the key pressed
            {
                case KeyEvent.VK_UP: // If the player wants to go up, update the player direction and flag
                    if(playerDirection != 3) {
                        playerDirection = 1;
                        hasMoved = false;
                    }
                    break;
                case KeyEvent.VK_DOWN: // If the player wants to go down, update the player direction and flag
                    if(playerDirection != 1) {
                        playerDirection = 3;
                        hasMoved = false;
                    }
                    break;
                case KeyEvent.VK_LEFT: // If the player wants to go left, update the player direction and flag
                    if(playerDirection != 2) {
                        playerDirection = 4;
                        hasMoved = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT: // If the player wants to go right, update the player direction and flag
                    if(playerDirection != 4) {
                        playerDirection = 2;
                        hasMoved = false;
                    }
                    break;
            }
        }
    }

    private static void restartGame() // To restart the game
    {
        if(deathTimer.isRunning()) { // Stop the timer (which stops the blinking head)
            deathTimer.stop();
        }

        playerIndex = new Random().nextInt(2500); // Reset all variables
        foodIndex = new Random().nextInt(2500);
        gameOver = false;
        length = 5;
        score  = 0;
        hasMoved = true;
        playerDirection = 0;

        for (Box box : boxes) // Color all the boxes white
        {
            box.color = Color.WHITE;
        }

        boxes.get(playerIndex).color = Color.GREEN; // Add the head of the snake

        while (playerIndex == foodIndex) // Add the first apple somwehere not on the snake
        {
            foodIndex = new Random().nextInt(2500);
        }

        boxes.get(foodIndex).color = Color.RED; // Color the apple red

        panel.repaint(); // Repaint the panel and return focus
        panel.transferFocusBackward();

        gameTimer.restart(); // Restart the timer
    }
}
