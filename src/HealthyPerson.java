import java.awt.Color;
import java.awt.Graphics;

/**
 * HEALTHY CLASS - Level 3 of inheritance
 * This class inherits from Person
 * Represents a person who is NOT sick
 * Color: YELLOW
 */
      public class HealthyPerson extends Person {

        /**
         * CONSTRUCTOR - Creates a new Healthy person
         * @param row Starting row on grid
         * @param col Starting column on grid
         * @param sex Male or Female
         * @param ageGroup Child, Teen, Adult, Elder
         * @param belief Believer or Non-believer
         */
        public Healthy(int row, int col, String sex, String ageGroup, String belief) {
            // Call the Person constructor first!
            super(row, col, sex, ageGroup, belief);

            // Healthy people are YELLOW - easy to spot!
            this.color = Color.YELLOW;
        }

        /**
         * GET VACCINATED - Specific to Healthy people
         * Only healthy people can get vaccinated (you don't vaccinate sick people)
         */
        public void getVaccinated() {
            this.isVaccinated = true; // Mark as vaccinated
        }

        /**
         * DRAW - How to draw a Healthy person on the grid
         * Overrides the Entity draw method to add more details
         * @param g The Graphics object for drawing
         */
        @Override
        public void draw(Graphics g) {
            // Draw the person as a colored square
            g.setColor(color);
            g.fillRect(col * size, row * size, size - 1, size - 1);

            // DRAW BELIEF BORDER:
            // Blue border = BELIEVER (believes in disease, takes precautions)
            // Red border = NON-BELIEVER (doesn't believe, takes fewer precautions)
            if (belief.equals("BELIEVER")) {
                g.setColor(Color.BLUE);
                g.drawRect(col * size, row * size, size - 1, size - 1);
            } else {
                g.setColor(Color.RED);
                g.drawRect(col * size, row * size, size - 1, size - 1);
            }

            // DRAW STATUS SYMBOLS:
            g.setColor(Color.WHITE); // White text for visibility
            if (isQuarantined) {
                // "Q" means this person is in quarantine
                g.drawString("Q", col * size + 5, row * size + 15);
            }
            if (isVaccinated) {
                // "V" means this person is vaccinated
                g.drawString("V", col * size + 5, row * size + 15);
            }
            // Note: If both Q and V, they'll overlap - that's okay for simplicity
        }
    }
}
