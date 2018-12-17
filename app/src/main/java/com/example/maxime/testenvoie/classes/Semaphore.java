package com.example.maxime.testenvoie.classes;

public class Semaphore {
    private int val;
	
    public Semaphore(int val) {
		this.val = val;
    }
    
    public synchronized void P() {
		//System.out.println("P : " + val);
		if (--this.val < 0)
			try {
				wait();
			} catch (InterruptedException e) {}
    }
	
    public synchronized void V() {
		//System.out.println("V : " + val);
		if (++this.val <= 0) notify();
    }
}
