package es.datastructur.synthesizer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .99; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        double capacity = Math.round(SR / frequency);

        buffer = new ArrayRingBuffer((int) capacity);
        while (!buffer.isFull()) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        //
        //       Make sure that your random numbers are different from each
        //       other.
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        while (!buffer.isFull()) {
            buffer.enqueue(Math.random() - 0.5);
        }

    }
    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double frontSample = buffer.dequeue();
        double second = buffer.peek();
        double newDouble = ((frontSample + second) / 2) * DECAY;
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}

